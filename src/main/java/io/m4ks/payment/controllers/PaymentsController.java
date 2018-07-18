package io.m4ks.payment.controllers;

import com.google.common.collect.ImmutableMap;
import io.m4ks.payment.ApiResponse;
import io.m4ks.payment.model.PaymentResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("v1/payment")
public class PaymentsController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody PaymentResource paymentResource) {

        PaymentResource saved = personRepository.save(paymentResource);
        String newId = saved.getId().toString();

        String newURL = ServletUriComponentsBuilder.fromCurrentRequest().path(newId).build().toString();

        return new ResponseEntity<>(new ApiResponse(newId, ImmutableMap.of("get", newURL)), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ApiResponse list() {
        return new ApiResponse(personRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable String id) {
        Optional<PaymentResource> payment = personRepository.findById(UUID.fromString(id));
        if (payment.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(payment.get()));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
