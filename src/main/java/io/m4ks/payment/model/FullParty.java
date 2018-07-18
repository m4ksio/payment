package io.m4ks.payment.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Embeddable
public class FullParty {

    @NotNull
    private String bankId;

    @NotNull
    private String bankIdCode;

    @NotNull
    private String accountName;

    @NotNull
    private String accountNumber;

    @NotNull
    private String accountNumberCode;

    @NotNull
    private int accountType;

    @NotNull
    private String address;

    @NotNull
    private String name;
}
