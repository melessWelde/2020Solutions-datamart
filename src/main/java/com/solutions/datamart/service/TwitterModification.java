package com.solutions.datamart.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.support.URIBuilder;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class TwitterModification  extends TwitterTemplate{
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${spring.social.twitter.appId}")
	private String consumerKey;
	@Value("${spring.social.twitter.appSecret}")
	private String consumerSecret;
	@Value("${twitter.access.token}")
	private String accessToken;
	@Value("${twitter.access.token.secret}")
	private String accessTokenSecret;

	public TwitterModification(String consumerKey, String consumerSecret, String accessToken,
			String accessTokenSecret) {
		super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		// TODO Auto-generated constructor stub
	}

	public List<Tweet> getUserTimeline(String screenName) {
		return getUserTimeline(screenName, 20, 0, 0);
	}

	public List<Tweet> getUserTimeline(String screenName, int pageSize) {
		return getUserTimeline(screenName, pageSize, 0, 0);
	}

	public List<Tweet> getUserTimeline(String screenName, int pageSize, long sinceId, long maxId) {
//		requireEitherUserOrAppAuthorization();
		MultiValueMap<String, String> parameters = buildPagingParametersWithCount(pageSize, sinceId, maxId);
		parameters.set("screen_name", screenName);
		parameters.set("include_entities", "true");
		parameters.set("tweet_mode", "extended");
		return restTemplate.getForObject(buildUri("statuses/user_timeline.json", parameters), TweetList.class);
	}
	
	public static MultiValueMap<String, String> buildPagingParametersWithCount(int page, int pageSize, long sinceId, long maxId) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("page", String.valueOf(page));
		parameters.set("count", String.valueOf(pageSize));
		if (sinceId > 0) {
			parameters.set("since_id", String.valueOf(sinceId));
		}
		if (maxId > 0) {
			parameters.set("max_id", String.valueOf(maxId));
		}
		return parameters;
	}

	public static MultiValueMap<String, String> buildPagingParametersWithCount(int pageSize, long sinceId, long maxId) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("count", String.valueOf(pageSize));
		if (sinceId > 0) {
			parameters.set("since_id", String.valueOf(sinceId));
		}
		if (maxId > 0) {
			parameters.set("max_id", String.valueOf(maxId));
		}
		return parameters;
	}

	protected URI buildUri(String path) {
		return buildUri(path, EMPTY_PARAMETERS);
	}
	
	protected URI buildUri(String path, String parameterName, String parameterValue) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set(parameterName, parameterValue);
		return buildUri(path, parameters);
	}
	
	protected URI buildUri(String path, MultiValueMap<String, String> parameters) {
		return URIBuilder.fromUri(API_URL_BASE + path).queryParams(parameters).build();
	}
	
	private static final String API_URL_BASE = "https://api.twitter.com/1.1/";

	private static final LinkedMultiValueMap<String, String> EMPTY_PARAMETERS = new LinkedMultiValueMap<String, String>();
	
	@SuppressWarnings("serial")
	private static class TweetList extends ArrayList<Tweet> {}
}
