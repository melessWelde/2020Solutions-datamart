package com.solutions.datamart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterObject;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TwitterController.TWITTER_BASE_URL)
public class TwitterController {
	
	public static final String TWITTER_BASE_URL = "svc/v1/tweets";

	@Autowired
	private Twitter twitter;

	
	@RequestMapping(value ="{userName}&result_type=recent&tweet_mode=extended")
	public List<Tweet> getTweets(@PathVariable final String userName ){
		
		//return twitter.searchOperations().search(hashTag).getTweets();
		return twitter.timelineOperations().getUserTimeline(userName);
		
	}
	
}
