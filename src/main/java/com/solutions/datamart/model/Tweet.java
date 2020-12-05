package com.solutions.datamart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Tweet implements Serializable{

	private long tweetId;
	
	private String tweetText;
	
	private Date createdDt;
	
	private List<String> hashTags = new ArrayList<>();
	
	private int retweetCount;
	
	private int favouriteCount;
	
	private String tweetUrl;
	
	
}
