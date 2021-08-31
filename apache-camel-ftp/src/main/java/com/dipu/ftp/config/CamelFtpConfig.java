package com.dipu.ftp.config;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class CamelFtpConfig {
    public static final String FILE_NAME_INCLUDE_PATTERN = "*.csv";

    @Value("${app.camel.ftp.pull.path:pull/}")
    String pullPath;
    @Value("${app.camel.ftp.push.path:push/}")
    String pushPath;
    @Value("${app.camel.ftp.error.path:error/}")
    String errorPath;
    @Value("${app.camel.ftp.done.path:done/}")
    String donePath;
    @Value("${app.camel.ftp.in-progress.path:in-progress/}")
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

    @Value("${app.ftp.hostname:localhost}")
    String ftpHostname;

    @Value("${app.ftp.port:22}")
    Integer ftpPort;

    @PostConstruct
    public void init(){
        System.out.println("UserName:"+userName);
        System.out.println("Password:"+userPassword);
        System.out.println("push Path:"+pushPath);
        System.out.println("pull Path:"+pullPath);
        System.out.println("done Path:"+donePath);
        System.out.println("error Path:"+errorPath);
        System.out.println("in-progress Path:"+inProgressPath);
        System.out.println("FTP hostname:"+ftpHostname);
        System.out.println("FTP port:"+ftpPort);
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

    public String getFtpHostname() {
        return ftpHostname;
    }

    public Integer getFtpPort() {
        return ftpPort;
    }

    @Bean(name = "pullFtpUri")
    public URI pullFtpUri() throws URISyntaxException {
        URI pullFtpUrl = new URIBuilder()
                .setScheme("sftp")
                .setHost(getFtpHostname())
                .setPort(getFtpPort())
                .setPath(getPullPath())
                .addParameter("username", getUserName())
                .addParameter("password", getUserPassword())
                .addParameter("passiveMode", "false")
                .addParameter("antInclude", CamelFtpConfig.FILE_NAME_INCLUDE_PATTERN)
                .addParameter("initialDelay", "10s")
                .addParameter("delay", "50")
                .addParameter("moveFailed", getErrorPath()+"${headers.CamelFileName}.error")
                .addParameter("move", getDonePath()+"/${headers.CamelFileName}.pull.ftp.old")
                .addParameter("preMove", getInProgressPath()+"${headers.CamelFileName}")
                .addParameter("readLock", "changed")
                .addParameter("readLockMinAge", "1m")
                .addParameter("readLockTimeout", "70000")
                .addParameter("readLockCheckInterval", "5000")
                .addParameter("stepwise", "false")
                .build();
        return pullFtpUrl;
    }

    @Bean(name = "pushFtpUri")
    public URI pushFtpUri() throws URISyntaxException {
        URI pushFtpUrl = new URIBuilder()
                .setScheme("sftp")
                .setHost(getFtpHostname())
                .setPort(getFtpPort())
                .setPath(getPushPath())
                .addParameter("username", getUserName())
                .addParameter("password", getUserPassword())
                .addParameter("passiveMode", "false")
                .addParameter("antInclude", CamelFtpConfig.FILE_NAME_INCLUDE_PATTERN)
                .addParameter("initialDelay", "10s")
                .addParameter("delay", "50")
                .addParameter("moveFailed", getErrorPath()+"${headers.CamelFileName}.error")
                .addParameter("move", getDonePath()+"/${headers.CamelFileName}.push.ftp.old")
                .addParameter("preMove", getInProgressPath()+"${headers.CamelFileName}")
                .addParameter("readLock", "changed")
                .addParameter("readLockMinAge", "1m")
                .addParameter("readLockTimeout", "70000")
                .addParameter("readLockCheckInterval", "5000")
                .addParameter("stepwise", "false")
                .build();
        return pushFtpUrl;
    }

    @Bean(name = "doneFtpUri")
    public URI doneFtpUri() throws URISyntaxException {
        URI doneFtpUrl = new URIBuilder()
                .setScheme("sftp")
                .setHost(getFtpHostname())
                .setPort(getFtpPort())
                .setPath(getDonePath())
                .addParameter("username", getUserName())
                .addParameter("password", getUserPassword())
                .addParameter("passiveMode", "false")
                .addParameter("antInclude", CamelFtpConfig.FILE_NAME_INCLUDE_PATTERN)
                .addParameter("initialDelay", "10s")
                .addParameter("delay", "50")
                .addParameter("moveFailed", getErrorPath()+"${headers.CamelFileName}.error")
                .addParameter("move", getDonePath()+"/${headers.CamelFileName}.done.ftp.old")
                .addParameter("preMove", getInProgressPath()+"${headers.CamelFileName}")
                .addParameter("readLock", "changed")
                .addParameter("readLockMinAge", "1m")
                .addParameter("readLockTimeout", "70000")
                .addParameter("readLockCheckInterval", "5000")
                .addParameter("stepwise", "false")
                .build();
        return doneFtpUrl;
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
