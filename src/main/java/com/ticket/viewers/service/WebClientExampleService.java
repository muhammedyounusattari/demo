package com.ticket.viewers.service;

import com.ticket.viewers.exception.handler.WebClientExampleGenericException;
import com.ticket.viewers.model.Products;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@ToString
public class WebClientExampleService {

    @Value("${get.product.url:https://fakestoreapi.com/products}")
    private String productUrl;

    private final WebClient webClient;

    public Flux<Products> getProducts(){
        log.info("Request made to getProducts");
        return  webClient.get().uri(productUrl).retrieve().bodyToFlux(Products.class);
    }

    public Mono<Products> getProductById(Long id) {
        log.info("Request made to getProducts by id {}", id);
        return webClient.get().uri(productUrl+"/"+id).retrieve()
                .onStatus(HttpStatus::is4xxClientError,clientResponse -> {
                    log.info("Status code is {}", clientResponse.statusCode());
                    if(clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)){
                        return Mono.error(new WebClientExampleGenericException("No record found with id "+id,clientResponse.statusCode().value()));
                    }
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage -> {
                              return  Mono.error(new WebClientExampleGenericException(responseMessage,clientResponse.statusCode().value()));
                            });
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    log.info("Status code under 5xx is {}", clientResponse.statusCode());
                    if(clientResponse.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
                        return Mono.error(new WebClientExampleGenericException("URI call failed "+productUrl+"/"+id,clientResponse.statusCode().value()));
                    }
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage -> {
                               return Mono.error(new RuntimeException(responseMessage));
                            });
                })
                .bodyToMono(Products.class);


               // bodyToMono(Products.class).switchIfEmpty(Mono.error(new WebClientExampleGenericException("No record found with id "+id)));
    }

    public Mono<Products> getCombineCall() {
        log.info("Request made to make combine call");
        return  webClient.get().uri(productUrl).retrieve().bodyToFlux(Products.class).next().flatMap(product -> {
          return webClient.get().uri(productUrl+"/"+product.getId()).retrieve().bodyToMono(Products.class);
        });
    }

    public Mono<Products> createProduct(Products product) {
        log.info("Request made to createProduct {}",product);
        return webClient.post().uri(productUrl).bodyValue(product).retrieve().bodyToMono(Products.class);
    }

    public Mono<Products> updateProduct(Products product) {
        log.info("Request made to updateProduct {}", product);
        return webClient.put().uri(productUrl.concat("/"+product.getId())).bodyValue(product).retrieve().bodyToMono(Products.class);
    }

    public Mono<Products> deleteProductById(Long id) {
        log.info("Request made to deleteProductById by id {}", id);
        return webClient.delete().uri(productUrl+"/"+id).retrieve().bodyToMono(Products.class);
    }
}
