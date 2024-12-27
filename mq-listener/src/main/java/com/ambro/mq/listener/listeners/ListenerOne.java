package com.ambro.mq.listener.listeners;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ListenerOne implements MessageListener {

    @JmsListener(destination = "${ibm.mq.queue}")
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String actualMessage = textMessage.getText();
            System.out.println(actualMessage);
        } catch (JMSException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
