package com.rse.mobile.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MobileWebserviceApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobileWebserviceApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Starting MobileWebserviceApplication...");
        SpringApplication.run(MobileWebserviceApplication.class, args);
        LOGGER.info("MobileWebserviceApplication has started.");
    }
}