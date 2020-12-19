package com.solutions.datamart.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("service.twitter")
public class TwitterProperties {

    private String url;
    private String param;
    private String home_url;
    private String header;
    private String paramInitial;
}
