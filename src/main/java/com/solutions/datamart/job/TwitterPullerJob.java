package com.solutions.datamart.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.solutions.datamart.service.TweetService;

@Configuration
@EnableScheduling
public class TwitterPullerJob {

	@Autowired
	private TweetService tweetService;
	
	@Scheduled(fixedRate = 30*60*1000, initialDelay = 5*60*1000)
	public void saveTweets() {
		tweetService.createTweetsRecord();
	}
}
