package com.dipu.rabbitmq.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class CamelRabbitMQProcessor implements Processor {

    public void process(String fileContent, Map<String, Object> headers){
        System.out.println("From: " +headers.get("fileName")+"| Content:"+fileContent);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println(exchange);
    }
    public void check(String fileContent, Map<String, Object> headers){
        System.out.println("From: " +headers.get("fileName")+"| Content:"+fileContent);
    }
    public void enrich(Exchange exchange) throws Exception{

        String body = exchange.getIn().getBody(String.class);
        if(body!=null && !body.equals("")){
            JSONObject json = (JSONObject) JSONValue.parse(body);
            exchange.setProperty("fileName",json.getOrDefault("key","10000")+".txt");
            exchange.setProperty("key",json.getOrDefault("key",10000));
            exchange.setProperty("FileName",json.getOrDefault("key","10000")+".txt");
            exchange.getOut().setHeader("key",json.getOrDefault("key",10000));
            exchange.getOut().setHeader("fileName",json.getOrDefault("key","10000")+".txt");
            exchange.getOut().setHeader("FileName",json.getOrDefault("key","10000")+".txt");
            exchange.getOut().setHeader("camelFileName",json.getOrDefault("key","10000")+".txt");
            exchange.getOut().setHeader("CamelFileName",json.getOrDefault("key","10000")+".txt");
            exchange.getOut().setBody(body);
        }
        //return exchange;
    }
}
