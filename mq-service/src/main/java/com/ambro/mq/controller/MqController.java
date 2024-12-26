package com.ambro.mq.controller;

import com.ambro.mq.service.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqController {

    private MqService mqService;

    MqController(MqService mqService){
        this.mqService = mqService;
    }

    @GetMapping("send")
    public String send(@RequestParam("msg") String msg) {
        return mqService.send(msg);
    }

    @GetMapping("recv")
    public String recv() {
        return mqService.recv();
    }
}
