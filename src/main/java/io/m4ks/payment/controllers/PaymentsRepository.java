package io.m4ks.payment.controllers;

import io.m4ks.payment.model.PaymentResource;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface PaymentsRepository extends PagingAndSortingRepository<PaymentResource, UUID> {

}