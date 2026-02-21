package com.company.employeemanagement.service;

import com.company.employeemanagement.config.RabbitConfig;
import com.company.employeemanagement.entity.LeaveRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class RabbitService {

    private final RabbitTemplate rabbitTemplate;
    private static final Logger log = LoggerFactory.getLogger(RabbitService.class);


    public void sendLeaveNotification(LeaveRequest leave) {

        String message =
                "Leave " + leave.getStatus()
                        + " for employee ID "
                        + leave.getEmployee().getId();

        try {
            log.info("Publishing message to queue [{}]: {}", RabbitConfig.QUEUE, message);

            rabbitTemplate.convertAndSend(RabbitConfig.QUEUE, message);

            log.info("Message published successfully");

        } catch (Exception e) {
            log.error("Failed to publish message for employee {}",
                    leave.getEmployee().getId(), e);
        }
    }




}

