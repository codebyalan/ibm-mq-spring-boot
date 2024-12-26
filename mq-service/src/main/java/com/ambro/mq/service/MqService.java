package com.ambro.mq.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service

public class MqService {

    @Value("${ibm.mq.queue}")
    private String queue;

//    @Autowired
    private JmsTemplate jmsTemplate;

    MqService(JmsTemplate jmsTemplate){
        this.jmsTemplate = jmsTemplate;
    }

    public JSONObject sendMessage(String message) {
        JSONObject res = new JSONObject();
        try {
            jmsTemplate.convertAndSend(queue, message);
            res.put("status", "OK");
            return res;
        } catch (JmsException e) {
            System.out.println(e.getStackTrace());
            res.put("status", "FAILED");
            return res;
        }
    }


    public String send(String message) {
        try {
            jmsTemplate.convertAndSend(queue, message);
            return "{ \"message\" : \"Message Sent: "  + message +"\" }";
        } catch (JmsException ex) {
            ex.printStackTrace();
            return "{ \"message\" : \"Some errors occured on sending the message: "+ message+ "\" }";
        }
    }

    public String recv() {
        try {
            String msg = jmsTemplate.receiveAndConvert(queue).toString();
            return "{ \"message\" : \"Message Received: "  + msg + "\" }";
        } catch (JmsException ex) {
            ex.printStackTrace();
            return "{ \"message\" : \"Error on receiving the message\" }";
        }
    }
}
