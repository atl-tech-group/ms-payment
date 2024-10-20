package com.mspayment.entity;

import com.mspayment.enums.PaymentMethod;
import com.mspayment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookingId;
    private String userId;
    private BigDecimal amount;
    private LocalDate paymentDate;

    @ElementCollection(targetClass = PaymentMethod.class)
    @CollectionTable(name = "payment_methods", joinColumns = @JoinColumn(name = "payment_id"))
    @Enumerated(value = EnumType.STRING)
    private Set<PaymentMethod> paymentMethod;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;
}
