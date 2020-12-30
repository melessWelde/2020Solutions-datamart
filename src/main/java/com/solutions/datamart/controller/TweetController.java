package com.solutions.datamart.controller;

import com.solutions.datamart.dto.HashTagRequest;
import com.solutions.datamart.dto.UserProfile;
import com.solutions.datamart.entity.HashTag;
import com.solutions.datamart.entity.TweetEntity;
import com.solutions.datamart.entity.TwitterUser;
import com.solutions.datamart.service.HashTagService;
import com.solutions.datamart.service.TweetService;
import com.solutions.datamart.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.solutions.datamart.util.Constants.EXCEPTION_MESSAGE;
import static com.solutions.datamart.util.Constants.EXCEPTION_MSG;
import static com.solutions.datamart.util.DateUtil.convertToDate;

@Slf4j
@RestController
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @Autowired
    private HashTagService hashTagService;

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/twitterProfile/{userName}")
    public TwitterProfile getTwitterProfile(@PathVariable("userName") String userName) {
        return userProfileService.saveUserInfo(userName);
    }

    @PostMapping("/twitterProfiles")
    public  List<TwitterUser>  saveUserInfos(@RequestBody UserProfile userProfile) {
         return userProfileService.saveUserInfo(userProfile);

    }
    @GetMapping("/tweets")
    public List<TweetEntity> getAllLatestTweets() {
        try {
            return tweetService.getAllLatestTweets();

        } catch (Exception e) {
            log.error(EXCEPTION_MESSAGE, "fetching data from database ", "getAllLatestTweets", e.getMessage(), e.getCause());
        }
        return null;
    }

    @GetMapping("/searchtweets")
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
            log.error(EXCEPTION_MESSAGE, "fetching twitter data from database ", "getAllTweetEntities", e.getMessage(), e.getCause());
        }
        return null;
    }


    @PostMapping("/hashtag")
    public void saveHashTag(@RequestParam("hashText") String hashText) {
        try {
            hashTagService.saveHashTag(hashText);
        } catch (Exception e) {
			log.error(EXCEPTION_MSG, e.getMessage(), e.getCause(), e);
        }
    }
	@PostMapping("/hashtags")
	public void saveHashTags(@RequestBody HashTagRequest hashText) {
		try {
			hashTagService.saveHashTags(hashText);
		} catch (Exception e) {
			log.error(EXCEPTION_MSG, e.getMessage(), e.getCause(), e);
		}
	}
    @GetMapping("/hastags")
    public List<HashTag> getAllHashTags() {
        try {
            return hashTagService.getAllHashTags();
        } catch (Exception e) {
            log.error(EXCEPTION_MSG, e.getMessage(), e.getCause(), e);
        }
        return new ArrayList<>();
    }

    @DeleteMapping("/deleteHashtag")
    public void deleteHashTag(@RequestBody HashTag hashTag) {
        try {
            hashTagService.deleteHashTag(hashTag.getId());
        } catch (Exception e) {
			log.error(EXCEPTION_MSG, e.getMessage(), e.getCause(), e);
        }
    }

    @PostMapping("/twitterUser")
    public List<TwitterUser> saveUserProfile(@RequestParam("username") String username) {
        try {
            return userProfileService.saveUserProfile(username);
        } catch (Exception e) {
			log.error(EXCEPTION_MSG, e.getMessage(), e.getCause(), e);
        }
		return new ArrayList<>();
    }

    @PostMapping("/twitterUsers")
    public List<TwitterUser> saveListOfUserProfile(@RequestBody UserProfile userProfile) {
        try {
            return userProfileService.saveUserProfile(userProfile);
        } catch (Exception e) {
			log.error(EXCEPTION_MSG, e.getMessage(), e.getCause(), e);
        }
		return new ArrayList<>();
    }

    @DeleteMapping("/deleteProfile")
    public List<TwitterUser> deleteProfile(@RequestBody TwitterUser twitterUser) {
        return userProfileService.deleteTwitterUser(twitterUser.getUserId());
    }

    @GetMapping("/twitterUsers")
    public List<TwitterUser> getAllTwitterUsers() {
        return userProfileService.getAllTwitterUsers();
    }
}
