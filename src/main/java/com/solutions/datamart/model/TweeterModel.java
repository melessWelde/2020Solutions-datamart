package com.solutions.datamart.model;

import java.util.Date;
import java.util.List;

import org.springframework.social.twitter.api.HashTagEntity;

public class TweeterModel {
	private Date created_ts;
	private String tweet_text;
	private String user;
	private List<HashTagEntity> allHashtags;
	private int retweetCount;
	private int followerCount;
	
	public TweeterModel() {
		
	}
	public Date getCreated_ts() {
		return created_ts;
	}
	public void setCreated_ts(Date created_ts) {
		this.created_ts = created_ts;
	}
	public String getTweet_text() {
		return tweet_text;
	}
	public void setTweet_text(String tweet_text) {
		this.tweet_text = tweet_text;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public List<HashTagEntity> getAllHashtags() {
		return allHashtags;
	}
	public void setAllHashtags(List<HashTagEntity> list) {
		this.allHashtags = list;
	}
	public int getRetweetCount() {
		return retweetCount;
	}
	public void setRetweetCount(int retweet_count) {
		this.retweetCount = retweet_count;
	}
	public int getFollowerCount() {
		return followerCount;
	}
	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}
	
	}
