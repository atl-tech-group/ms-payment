package com.mspayment.service;

import com.mspayment.dto.request.PaymentRequestDto;
import com.mspayment.entity.Payment;
import com.mspayment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public void processPayment(PaymentRequestDto paymentRequestDto) {

        Payment payment = new Payment();
        payment.setBookingId(String.valueOf(paymentRequestDto.getBookingId()));
        payment.setAmount(paymentRequestDto.getAmount());
        payment.setPaymentMethod(paymentRequestDto.getPaymentMethod());
        payment.setPaymentDate(LocalDate.now());

        paymentRepository.save(payment);
    }
}
