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
	
	@Scheduled(fixedRate = 60 * 60000, initialDelay = 60000)
	public void saveTweets() {
		tweetService.createTweetsRecord();
	}
}
