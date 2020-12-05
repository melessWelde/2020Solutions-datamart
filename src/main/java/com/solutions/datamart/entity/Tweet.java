package com.solutions.datamart.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class Tweet implements Serializable{

	@Id
	private long tweetId;
	
	private String tweetText;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDt;
	
	private String hashTags;
	
	private int retweetCount;
	
	private int favouriteCount;
	
	private String tweetUrl;
	
	
}
