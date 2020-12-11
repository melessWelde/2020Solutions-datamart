package com.solutions.datamart.controller;

import java.util.ArrayList;
import java.util.List;

import com.solutions.datamart.dto.UserProfile;
import com.solutions.datamart.dto.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solutions.datamart.entity.TweetEntity;
import com.solutions.datamart.model.HashTagEntity;
import com.solutions.datamart.model.TweetModel;
import com.solutions.datamart.repository.TweetRepository;
import com.solutions.datamart.service.UserProfileService;

@RestController
@RequestMapping(TwitterController.TWITTER_BASE_URL)
public class TwitterController {

	public static final String TWITTER_BASE_URL = "svc/v1";

	private static final String TWITTER_HOME_URL="https://twitter.com/";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	TweetRepository tweetRepository;

	@Autowired
	private Twitter twitterTemplate;

	@Autowired
	private UserProfileService userProfileService;

	private static final String GET_TWITT_BY_HASHTAG = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=martinplaut&retweet_status=false&tweet_mode=extended&q=%23tigray";


	@RequestMapping(value = "/tweets", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public String getAllTwitsByHashTag() {

		HttpHeaders headers = new HttpHeaders();

		headers.add("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAPErJwEAAAAAaVaHf%2FebQpB%2BxZ8P8SfrYu1dmJQ%3DTYaWglF2ULQYNUDa09rYhCzFx4Zr6kNZrLVEOWBBrJBWmDK560");

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		ResponseEntity<String> result = restTemplate.exchange(GET_TWITT_BY_HASHTAG, HttpMethod.GET, entity, String.class);
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

//		for (TweetModel tweetM : model) {
//			StringBuffer hashTags = new StringBuffer();
//			TweetEntity tweetE = new TweetEntity();
//			tweetE.setCreatedDt(tweetM.getCreatedAt());
//			tweetE.setFavouriteCount(tweetM.getFavoriteCount());
//			tweetE.setTweetId(tweetM.getId());
//			tweetE.setTweetText(tweetM.getText());
//			tweetE.setRetweetCount(tweetM.getRetweetCount());
//
//			for (HashTagEntity hashTag : tweetM.getEntities().getTags()) {
//				hashTags.append(hashTag.getText()).append(", ");
//			}
//			tweetE.setHashTags(hashTags.toString());
//			tweetE.setUserName(tweetM.getUser().getName());
//			if(null!= tweetM.getRetweetedStatus()) {
//				tweetE.setTweetText(tweetM.getRetweetedStatus().getText());
//			}
//			tweetE.setTweetUrl(TWITTER_HOME_URL+tweetM.getUser().getScreenName()+"/status/"+ tweetM.getId());
//			tweet.add(tweetE);
//		}
//		tweetRepository.saveAll((tweet));

		return result.getBody();

	}

	@GetMapping("/twitterProfile/{userName}")
	public TwitterProfile getTwitterProfile(@PathVariable("userName") String userName) {

		return twitterTemplate.userOperations().getUserProfile(userName);
	}

	@PostMapping("/twitterProfile")
	public ResponseEntity saveTwitterProfile(@RequestBody UserProfile userProfile) {
		UserProfileResponse userProfileResponse = userProfileService.saveUerProfile(userProfile);
		return new ResponseEntity(userProfileResponse, HttpStatus.valueOf(userProfileResponse.getStatus()));
	}
}
