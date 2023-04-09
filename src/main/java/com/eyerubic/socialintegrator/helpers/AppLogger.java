package com.eyerubic.socialintegrator.helpers;

import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.sql.Timestamp;

/**
 * This class writes different logs to files. Those are application log, request/response log
 * and activity log. It uses log4j library. Log file configurations are defined
 * under resources/log4j2.xml file
 */
@Component
public class AppLogger {

    private static final String INFO = "INFO";
    private static final String DEBUG = "DEBUG";
    private static final String WARN = "WARN";
    private static final String ERROR = "ERROR";

    private String seperator = "|";

    private String userIdentifier;
    private String endpoint;
    private String uniqueId;
    private String httpMethod;
    private String requestIp;
    private String hostIp;
    
    private static final Logger applicationLogger = LogManager.getLogger("AppLog");
    private static final Logger apiReqResLogger = LogManager.getLogger("ApiReqResLog");
    private static final Logger actLogger = LogManager.getLogger("ActLog");
    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    public AppLogger() {
        // Do nothing because
    }

    @Bean
    @RequestScope
    public AppLogger requestScopedBeanAppLogger() {
        return new AppLogger();
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public String getUserIdentifier() {
        return this.userIdentifier;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpMethod() {
        return this.httpMethod;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getRequestIp() {
        return this.requestIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getHostIp() {
        return this.hostIp;
    }

    /**
     * Writes application log
     */
    private void writeLog(String message, String level) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat logTime = new SimpleDateFormat(DATEFORMAT);
        String logString = logTime.format(timestamp) 
            + this.seperator + this.userIdentifier
            + this.seperator + this.uniqueId
            + this.seperator + this.httpMethod
            + this.seperator + this.endpoint
            + this.seperator + level
            + this.seperator + this.requestIp
            + this.seperator + this.hostIp
            + this.seperator + message;

        switch (level) {
            case INFO:
                applicationLogger.info(logString);
                break;
            
            case ERROR:
                applicationLogger.error(logString);
                break;

            case DEBUG:
                applicationLogger.debug(logString);
                break;

            case WARN:
                applicationLogger.warn(logString);
                break;

            default:
                // Do Nothing
                break;
        }
    }

    /**
     * Writes request/response log
     */
    public void writeApiReqResLog(String message) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat logTime = new SimpleDateFormat(DATEFORMAT);
        String logString = logTime.format(timestamp) 
            + this.seperator + this.userIdentifier
            + this.seperator + this.uniqueId
            + this.seperator + this.httpMethod
            + this.seperator + this.endpoint
            + this.seperator + this.requestIp
            + this.seperator + this.hostIp
            + this.seperator + message;

        apiReqResLogger.info(logString);
    }

    /**
     * Writes activity log
     */
    public void writeActivityLog(String message, String activityType) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat logTime = new SimpleDateFormat(DATEFORMAT);
        String logString = logTime.format(timestamp) 
            + this.seperator + this.userIdentifier
            + this.seperator + this.uniqueId
            + this.seperator + activityType
            + this.seperator + this.endpoint
            + this.seperator + this.requestIp
            + this.seperator + this.hostIp
            + this.seperator + message;

        actLogger.info(logString);
    }

    public void info(String message) {
        writeLog(message, INFO);
    }

    public void debug(String message) {
        writeLog(message, DEBUG);
    }

    public void warn(String message) {
        writeLog(message, WARN);
    }

    public void error(String message) {
        writeLog(message, ERROR);
    }
}
