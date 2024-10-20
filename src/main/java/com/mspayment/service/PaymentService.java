package com.mspayment.service;

import com.mspayment.dto.request.PaymentRequestDto;
import com.mspayment.entity.Payment;
import com.mspayment.exceptions.MyException;
import com.mspayment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            timeout = 30,
            rollbackFor = MyException.class)
    public void processPayment(PaymentRequestDto paymentRequestDto) {

        Payment payment = new Payment();
        payment.setBookingId(String.valueOf(paymentRequestDto.getBookingId()));
        payment.setAmount(paymentRequestDto.getAmount());
        payment.setPaymentMethod(paymentRequestDto.getPaymentMethod());
        payment.setPaymentDate(LocalDate.now());

        paymentRepository.save(payment);
    }
}
