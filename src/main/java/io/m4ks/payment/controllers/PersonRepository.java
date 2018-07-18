package io.m4ks.payment.controllers;

import io.m4ks.payment.model.PaymentResource;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface PersonRepository extends PagingAndSortingRepository<PaymentResource, UUID> {

}