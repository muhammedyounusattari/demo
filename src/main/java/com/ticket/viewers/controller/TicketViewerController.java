package com.ticket.viewers.controller;

import com.ticket.viewers.dto.RequestViewer;
import com.ticket.viewers.model.Viewers;
import com.ticket.viewers.service.TicketViewerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/api")
@Slf4j
public class TicketViewerController {

    Sinks.Many<Viewers> viewersSink = Sinks.many().replay().all();

    @Autowired
    protected  TicketViewerService ticketViewerService;

    @RequestMapping("/flux")
    public Flux<Integer> flux(){
      log.info("flux request created ");
      return  Flux.just(1,2,3,4,5,6,7).log();
    }

    @RequestMapping("/mono")
    public Mono<String> mono(){
        log.info("mono request made");
        return Mono.just("helloworld").log();
    }

    @GetMapping(value = "/stream", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<Long> stream(){
        log.info("stream request made");
        return Flux.interval(Duration.ofSeconds(4)).log();
    }

    @GetMapping(value = "/active/{id}", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<Viewers> getActiveViewers(@PathVariable("id") Long id){
        log.info("/active/{} request made",id);
       return ticketViewerService.getActiveViewers(id);
    }

    @GetMapping(value = "/active", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<Viewers> getActiveAllViewers(){
        log.info("/active request made");
       return ticketViewerService.getAllViewers();
    }

    @GetMapping(value = "/active/viewers", produces = {MediaType.APPLICATION_NDJSON_VALUE})
    public Flux<Viewers> getActiveAllViewersSink(){
        log.info("/active/viewers request made");
        return viewersSink.asFlux();
    }

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void saveViewer(@RequestBody RequestViewer requestViewer){
        log.info("/create request made {} ",requestViewer);
        ticketViewerService.saveViewer(requestViewer).doOnNext(savedViewer->viewersSink.tryEmitNext(savedViewer));
    }


}
