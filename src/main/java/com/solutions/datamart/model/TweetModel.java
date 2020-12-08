package com.solutions.datamart.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.social.twitter.api.Tweet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetModel implements Serializable{

	private long id;
	@JsonProperty("full_text")
	private String text;
	@JsonProperty("created_at")
	@JsonFormat(pattern="EEE MMM dd HH:mm:ss zzz yyyy")
	private Date createdAt;
	private String fromUser;
	private String profileImageUrl;
	private Long toUserId;
	private Long inReplyToStatusId;
	private Long inReplyToUserId;
	private String inReplyToScreenName;
	private long fromUserId;
	private String languageCode;
	private String source;
	@JsonProperty("retweet_count")
	private Integer retweetCount;
	private boolean retweeted;
    @JsonProperty("retweeted_status")
	private TweetModel retweetedStatus;
	private boolean favorited;
	@JsonProperty("favorite_count")
	private Integer favoriteCount;
	
	
	
	
	@JsonProperty("entities")
    private Entities entities;
    @JsonProperty("user")
    private TwitterProfile user;
   
   
	@Override
	public String toString() {
		return "TweetModel [id=" + id + ", text=" + text + ", createdAt=" + createdAt + ", retweetCount=" + retweetCount
				+ ", favoriteCount=" + favoriteCount + "]";
	}


	public TweetModel(Entities entities, TwitterProfile user) {
		super();
		this.entities = entities;
		this.user = user;
	}


	public TweetModel() {
		super();
	}
	
	public boolean isRetweet() {
		return this.retweetedStatus != null;
	}
	
	/**
	 * Returns the unmodified text of the tweet.
	 * If this tweet is a retweet, it returns the text of the original tweet.
	 * If it is not a retweet, then this method will return the same value as {@link #getText()}.
	 * @return The unmodified text of the tweet.
	 */
	public String getUnmodifiedText() {
		return isRetweet() ? retweetedStatus.getText() : getText();
	}
	
}
