package io.m4ks.payment.controllers;

import io.m4ks.payment.ApiResponse;
import io.m4ks.payment.model.PaymentResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("v1/payment")
public class PaymentsController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("")
    public ApiResponse create(@RequestBody PaymentResource paymentResource) {

        PaymentResource saved = personRepository.save(paymentResource);

        return new ApiResponse(saved.getId().toString());
    }

    @GetMapping("")
    public ApiResponse list() {
        return new ApiResponse(personRepository.findAll());
    }

    @GetMapping("{id}")
    public ApiResponse get(@PathVariable String id) {
        return new ApiResponse(personRepository.findById(UUID.fromString(id)));
    }

}
