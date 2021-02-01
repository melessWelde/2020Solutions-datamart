package com.solutions.datamart.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetEntity implements Serializable{

	@Id
	private Long tweetId;
	
	@Column(length = 2000)
	private String tweetText;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDt;
	
	private String hashTags;
	
	private Integer retweetCount;
	
	private Integer favouriteCount;
	
	private String tweetUrl;

	private String userName;
	//private String screenName;

}
