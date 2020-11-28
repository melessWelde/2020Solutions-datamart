package com.solutions.datamart.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	public static final String TWITTER_BASE_URL = "svc/v1";

	@Autowired
	private Twitter twitter;

	
	@RequestMapping("/tweets")
	public List<Tweet> getTweets(){
		
		String[] usersArray =  {"martinplaut","RAbdiAnalyst","TsedaleLemma","meazaG_"};
		List<String> usersList = Arrays.asList(usersArray);
		
		List<Tweet> tweets = new ArrayList<>();
		
		for(String user : usersList) {
			List<Tweet> tweetsL = new ArrayList<>();
			tweetsL  = twitter.timelineOperations().getUserTimeline(user);
			tweets.addAll(tweetsL);
		}
		return tweets;
	}
	
}
