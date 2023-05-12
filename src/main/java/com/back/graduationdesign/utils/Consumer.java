package com.back.graduationdesign.utils;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Consumer {

    @RabbitHandler
    @RabbitListener(queuesToDeclare  = @Queue("test"))
    public void testHandler(Map<String,Object> message){
        System.out.println("message = " + message);
    }
}
