package com.ticket.viewers.repo;

import com.ticket.viewers.model.Viewers;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ViewerRepository extends ReactiveCrudRepository<Viewers, Integer> {
    Flux<Viewers> findAllByStatusTrue(); // example custom method

    Flux<Viewers> findByTicketid(Integer ticketid);
}
