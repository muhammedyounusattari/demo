package com.ticket.viewers.controller;

import com.ticket.viewers.model.Viewers;
import com.ticket.viewers.service.ViewerService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/viewers")
public class ViewerController {

    private final ViewerService viewerService;

    public ViewerController(ViewerService viewerService) {
        this.viewerService = viewerService;
    }

    @PostMapping
    public Mono<Viewers> addViewer(@RequestBody Viewers viewer) {
        return viewerService.createViewer(viewer);
    }

    @GetMapping
    public Flux<Viewers> listViewers() {
        return viewerService.getAllViewers();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Viewers>> streamViewers() {
        return viewerService.streamViewers();
    }

//    @GetMapping(value = "/stream/{ticketId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Viewers> streamViewersByTicketId(@PathVariable Integer ticketId) {
//        return viewerService.getViewersByTicketId(ticketId);
//    }

    @GetMapping(value = "/stream/{ticketId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Viewers>> streamViewersByTicketId(@PathVariable Integer ticketId) {
        AtomicLong counter = new AtomicLong();

        return viewerService.getViewersByTicketId(ticketId)
                .map(viewer ->
                        ServerSentEvent.<Viewers>builder()
                                .id(String.valueOf(counter.incrementAndGet()))
                                .event("viewer-event")
                                .data(viewer)
                                .build()
                );
    }

}
