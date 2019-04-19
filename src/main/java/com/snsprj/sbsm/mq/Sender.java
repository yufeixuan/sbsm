package com.snsprj.sbsm.mq;

import java.util.Date;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(){

        String context = "hello " + new Date();

        rabbitTemplate.convertAndSend(context);
    }
}
