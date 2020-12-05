package com.solutions.datamart.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.solutions.datamart.model.TweeterModel;
import com.solutions.datamart.service.TwitterModification;

@RestController
@RequestMapping(TwitterController.TWITTER_BASE_URL)
public class TwitterController {
	
	public static final String TWITTER_BASE_URL = "svc/v1";

	@Autowired
	private TwitterModification twitter;

	@Autowired
	private RestTemplate restTemplate;
	
//	url:https://api.twitter.com/1.1/search/tweets.json?q=%23tigray&result_type=recent&tweet_mode=extended
	private static final String GET_TWITT_BY_HASHTAG = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=martinplaut&retweet_status=false&tweet_mode=extended&q=%23tigray";
	
	@RequestMapping("/tweets")
	public List<TweeterModel> getTweets(){
		//PathVariable pv = new PathVariable();
		String[] usersArray = {"martinplaut","RAbdiAnalyst","TsedaleLemma","meazaG_"};
		List<String> usersList = Arrays.asList(usersArray);
		
		List<Tweet> tweets = new ArrayList<>();
		List<TweeterModel> allTweets = new ArrayList<>();
		//modifiedTimelineOperation.setUserAuthorized(false);
		
		//modifiedTimelineOperation.setAppAuthorized(true);
		for(String user : usersList) {
			List<Tweet> tweetsL = new ArrayList<>();
			tweetsL = twitter.timelineOperations().getUserTimeline(user);
			//tweetsL = twitter.timelineOperations().getStatus();
			//twitter.timelineOperations().getStatus();
			tweets.addAll(tweetsL);
		}
		
		for(Tweet t : tweets) {
			TweeterModel tm = new TweeterModel();
			tm.setCreated_ts(t.getCreatedAt());
			tm.setTweet_text(t.getText());
			tm.setUser(t.getFromUser());
			tm.setAllHashtags(t.getEntities().getHashTags());
			tm.setRetweetCount(t.getRetweetCount());
			tm.setFollowerCount(t.getUser().getFollowersCount());
			allTweets.add(tm);
		}
		return allTweets;
	}
	
	@RequestMapping(value = "/tweetsss", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAllTwitsByHashTag() {
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAPErJwEAAAAAaVaHf%2FebQpB%2BxZ8P8SfrYu1dmJQ%3DTYaWglF2ULQYNUDa09rYhCzFx4Zr6kNZrLVEOWBBrJBWmDK560");
		
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<String> result = restTemplate.exchange(GET_TWITT_BY_HASHTAG, HttpMethod.GET, entity, String.class);
		
		return result.getBody();
		
	}
	
}
