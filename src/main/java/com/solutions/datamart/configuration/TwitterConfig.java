package com.solutions.datamart.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TwitterConfig {
	@Value("${spring.social.twitter.appId}")
	private String consumerKey;
	@Value("${spring.social.twitter.appSecret}")
	private String consumerSecret;
	@Value("${twitter.access.token}")
	private String accessToken;
	@Value("${twitter.access.token.secret}")
	private String accessTokenSecret;
	@Bean
	TwitterTemplate twitterTemplate(){
		return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
