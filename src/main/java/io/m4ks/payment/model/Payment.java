package io.m4ks.payment.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Payment {

    @Column
    @Pattern(regexp = "\\d+(.\\d{2})?")
    private String amount;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="bankId",column=@Column(name="beneficiary_bank_id")),
        @AttributeOverride(name="bankIdCode",column=@Column(name="beneficiary_bank_id_code")),
        @AttributeOverride(name="accountName",column=@Column(name="beneficiary_account_name")),
        @AttributeOverride(name="accountNumber",column=@Column(name="beneficiary_account_number")),
        @AttributeOverride(name="accountNumberCode",column=@Column(name="beneficiary_account_number_code")),
        @AttributeOverride(name="accountType",column=@Column(name="beneficiary_account_type")),
        @AttributeOverride(name="address",column=@Column(name="beneficiary_address")),
        @AttributeOverride(name="name",column=@Column(name="beneficiary_name")),
    })
    private FullParty beneficiaryParty;

    private ChargesInformation chargesInformation;

    @Column
    @Size(max = 3, min = 3)
    private String currency;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="bankId",column=@Column(name="debtor_bank_id")),
            @AttributeOverride(name="bankIdCode",column=@Column(name="debtor_bank_id_code")),
            @AttributeOverride(name="accountName",column=@Column(name="debtor_account_name")),
            @AttributeOverride(name="accountNumber",column=@Column(name="debtor_account_number")),
            @AttributeOverride(name="accountNumberCode",column=@Column(name="debtor_account_number_code")),
            @AttributeOverride(name="accountType",column=@Column(name="debtor_account_type")),
            @AttributeOverride(name="address",column=@Column(name="debtor_address")),
            @AttributeOverride(name="name",column=@Column(name="debtor_name")),
    })
    private FullParty debtorParty;

    private String endToEndReference;

    private FX fx;

    private String numericReference;

    private String paymentId;

    private String paymentPurpose;

    private String paymentScheme;

    private String paymentType;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String processingDate;

    private String reference;

    private String schemePaymentSubType;

    private String schemePaymentType;

    @Embedded
    private SimpleParty sponsorParty;
}
