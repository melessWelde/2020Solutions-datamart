package com.solutions.datamart.service.impl;

import static com.solutions.datamart.util.Constants.EXCEPTION_MESSAGE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solutions.datamart.dto.UserProfileResponse;
import com.solutions.datamart.entity.TwitterUser;
import com.solutions.datamart.repository.TwittUserRepository;
import com.solutions.datamart.service.UserProfileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	TwittUserRepository twittUserRepository;

	@Autowired
	private Twitter twitterTemplate;

	@Override
	public TwitterProfile saveUserInfo(String userName) {
		try {
			TwitterProfile t = twitterTemplate.userOperations().getUserProfile(userName);
			TwitterUser userProfile = new TwitterUser();
			if (null != t) {
				buildUserProfile(t, userProfile);
			}
			twittUserRepository.save(userProfile);
			return t;
		} catch (Exception e) {
			log.error(EXCEPTION_MESSAGE, "calling userOperations ","saveUerProfile", e.getMessage(), e.getCause());
			return null;
		}
	}

	private void buildUserProfile(TwitterProfile t, TwitterUser userProfile) {
		userProfile.setName(t.getName());
		userProfile.setScreenName(t.getScreenName());
		userProfile.setUserUrl(t.getProfileUrl());
		userProfile.setDescription(t.getDescription());
		userProfile.setUserId(t.getId());
		userProfile.setLocation(t.getLocation());
		userProfile.setImageUrl(t.getProfileImageUrl());
	}

	private UserProfileResponse buildResponse(String message, HttpStatus status) {
		return UserProfileResponse.builder()
				.message(message)
				.status(status.value())
				.build();
	}

	@Override
	public List<String> getAllScreenNames() {
		List<TwitterUser> twitterUsers = twittUserRepository.findAll();
		List<String> screenNames = new ArrayList<>();
		for (TwitterUser user : twitterUsers) {
			screenNames.add(user.getScreenName());
		}
		return screenNames;
	}

	@Override
	public List<TwitterUser> saveUserProfile(String screenName) {
		TwitterProfile tProfile = twitterTemplate.userOperations().getUserProfile(screenName);
		try {
			TwitterUser userProfile = new TwitterUser();
			if (null != tProfile) {
				buildUserProfile(tProfile, userProfile);
			} 
			twittUserRepository.save(userProfile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	 return twittUserRepository.findAll();
	}

	@Override
	public List<TwitterUser> getAllTwitterUsers() {

		return twittUserRepository.findAll();
	}

	@Override
	public List<TwitterUser> deleteTwitterUser(long id) {
		twittUserRepository.deleteById(id);
		return twittUserRepository.findAll();
		
	}
}
