package com.dipu.ftp.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class CamelFtpProcessor implements Processor {

    public void process(String fileContent, Map<String, Object> headers){
        System.out.println("From: " +headers.get("fileName")+"| Content:"+fileContent);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println(exchange);
        System.out.println("From: " +exchange.getFromEndpoint().getEndpointUri() + " InBody: " + exchange.getIn().getBody() + "| OutBody:"+exchange.getOut().getBody());
    }
}
