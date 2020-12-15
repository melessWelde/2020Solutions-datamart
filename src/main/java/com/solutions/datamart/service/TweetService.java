package com.solutions.datamart.service;

import com.solutions.datamart.entity.TweetEntity;

import java.util.Date;
import java.util.List;

public interface TweetService {
    void createTweetsRecord();

    List<TweetEntity> getAllLatestTweets();

    List<TweetEntity> getAllTweetsByUser(String userName);

    List<TweetEntity> getAllTweetsByHashTag(String hashText);

    List<TweetEntity> getAllTweetsByNameAndHashTag(String userName, String hashText);

    List<TweetEntity> getAllTweetsByNameHashAndDate(String userName, String hashText, Date from, Date to);

    List<TweetEntity> getAllTweetsByUserAndDate(String userName, Date from, Date to);

    List<TweetEntity> getAllTweetsByHashTagAndDate(String hashText, Date from, Date to);

    List<TweetEntity> getAllTweetsByDate(Date fromDate, Date toDate);
}
