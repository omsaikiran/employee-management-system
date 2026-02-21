package com.company.employeemanagement.messaging;

import com.company.employeemanagement.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@RequiredArgsConstructor
public class LeaveProducer {
    private static final Logger log = LoggerFactory.getLogger(LeaveProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE, message);
        log.info("RabbitMQ → Received notification: {}", message);
    }
}

