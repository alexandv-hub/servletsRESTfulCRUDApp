package com.servletsRESTfulCRUDApp.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.LoggerFactory;

public class LoggingConfig {

    public static final String LOGGING_FORMAT_PATTERN = "%highlight([%-4level]) - %msg%n";

    static void configure() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern(LOGGING_FORMAT_PATTERN);
        encoder.start();

        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
        appender.setContext(context);
        appender.setEncoder(encoder);
        appender.start();

        ch.qos.logback.classic.Logger rootLogger = context.getLogger("root");
        rootLogger.detachAndStopAllAppenders();
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(appender);

        ch.qos.logback.classic.Logger swaggerLogger = context.getLogger("io.swagger");
        swaggerLogger.setLevel(Level.INFO);
    }
}

