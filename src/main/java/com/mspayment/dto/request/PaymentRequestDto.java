package com.mspayment.dto.request;

import com.mspayment.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
public class PaymentRequestDto {

    private Long bookingId;
    private BigDecimal amount;
    private Set<PaymentMethod> paymentMethod;
}
