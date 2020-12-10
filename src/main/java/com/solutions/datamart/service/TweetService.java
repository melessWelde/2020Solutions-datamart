package com.solutions.datamart.service;

import java.util.Date;
import java.util.List;

import com.solutions.datamart.entity.TweetEntity;

public interface TweetService {
	public void createTweetsRecord();
	
	public List<TweetEntity> getAllLatestTweets();
	
	public List<TweetEntity> getAllTweetsByUser(String userName);
	
	public List<TweetEntity> getAllTweetsByHashTag(String hashText);
	
	public List<TweetEntity> getAllTweetsByNameAndHashTag(String userName, String hashText);
	
	public List<TweetEntity> getAllTweetsByNameHashAndDate(String userName, String hashText, Date from, Date to);
	
	public List<TweetEntity> getAllTweetsByUserAndDate(String userName,Date from, Date to);
	
	public List<TweetEntity> getAllTweetsByHashTagAndDate(String hashText,Date from, Date to);

	public List<TweetEntity> getAllTweetsByDate(Date fromDate, Date toDate);
}
