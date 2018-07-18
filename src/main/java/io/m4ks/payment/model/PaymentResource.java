package io.m4ks.payment.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "payment_resource")
public class PaymentResource extends Resource<Payment> {

    @Override
    public String getType() {
        return "Payment";
    }
}
