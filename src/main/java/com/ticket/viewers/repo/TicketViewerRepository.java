package com.ticket.viewers.repo;

import com.ticket.viewers.model.Viewers;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketViewerRepository extends ReactiveCrudRepository<Viewers, Long> {


}
