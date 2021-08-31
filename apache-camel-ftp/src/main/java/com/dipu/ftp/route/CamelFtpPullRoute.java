package com.dipu.ftp.route;

import com.dipu.ftp.processor.CamelFtpProcessor;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URI;


@Component
public class CamelFtpPullRoute extends RouteBuilder {

    @Autowired
    @Qualifier("pullFtpUri")
    private URI pullFtpUri;

    @Autowired
    @Qualifier("pushFtpUri")
    private URI pushFtpUri;

    @Autowired
    @Qualifier("doneFtpUri")
    private URI doneFtpUri;

    @Autowired
    @Qualifier("pullFileUrl")
    private StringBuilder pullFileUrl;

    @Autowired
    @Qualifier("pushFileUrl")
    private StringBuilder pushFileUrl;

    @Autowired
    private Processor processor;

    @Override
    public void configure() throws Exception {
        from(pushFtpUri.toString())
                .setHeader("useId").simple("test-push-123")
                .setHeader("fileName").simple("${file:name}")
                .bean(CamelFtpProcessor.class, "process(${body}, ${headers})")
                //.process(processor)
                .log("Downloaded file from push file  ${file:name} complete.")
                .to(pushFileUrl.toString())
                .log("Moving to done ftp: file ${file:name} complete.")
                .to(doneFtpUri.toString());
    }
}
