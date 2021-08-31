package com.dipu.rabbitmq.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Log4j
@Controller
public class CamelController {
    private final Random random=new Random();
    private static int count=0;
    @RequestMapping("/from/{data}")
    public ResponseEntity<String> index(@PathVariable("data") String data){
        String response="{ \"message\":\"Hi,"+data+random.nextInt()+"  visiting : "+count++ +"\", \"key\":"+count+" }";
        log.info(response);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }
}
