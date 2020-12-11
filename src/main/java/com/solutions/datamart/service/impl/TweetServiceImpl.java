package com.solutions.datamart.service.impl;

import java.util.*;

import com.solutions.datamart.configuration.TwitterProperties;
import lombok.extern.slf4j.Slf4j;
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

import static com.solutions.datamart.util.Constants.*;

@Slf4j
@Service("tweetService")
@Transactional
public class TweetServiceImpl implements TweetService {

	private RestTemplate restTemplate;
	private TwitterProperties properties;
	private ObjectMapper objectMapper;
	private TweetRepository tweetRepository;
	private UserProfileService userProfileService;
	private HashTagService hashTagService;


	@Autowired
	public TweetServiceImpl(RestTemplate restTemplate, TwitterProperties properties, ObjectMapper objectMapper, TweetRepository tweetRepository, UserProfileService userProfileService, HashTagService hashTagService) {
		this.restTemplate = restTemplate;
		this.properties = properties;
		this.objectMapper = objectMapper;
		this.tweetRepository = tweetRepository;
		this.userProfileService = userProfileService;
		this.hashTagService = hashTagService;
	}

	public void createTweetsRecord() {
		ResponseEntity<String> result;

		for (String screenName : userProfileService.getAllScreenNames()) {
			result = restTemplate.exchange(buildUrl(screenName), HttpMethod.GET, getHttpEntity(), String.class);
			List<TweetEntity> tweets = getListOfTweet(result.getBody());
			List<TweetEntity> finalTweets = new ArrayList<>();
			tweets.stream().filter(Objects::nonNull)
					.filter(this::isHashTag)
					.forEach(o -> finalTweets.add(o));

			if (!finalTweets.isEmpty()) {
				tweetRepository.saveAll((finalTweets));
			}
		}
	}

	private HttpEntity<String> getHttpEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(AUTH, properties.getHeader());
		return new HttpEntity<>(PARAMS, headers);
	}

	private boolean isHashTag(TweetEntity tweetEntity) {
		List<String> hashTag = new ArrayList<>(Arrays.asList(tweetEntity.getHashTags().split(SPLIT)));
		return hashTagService.getHashTags().containsAll(hashTag);
	}

	private List<TweetEntity> getListOfTweet(String json) {
		List<TweetEntity> tweets = new ArrayList<>();
		try {
			TweetModel[] model = objectMapper.readValue(json, TweetModel[].class);
			for (TweetModel tweetM : model) {
				TweetEntity tweetE = buildTweetObject(tweetM);
				tweets.add(tweetE);
			}
		} catch (Exception e) {
			log.error(EXCEPTION_MESSAGE, "parsing twitter response ","getListOfTweet", e.getMessage(), e.getCause());
		}
		return tweets;

	}

	private TweetEntity buildTweetObject(TweetModel tweetM) {
		return TweetEntity.builder()
				.createdDt(tweetM.getCreatedAt())
				.favouriteCount(tweetM.getFavoriteCount())
				.tweetId(tweetM.getId())
				.tweetText(null != tweetM.getRetweetedStatus() ? tweetM.getRetweetedStatus().getText() : tweetM.getText())
				.retweetCount(tweetM.getRetweetCount())
				.hashTags(getHashTag(tweetM))
				.userName(tweetM.getUser().getName())
				.tweetUrl(properties.getHome_url() + tweetM.getUser().getScreenName() + STATUS_PATH + tweetM.getId())
				.build();
	}

	private String getHashTag(TweetModel tweetM) {
		StringBuffer hashTags = new StringBuffer();
		for (HashTagEntity hashTag : tweetM.getEntities().getTags()) {
			hashTags.append(hashTag.getText()).append(SPLIT);
		}
		return hashTags.toString();
	}

	private String buildUrl(String screenNames) {
		return properties.getUrl() + screenNames + AND + properties.getParam();
	}

	@Override
	public List<TweetEntity> getAllLatestTweets() {
		List<TweetEntity> tweets =tweetRepository.getAllLatestTweets();
		return tweets;
	}

	@Override
	public List<TweetEntity> getAllTweetsByUser(String userName) {
		return tweetRepository.getAllTweetsByUser(userName);
	}

	@Override
	public List<TweetEntity> getAllTweetsByHashTag(String hashText) {
		return tweetRepository.getAllTweetsByHashTag(hashText);
	}

	@Override
	public List<TweetEntity> getAllTweetsByNameAndHashTag(String userName, String hashText) {
		return tweetRepository.getAllTweetsByNameAndHashTag(userName, hashText);
	}

	@Override
	public List<TweetEntity> getAllTweetsByNameHashAndDate(String userName, String hashText, Date from, Date toDate) {
		return tweetRepository.getAllTweetsByNameHashAndDate(userName, hashText, from, toDate);
	}

	@Override
	public List<TweetEntity> getAllTweetsByUserAndDate(String userName, Date fromDate, Date toDate) {
		return tweetRepository.getAllTweetsByUserAndDate(userName, fromDate, toDate);
	}

	@Override
	public List<TweetEntity> getAllTweetsByHashTagAndDate(String hashText, Date fromDate, Date toDate) {
		return tweetRepository.getAllTweetsByHashTagAndDate(hashText, fromDate, toDate);
	}

	@Override
	public List<TweetEntity> getAllTweetsByDate(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}
}
