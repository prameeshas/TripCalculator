package com.littlepaydemo.tripcalculator.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("app")
@Component
@Data
public class ApplicationConfiguration {

    private char csvSeparator;
    private String outputFile;


    public ApplicationConfiguration() {
    }

    public ApplicationConfiguration(char csvSeparator, String outputFile) {
        this.csvSeparator = csvSeparator;
        this.outputFile = outputFile;
    }
}
