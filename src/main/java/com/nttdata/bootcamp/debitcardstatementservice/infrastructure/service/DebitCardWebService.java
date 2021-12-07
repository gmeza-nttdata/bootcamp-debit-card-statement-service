package com.nttdata.bootcamp.debitcardstatementservice.infrastructure.service;

import com.nttdata.bootcamp.debitcardstatementservice.application.service.DebitCardService;
import com.nttdata.bootcamp.debitcardstatementservice.domain.entity.DebitCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DebitCardWebService implements DebitCardService {

    private final WebClient.Builder webClientBuilder;
    private final String URI;

    @Autowired
    public DebitCardWebService(WebClient.Builder webClientBuilder,
                             @Value("${bootcamp.web.debit-card:http://debit-card-service/debit-cards}") String URI) {
        this.webClientBuilder = webClientBuilder;
        this.URI = URI;
    }

    @Override
    public Flux<DebitCard> getAll() {
        return webClientBuilder.build().get().uri(URI)
                .retrieve().bodyToFlux(DebitCard.class);
    }

    @Override
    public Mono<DebitCard> get(String id) {
        return webClientBuilder.build().get()
                .uri(URI + "/{id}", id)
                .retrieve().bodyToMono(DebitCard.class);
    }
}
