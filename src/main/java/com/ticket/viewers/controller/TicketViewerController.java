package com.ticket.viewers.controller;

import com.ticket.viewers.dto.RequestViewer;
import com.ticket.viewers.model.Viewers;
import com.ticket.viewers.service.TicketViewerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/api")

public class TicketViewerController {

    Sinks.Many<Viewers> viewersSink = Sinks.many().replay().all();

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected  TicketViewerService ticketViewerService;

    @RequestMapping("/flux")
    public Flux<Integer> flux(){
        logger.info("flux request made");
    //  log.info("flux request created ");
      return  Flux.just(1,2,3,4,5,6,7).log();
    }

    @RequestMapping("/mono")
    public Mono<String> mono(){
        logger.info("mono request made");
        return Mono.just("helloworld").log();
    }

    @GetMapping(value = "/stream", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<Long> stream(){
        logger.info("stream request made");
        return Flux.interval(Duration.ofSeconds(4)).log();
    }

    @GetMapping(value = "/active/{id}", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<Viewers> getActiveViewers(@PathVariable("id") Long id){
        logger.info("/active/{} request made",id);
       return ticketViewerService.getActiveViewers(id);
    }

    @GetMapping(value = "/active", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<Viewers> getActiveAllViewers(){
        logger.info("/active request made");
       return ticketViewerService.getAllViewers();
    }

    @GetMapping(value = "/active/viewers", produces = {MediaType.APPLICATION_NDJSON_VALUE})
    public Flux<Viewers> getActiveAllViewersSink(){
        logger.info("/active/viewers request made");
        return viewersSink.asFlux();
    }

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void saveViewer(@RequestBody RequestViewer requestViewer){
        logger.info("/create request made {} ",requestViewer);
        ticketViewerService.saveViewer(requestViewer).doOnNext(savedViewer->viewersSink.tryEmitNext(savedViewer));
    }


}
