package io.m4ks.payment.controllers;

import com.google.common.collect.ImmutableMap;
import io.m4ks.payment.ApiResponse;
import io.m4ks.payment.model.PaymentResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("v1/payment")
public class PaymentsController {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @PostMapping("")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody PaymentResource paymentResource) {

        PaymentResource saved = paymentsRepository.save(paymentResource);
        String newId = saved.getId().toString();

        String newURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/").path(newId).build().toString();

        return new ResponseEntity<>(new ApiResponse(newId,
                ImmutableMap.of("get", newURL)), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable String id, @Valid @RequestBody PaymentResource paymentResource) {
        Optional<PaymentResource> savedResource = paymentsRepository.findById(UUID.fromString(id)).map(old -> {
            paymentResource.setVersion(old.getVersion() + 1);
            paymentResource.setId(old.getId());
            return paymentsRepository.save(paymentResource);
        });

        return ResponseEntity.ok(new ApiResponse(null));
    }

    @GetMapping("")
    public ApiResponse list() {
        return new ApiResponse(paymentsRepository.findAll(),
                ImmutableMap.of("self", ServletUriComponentsBuilder.fromCurrentRequest().build().toString()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable String id) {
        Optional<PaymentResource> payment = paymentsRepository.findById(UUID.fromString(id));
        if (payment.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(payment.get(),
                    ImmutableMap.of("self", ServletUriComponentsBuilder.fromCurrentRequest().build().toString())));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id) {
        paymentsRepository.deleteById(UUID.fromString(id));

        return ResponseEntity.ok(new ApiResponse(null));
    }

}
