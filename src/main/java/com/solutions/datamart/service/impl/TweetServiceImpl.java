package com.solutions.datamart.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solutions.datamart.Cache.CacheUtil;
import com.solutions.datamart.configuration.TwitterProperties;
import com.solutions.datamart.entity.TweetEntity;
import com.solutions.datamart.model.HashTagEntity;
import com.solutions.datamart.model.TweetModel;
import com.solutions.datamart.repository.TweetRepository;
import com.solutions.datamart.service.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.solutions.datamart.util.Constants.*;
import static com.solutions.datamart.util.DataMartUtil.addDays;
import static com.solutions.datamart.util.DataMartUtil.same;

@Slf4j
@Service("tweetService")
@Transactional
public class TweetServiceImpl implements TweetService {

    private RestTemplate restTemplate;
    private TwitterProperties properties;
    private ObjectMapper objectMapper;
    private TweetRepository tweetRepository;
    private CacheUtil cacheUtil;

    private List<String> hashTexts = new ArrayList<>();

    @Autowired
    public TweetServiceImpl(RestTemplate restTemplate, TwitterProperties properties, ObjectMapper objectMapper, TweetRepository tweetRepository, CacheUtil cacheUtil) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        this.objectMapper = objectMapper;
        this.tweetRepository = tweetRepository;
        this.cacheUtil = cacheUtil;
    }

    public void createTweetsRecord() {
        ResponseEntity<String> result;
        hashTexts = cacheUtil.retrieveHashTagFromCache();
        for (String screenName : cacheUtil.retrieveUserProfileFromCache()) {
            try {
                result = restTemplate.exchange(buildUrl(screenName), HttpMethod.GET, getHttpEntity(), String.class);
                List<TweetEntity> tweets = getListOfTweet(result.getBody());
                List<TweetEntity> finalTweets = new ArrayList<>();
                tweets.stream().filter(Objects::nonNull)
                        .filter(this::isHashTag)
                        .forEach(finalTweets::add);

                if (!finalTweets.isEmpty()) {
                    tweetRepository.saveAll((finalTweets));
                }
            } catch (Exception e) {
                log.error(EXCEPTION_MESSAGE, "getting data from twitter ", "createTweetsRecord", e.getMessage(), e.getCause());
            }

        }
    }
   
    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH, properties.getHeader());
        return new HttpEntity<>(PARAMS, headers);
    }

    private boolean isHashTag(TweetEntity tweetEntity) {
        return hashTexts.stream().anyMatch(s -> tweetEntity.getTweetText().contains(s));
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

    private String buildUrl(String screenNames) {
        return properties.getUrl() + screenNames + AND + properties.getParam();
    }

    @Override
    public List<TweetEntity> getAllLatestTweets() {
        return tweetRepository.getAllLatestTweets();
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
        if(same(from,toDate)){
            return tweetRepository.getAllTweetsByNameHashAndDate(userName, hashText, from, addDays(toDate,1));
        }
        return tweetRepository.getAllTweetsByNameHashAndDate(userName, hashText, from, toDate);
    }

    @Override
    public List<TweetEntity> getAllTweetsByUserAndDate(String userName, Date fromDate, Date toDate) {
        if(same(fromDate,toDate)){
            return tweetRepository.getAllTweetsByUserAndDate(userName, fromDate, addDays(toDate,1));
        }
        return tweetRepository.getAllTweetsByUserAndDate(userName, fromDate, toDate);
    }

    @Override
    public List<TweetEntity> getAllTweetsByHashTagAndDate(String hashText, Date fromDate, Date toDate) {
        if(same(fromDate,toDate)){
            return tweetRepository.getAllTweetsByHashTagAndDate(hashText, fromDate, addDays(toDate,1));
        }
        return tweetRepository.getAllTweetsByHashTagAndDate(hashText, fromDate, toDate);
    }

    @Override
    public List<TweetEntity> getAllTweetsByDate(Date fromDate, Date toDate) {
        if(same(fromDate,toDate)){
            return tweetRepository.getAllTweetsByDate(fromDate, addDays(toDate,1));
        }
        return tweetRepository.getAllTweetsByDate(fromDate, toDate);
    }

    @Override
    public List<TweetEntity> getAllTweetsByHandleName(String handleName) {
        return tweetRepository.getAllTweetsByHandleName(handleName);
    }

    @Override
    public List<TweetEntity> getAllTweetsByHandleNameAndDate(String handleName, Date from, Date to) {
        if(same(from,to)){
            return tweetRepository.getAllTweetsByHandleNameAndDate(handleName,from,addDays(to,1));
        }
        return tweetRepository.getAllTweetsByHandleNameAndDate(handleName,from,to);
    }


}
