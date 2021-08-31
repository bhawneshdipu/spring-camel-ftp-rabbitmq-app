package com.dipu.rabbitmq.config;

import org.apache.camel.component.file.remote.SftpConfiguration;
import org.apache.camel.component.file.remote.SftpEndpoint;
import org.apache.camel.processor.idempotent.FileIdempotentRepository;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class CamelRabbitMQConfig {
    public static final String FILE_NAME_INCLUDE_PATTERN = "*.csv";

    @Value("${app.camel.rabbitmq.pull.path:pull/}")
    String pullPath;
    @Value("${app.camel.rabbitmq.push.path:push/}")
    String pushPath;
    @Value("${app.camel.rabbitmq.error.path:error/}")
    String errorPath;
    @Value("${app.camel.rabbitmq.done.path:done/}")
    String donePath;
    @Value("${app.camel.rabbitmq.in-progress.path:in-progress/}")
    String inProgressPath;

    @Value("${app.camel.local.pull.path:pull/}")
    String pullPathLocal;
    @Value("${app.camel.local.push.path:push/}")
    String pushPathLocal;
    @Value("${app.camel.local.error.path:error/}")
    String errorPathLocal;
    @Value("${app.camel.local.done.path:done/}")
    String donePathLocal;
    @Value("${app.camel.local.in-progress.path:in-progress/}")
    String inProgressPathLocal;


    @Value("${app.user.name:dipu}")
    String userName;
    @Value("${app.user.password:welcome@123}")
    String userPassword;

    @Value("${app.rabbitmq.hostname:localhost}")
    String rabbitmqHostname;

    @Value("${app.rabbitmq.port:22}")
    Integer rabbitmqPort;

    @PostConstruct
    public void init(){
        System.out.println("UserName:"+userName);
        System.out.println("Password:"+userPassword);
        System.out.println("push Path:"+pushPath);
        System.out.println("pull Path:"+pullPath);
        System.out.println("done Path:"+donePath);
        System.out.println("error Path:"+errorPath);
        System.out.println("in-progress Path:"+inProgressPath);
        System.out.println("FTP hostname:"+rabbitmqHostname);
        System.out.println("FTP port:"+rabbitmqPort);
    }

    public String getPullPath() {
        return pullPath;
    }

    public String getPushPath() {
        return pushPath;
    }

    public String getErrorPath() {
        return errorPath;
    }

    public String getDonePath() {
        return donePath;
    }

    public String getInProgressPath() {
        return inProgressPath;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getRabbitMQHostname() {
        return rabbitmqHostname;
    }

    public Integer getRabbitMQPort() {
        return rabbitmqPort;
    }

    @Bean(name = "pullRabbitMQUri")
    public URI pullRabbitMQUri() throws URISyntaxException {
        URI pullRabbitMQUrl = new URIBuilder()
                .setScheme("srabbitmq")
                .setHost(getRabbitMQHostname())
                .setPort(getRabbitMQPort())
                .setPath(getPullPath())
                .addParameter("username", getUserName())
                .addParameter("password", getUserPassword())
                .addParameter("passiveMode", "false")
                .addParameter("antInclude", CamelRabbitMQConfig.FILE_NAME_INCLUDE_PATTERN)
                .addParameter("initialDelay", "10s")
                .addParameter("delay", "50")
                .addParameter("moveFailed", getErrorPath()+"${headers.CamelFileName}.error")
                .addParameter("move", getDonePath()+"/${headers.CamelFileName}.pull.rabbitmq.old")
                .addParameter("preMove", getInProgressPath()+"${headers.CamelFileName}")
                .addParameter("readLock", "changed")
                .addParameter("readLockMinAge", "1m")
                .addParameter("readLockTimeout", "70000")
                .addParameter("readLockCheckInterval", "5000")
                .addParameter("stepwise", "false")
                .build();
        return pullRabbitMQUrl;
    }

    @Bean(name = "pushRabbitMQUri")
    public URI pushRabbitMQUri() throws URISyntaxException {
        URI pushRabbitMQUrl = new URIBuilder()
                .setScheme("srabbitmq")
                .setHost(getRabbitMQHostname())
                .setPort(getRabbitMQPort())
                .setPath(getPushPath())
                .addParameter("username", getUserName())
                .addParameter("password", getUserPassword())
                .addParameter("passiveMode", "false")
                .addParameter("antInclude", CamelRabbitMQConfig.FILE_NAME_INCLUDE_PATTERN)
                .addParameter("initialDelay", "10s")
                .addParameter("delay", "50")
                .addParameter("moveFailed", getErrorPath()+"${headers.CamelFileName}.error")
                .addParameter("move", getDonePath()+"/${headers.CamelFileName}.push.rabbitmq.old")
                .addParameter("preMove", getInProgressPath()+"${headers.CamelFileName}")
                .addParameter("readLock", "changed")
                .addParameter("readLockMinAge", "1m")
                .addParameter("readLockTimeout", "70000")
                .addParameter("readLockCheckInterval", "5000")
                .addParameter("stepwise", "false")
                .build();
        return pushRabbitMQUrl;
    }

    @Bean(name = "doneRabbitMQUri")
    public URI doneRabbitMQUri() throws URISyntaxException {
        URI doneRabbitMQUrl = new URIBuilder()
                .setScheme("srabbitmq")
                .setHost(getRabbitMQHostname())
                .setPort(getRabbitMQPort())
                .setPath(getDonePath())
                .addParameter("username", getUserName())
                .addParameter("password", getUserPassword())
                .addParameter("passiveMode", "false")
                .addParameter("antInclude", CamelRabbitMQConfig.FILE_NAME_INCLUDE_PATTERN)
                .addParameter("initialDelay", "10s")
                .addParameter("delay", "50")
                .addParameter("moveFailed", getErrorPath()+"${headers.CamelFileName}.error")
                .addParameter("move", getDonePath()+"/${headers.CamelFileName}.done.rabbitmq.old")
                .addParameter("preMove", getInProgressPath()+"${headers.CamelFileName}")
                .addParameter("readLock", "changed")
                .addParameter("readLockMinAge", "1m")
                .addParameter("readLockTimeout", "70000")
                .addParameter("readLockCheckInterval", "5000")
                .addParameter("stepwise", "false")
                .build();
        return doneRabbitMQUrl;
    }
    @Bean(name = "pullFileUrl")
    public StringBuilder pullFileUrl(){
        StringBuilder fileURL = new StringBuilder();
        fileURL.append("file://"+pullPathLocal);
        return fileURL;
    }
    @Bean(name = "pushFileUrl")
    public StringBuilder pushFileUrl(){
        StringBuilder fileURL = new StringBuilder();
        fileURL.append("file://"+pushPathLocal);
        return fileURL;
    }
}
