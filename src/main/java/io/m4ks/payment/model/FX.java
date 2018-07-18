package io.m4ks.payment.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FX {
    private String contractReference;

    @Pattern(regexp = "\\d+(.\\d+)?")
    private String exchangeRate;

    private String originalAmount;

    private String originalCurrency;
}
