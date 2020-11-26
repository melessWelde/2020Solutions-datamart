package com.solutions.datamart;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestClient {
	
	private static final String GET_TWITT_BY_HASHTAG = "https://api.twitter.com/1.1/search/tweets.json?q=%23tigray&result_type=recent&tweet_mode=extended";

	static RestTemplate restTemplate = new RestTemplate();

	
	
	private void getAllTwitsByHashTag() {
		HttpHeaders headers  = new HttpHeaders();
		
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<String> result  = restTemplate.exchange(GET_TWITT_BY_HASHTAG, HttpMethod.GET, entity, String.class);
		
		System.out.println(result);
		
	}
	
}


