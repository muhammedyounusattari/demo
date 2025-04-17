package com.ticket.viewers.service;

import com.ticket.viewers.dto.RequestViewer;
import com.ticket.viewers.model.Viewers;
import com.ticket.viewers.repo.TicketViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class TicketViewerService {

    @Autowired
    TicketViewerRepository ticketViewerRepository;



    public Flux<Viewers> getActiveViewers(Long id) {
        return Flux.concat(Mono.just(ticketViewerRepository.findById(id)));
    }

    public Flux<Viewers> getAllViewers() {
        return ticketViewerRepository.findAll().delayElements(Duration.ofSeconds(3)).log();
    }

    public Mono<Viewers> saveViewer(RequestViewer requestViewer) {
       Mono<Viewers> savedRecord = ticketViewerRepository.save(convert(requestViewer));
      // savedRecord.subscribe(s-> System.out.println(s));
       return savedRecord;
    }

    Viewers convert(RequestViewer requestViewer){
       Viewers viewers = new Viewers();
       viewers.setId(requestViewer.getViewerid());
       viewers.setTicketid(requestViewer.getTicketid());
       viewers.setName(requestViewer.getName());
       viewers.setStatus(true);

       return viewers;
    }
}
