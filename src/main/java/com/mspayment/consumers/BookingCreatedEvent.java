package com.mspayment.consumers;

import com.mspayment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingCreatedEvent {
    private Long bookingId;
    private String userId;
    private BigDecimal totalPrice;

    public static Payment eventToNotificationEntity(BookingCreatedEvent event) {
        return Payment.builder()
                .bookingId(String.valueOf(event.getBookingId()))
                .userId(event.userId)
                .amount(event.totalPrice)
                .build();
    }
}
