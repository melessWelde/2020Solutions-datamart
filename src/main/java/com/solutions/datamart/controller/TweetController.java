package com.solutions.datamart.controller;

import com.solutions.datamart.entity.TweetEntity;
import com.solutions.datamart.service.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.solutions.datamart.util.Constants.EXCEPTION_MESSAGE;
import static com.solutions.datamart.util.DateUtil.convertToDate;

@Slf4j
@RestController
public class TweetController {

	@Autowired
	private TweetService tweetService;

	@GetMapping("/alltweets")
	public List<TweetEntity> getAllLatestTweets() {
		try {
			return tweetService.getAllLatestTweets();

		} catch (Exception e) {
			log.error(EXCEPTION_MESSAGE, "fetching data from database ","getAllLatestTweets", e.getMessage(), e.getCause());
		}
		return null;
	}

	@GetMapping("/searchalltweets")
	public List<TweetEntity> getAllTweetEntities(@RequestParam("username") String username, @RequestParam("tagtext") String tagtext, @RequestParam("fromdate") String fromdate, @RequestParam("todate") String todate) {
		try {
			if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(tagtext) && !StringUtils.isEmpty(fromdate) && !StringUtils.isEmpty(todate)) {
				return tweetService.getAllTweetsByNameHashAndDate(username, tagtext, convertToDate(fromdate), convertToDate(todate));
			} else if (!StringUtils.isEmpty(username) && StringUtils.isEmpty(tagtext) && !StringUtils.isEmpty(fromdate) && !StringUtils.isEmpty(todate)) {
				return tweetService.getAllTweetsByUserAndDate(username, convertToDate(fromdate), convertToDate(todate));
			} else if (StringUtils.isEmpty(username) && !StringUtils.isEmpty(tagtext) && !StringUtils.isEmpty(fromdate) && !StringUtils.isEmpty(todate)) {
				return tweetService.getAllTweetsByHashTagAndDate(tagtext, convertToDate(fromdate), convertToDate(todate));
			} else if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(tagtext) && StringUtils.isEmpty(fromdate) && StringUtils.isEmpty(todate)) {
				return tweetService.getAllTweetsByNameAndHashTag(username, tagtext);
			} else if (!StringUtils.isEmpty(username) && StringUtils.isEmpty(tagtext) && StringUtils.isEmpty(fromdate) && StringUtils.isEmpty(todate)) {
				return tweetService.getAllTweetsByUser(username);
			} else if (StringUtils.isEmpty(username) && !StringUtils.isEmpty(tagtext) && StringUtils.isEmpty(fromdate) && StringUtils.isEmpty(todate)) {
				return tweetService.getAllTweetsByHashTag(tagtext);
			} else if (StringUtils.isEmpty(username) && StringUtils.isEmpty(tagtext) && !StringUtils.isEmpty(fromdate) && !StringUtils.isEmpty(todate)) {
				return tweetService.getAllTweetsByDate(convertToDate(fromdate), convertToDate(todate));
			} else {
				return tweetService.getAllLatestTweets();
			}


		} catch (Exception e) {
			log.error(EXCEPTION_MESSAGE, "fetching data from database ","getAllTweetEntities", e.getMessage(), e.getCause());
		}
		return null;
	}

}
