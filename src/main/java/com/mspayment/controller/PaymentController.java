package com.mspayment.controller;

import com.mspayment.dto.request.PaymentRequestDto;
import com.mspayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Void> createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        paymentService.processPayment(paymentRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
