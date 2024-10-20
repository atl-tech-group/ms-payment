package com.mspayment.events;

import com.mspayment.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreatedEvent {
    private Long id;
    private String userId;
    private LocalDate paymentDate;
    private PaymentStatus paymentStatus;
}
