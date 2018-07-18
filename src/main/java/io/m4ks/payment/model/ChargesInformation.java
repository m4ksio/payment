package io.m4ks.payment.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Embeddable
public class ChargesInformation {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @Embeddable
    public static class CurrencyAmount {

        @Pattern(regexp = "\\d+(.\\d{2})?")
        private String amount;

        @Size(max = 3, min = 3)
        private String currency;
    }

    private String bearerCode;

    @ElementCollection
    @CollectionTable(name="sender_charges", joinColumns=@JoinColumn(name="payment_id"))
    private List<CurrencyAmount> senderCharges;

    private String receiverChargesAmount;

    private String receiverChargesCurrency;
}
