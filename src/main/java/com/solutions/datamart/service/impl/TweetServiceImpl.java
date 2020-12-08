package com.solutions.datamart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solutions.datamart.entity.TweetEntity;
import com.solutions.datamart.model.HashTagEntity;
import com.solutions.datamart.model.TweetModel;
import com.solutions.datamart.repository.TweetRepository;
import com.solutions.datamart.service.HashTagService;
import com.solutions.datamart.service.TweetService;
import com.solutions.datamart.service.UserProfileService;

@Service("tweetService")
@Transactional
public class TweetServiceImpl implements TweetService {

	private static final String TWITTER_HOME_URL = "https://twitter.com/";

	private static final String GET_TWITT_BASE_URL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";

	private static final String TWEET_PARAMS = "&retweet_status=false&tweet_mode=extended";
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	TweetRepository tweetRepository;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private HashTagService hashTagService;

	public void createTweetsRecord() {
		HttpHeaders headers = new HttpHeaders();

		headers.add("Authorization",
				"Bearer AAAAAAAAAAAAAAAAAAAAAPErJwEAAAAAaVaHf%2FebQpB%2BxZ8P8SfrYu1dmJQ%3DTYaWglF2ULQYNUDa09rYhCzFx4Zr6kNZrLVEOWBBrJBWmDK560");

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		ResponseEntity<String> result = null;
		for (String screenName : userProfileService.getAllScreenNames()) {
			result = restTemplate.exchange(buildUrl(screenName), HttpMethod.GET, entity, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
			objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			objectMapper.setVisibility(PropertyAccessor.CREATOR, Visibility.ANY);
			String json = result.getBody();
			TweetModel[] model = null;
			List<TweetEntity> tweet = new ArrayList<>();
			try {
				model = objectMapper.readValue(json, TweetModel[].class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (TweetModel tweetM : model) {
				for (String hashText : hashTagService.getHashTags()) {
					if (null != tweetM.getRetweetedStatus()) {
						if (!tweetM.getRetweetedStatus().getText().contains(hashText)) {
							continue;
						}
					} else if (!tweetM.getText().contains(hashText)) {
						continue;
					} else {
						TweetEntity tweetE = buildTweetObject(tweetM);
						tweet.add(tweetE);
						break;
					}
				}

			}
			if (!tweet.isEmpty()) {
				tweetRepository.saveAll((tweet));
			}
		}
	}

	private TweetEntity buildTweetObject(TweetModel tweetM) {
		StringBuffer hashTags = new StringBuffer();
		TweetEntity tweetE = new TweetEntity();
		tweetE.setCreatedDt(tweetM.getCreatedAt());
		tweetE.setFavouriteCount(tweetM.getFavoriteCount());
		tweetE.setTweetId(tweetM.getId());
		tweetE.setTweetText(tweetM.getText());
		tweetE.setRetweetCount(tweetM.getRetweetCount());

		for (HashTagEntity hashTag : tweetM.getEntities().getTags()) {
			hashTags.append(hashTag.getText()).append(", ");
		}
		tweetE.setHashTags(hashTags.toString());
		tweetE.setUserName(tweetM.getUser().getName());
		if (null != tweetM.getRetweetedStatus()) {
			tweetE.setTweetText(tweetM.getRetweetedStatus().getText());
		}
		tweetE.setTweetUrl(TWITTER_HOME_URL + tweetM.getUser().getScreenName() + "/status/" + tweetM.getId());
		return tweetE;
	}

	private String buildUrl(String screenNames) {

		return GET_TWITT_BASE_URL + screenNames + TWEET_PARAMS;
	}
}
