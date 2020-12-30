package com.solutions.datamart.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solutions.datamart.configuration.TwitterProperties;
import com.solutions.datamart.dto.UserProfile;
import com.solutions.datamart.dto.UserProfileResponse;
import com.solutions.datamart.entity.TweetEntity;
import com.solutions.datamart.entity.TwitterUser;
import com.solutions.datamart.model.HashTagEntity;
import com.solutions.datamart.model.TweetModel;
import com.solutions.datamart.repository.TweetRepository;
import com.solutions.datamart.repository.TwittUserRepository;
import com.solutions.datamart.service.HashTagService;
import com.solutions.datamart.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.solutions.datamart.util.Constants.*;

@Slf4j
@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private TwittUserRepository twittUserRepository;

    private Twitter twitterTemplate;

    private RestTemplate restTemplate;
    private TwitterProperties properties;
    private ObjectMapper objectMapper;
    private TweetRepository tweetRepository;

    private HashTagService hashTagService;

    private List<String> hashTexts = new ArrayList<>();

    @Autowired
    public UserProfileServiceImpl(TwittUserRepository twittUserRepository, Twitter twitterTemplate,
                                  RestTemplate restTemplate, TwitterProperties properties, ObjectMapper objectMapper,
                                  TweetRepository tweetRepository, HashTagService hashTagService) {
        super();
        this.twittUserRepository = twittUserRepository;
        this.twitterTemplate = twitterTemplate;
        this.restTemplate = restTemplate;
        this.properties = properties;
        this.objectMapper = objectMapper;
        this.tweetRepository = tweetRepository;
        this.hashTagService = hashTagService;
    }

    @Override
    public TwitterProfile saveUserInfo(String userName) {
        try {
            TwitterProfile t = twitterTemplate.userOperations().getUserProfile(userName);
            TwitterUser userProfile = new TwitterUser();
            List<TweetEntity> finalTweets = new ArrayList<>();
            if (null != t) {
                buildUserProfile(t, userProfile);
                hashTexts = hashTagService.getHashTags();
                twittUserRepository.save(userProfile);

                ResponseEntity<String> result = restTemplate.exchange(buildUrl(userProfile.getScreenName()), HttpMethod.GET,
                        getHttpEntity(), String.class);
                List<TweetEntity> tweets = getListOfTweet(result.getBody());
                tweets.stream().filter(Objects::nonNull).filter(this::isHashTag).forEach(finalTweets::add);
            }
            if (!finalTweets.isEmpty()) {
                tweetRepository.saveAll((finalTweets));
            }
            return t;
        } catch (Exception e) {
            log.error(EXCEPTION_MESSAGE, "calling userOperations ", "saveUerProfile", e.getMessage(), e.getCause());
            return null;
        }
    }

    @Override
    public List<TwitterUser> saveUserInfo(UserProfile userProfile) {
        userProfile.getHandleName().forEach(this::saveUserInfo);
        return twittUserRepository.findAll();
    }

    private void buildUserProfile(TwitterProfile t, TwitterUser userProfile) {
        userProfile.setName(t.getName());
        userProfile.setScreenName(t.getScreenName());
        userProfile.setUserUrl(t.getProfileUrl());
        userProfile.setDescription(t.getDescription());
        userProfile.setUserId(t.getId());
        userProfile.setLocation(t.getLocation());
        userProfile.setImageUrl(t.getProfileImageUrl());
    }

    private UserProfileResponse buildResponse(String message, HttpStatus status) {
        return UserProfileResponse.builder().message(message).status(status.value()).build();
    }

    @Override
    public List<String> getAllScreenNames() {
        List<TwitterUser> twitterUsers = twittUserRepository.findAll();
        List<String> screenNames = new ArrayList<>();
        for (TwitterUser user : twitterUsers) {
            screenNames.add(user.getScreenName());
        }
        return screenNames;
    }

    @Override
    public List<TwitterUser> saveUserProfile(String screenName) {
        saveUser(screenName);
        return twittUserRepository.findAll();
    }

    private void saveUser(String screenName) {
        TwitterProfile tProfile = twitterTemplate.userOperations().getUserProfile(screenName);
        try {
            TwitterUser userProfile = new TwitterUser();
            if (null != tProfile) {
                buildUserProfile(tProfile, userProfile);
            }
            twittUserRepository.save(userProfile);

        } catch (Exception e) {
            log.error(EXCEPTION_MESSAGE, e.getMessage(), e.getCause(), e);
        }
    }

    @Override
    public List<TwitterUser> saveUserProfile(UserProfile userProfile) {
        userProfile.getHandleName().forEach(this::saveUser);
        return twittUserRepository.findAll();
    }

    @Override
    public List<TwitterUser> getAllTwitterUsers() {

        return twittUserRepository.findAll();
    }

    @Override
    public List<TwitterUser> deleteTwitterUser(long id) {
        twittUserRepository.deleteById(id);
        return twittUserRepository.findAll();

    }

    private String buildUrl(String screenNames) {
        return properties.getUrl() + screenNames + AND + properties.getParamInitial();
    }

    private boolean isHashTag(TweetEntity tweetEntity) {
        return hashTexts.stream().anyMatch(s -> tweetEntity.getTweetText().contains(s));
    }

    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH, properties.getHeader());
        return new HttpEntity<>(PARAMS, headers);
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
            log.error(EXCEPTION_MESSAGE, "parsing twitter response ", "getListOfTweet", e.getMessage(), e.getCause());
        }
        return tweets;

    }

    public TweetEntity buildTweetObject(TweetModel tweetM) {
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
}
