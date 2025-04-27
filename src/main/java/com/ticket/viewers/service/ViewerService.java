package com.ticket.viewers.service;

import com.ticket.viewers.exception.handler.VieweNotFoundException;
import com.ticket.viewers.model.Viewers;
import com.ticket.viewers.repo.ViewerRepository;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ViewerService {
    private final ViewerRepository viewerRepository;
    private final Sinks.Many<Viewers> viewerSink;

    public ViewerService(ViewerRepository viewerRepository) {
        this.viewerRepository = viewerRepository;
        this.viewerSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Mono<Viewers> createViewer(Viewers viewer) {
        return viewerRepository.save(viewer)
                               .doOnNext(viewerSink::tryEmitNext);
    }

    public Flux<Viewers> getAllViewers() {
        return viewerRepository.findAll().switchIfEmpty(Mono.error(new VieweNotFoundException("No data found")));
    }

    public Flux<ServerSentEvent<Viewers>> streamViewers() {
        return viewerSink.asFlux()
                         .map(viewer -> ServerSentEvent.builder(viewer).build());
    }

    public Flux<Viewers> getViewersByTicketId(Integer ticketId) {
        return viewerRepository.findByTicketid(ticketId).switchIfEmpty(Mono.error(new VieweNotFoundException("ticketId not found "+ticketId)));
    }


}
