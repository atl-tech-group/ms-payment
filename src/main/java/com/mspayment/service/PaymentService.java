package com.mspayment.service;

import com.mspayment.config.properties.PaymentCreatedTopicProperties;
import com.mspayment.dto.request.PaymentRequestDto;
import com.mspayment.entity.Payment;
import com.mspayment.events.PaymentCreatedEvent;
import com.mspayment.exceptions.MyException;
import com.mspayment.producer.PaymentKafkaProducer;
import com.mspayment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.kafka.support.KafkaHeaders.KEY;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentKafkaProducer paymentKafkaProducer;
    private final PaymentCreatedTopicProperties paymentCreatedTopicProperties;

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
        sendPaymentCreatedEvent(payment);
    }

    public void save(Payment payment) {
        Payment savedEntity = paymentRepository.save(payment);
        log.info("Saved Payment Id: {}", savedEntity.getId());
    }

    private void sendPaymentCreatedEvent(Payment payment) {
        PaymentCreatedEvent event = PaymentCreatedEvent.builder()
                .id(payment.getId())
                .userId(payment.getUserId())
                .paymentDate(payment.getPaymentDate())
                .paymentStatus(payment.getPaymentStatus())
                .build();

        Map<String, Object> headers = new HashMap<>();
        headers.put(TOPIC, paymentCreatedTopicProperties.getTopicName());
        headers.put(KEY, payment.getId().toString());

        paymentKafkaProducer.sendMessage(new GenericMessage<>(event, headers));
    }
}
