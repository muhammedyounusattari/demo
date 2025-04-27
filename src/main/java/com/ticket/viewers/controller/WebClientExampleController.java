package com.ticket.viewers.controller;

import com.ticket.viewers.model.Products;
import com.ticket.viewers.service.WebClientExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class WebClientExampleController {

    private final WebClientExampleService webClientExampleService;

    @GetMapping
    public Flux<Products> getProducts(){
        return webClientExampleService.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<Products> getByProductId(@PathVariable("id") Long id){
        return webClientExampleService.getProductById(id);
    }

    @GetMapping("/combine")
    public Mono<Products> getCombineCall(){
        return webClientExampleService.getCombineCall();
    }

    @PostMapping
    public Mono<Products> createProduct(@RequestBody Products product){
        return webClientExampleService.createProduct(product);
    }

    @PutMapping
    public Mono<Products> updateProduct(@RequestBody Products product){
        return webClientExampleService.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    public Mono<Products> deleteByProductId(@PathVariable("id") Long id){
        return webClientExampleService.deleteProductById(id);
    }

}
