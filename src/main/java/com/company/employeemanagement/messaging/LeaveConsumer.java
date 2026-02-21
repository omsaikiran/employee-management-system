package com.company.employeemanagement.messaging;

import com.company.employeemanagement.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LeaveConsumer {

    private static final Logger log = LoggerFactory.getLogger(LeaveConsumer.class);
    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void receive(String message) {
        try {
            log.info("RabbitMQ → Received notification: {}", message);
            System.out.println(" Leave Notification: " + message);

        }
       catch (Exception e){
            log.error("Failed to process message", e);
       }


    }
}
