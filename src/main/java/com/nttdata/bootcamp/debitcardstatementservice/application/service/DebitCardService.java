package com.nttdata.bootcamp.debitcardstatementservice.application.service;

import com.nttdata.bootcamp.debitcardstatementservice.domain.entity.DebitCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DebitCardService {
    Flux<DebitCard> getAll();
    Mono<DebitCard> get(String id);
}
