package com.solutions.datamart.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.solutions.datamart.dto.UserProfile;
import com.solutions.datamart.dto.UserProfileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solutions.datamart.entity.TwitterUser;
import com.solutions.datamart.repository.TwittUserRepository;
import com.solutions.datamart.service.UserProfileService;

import static com.solutions.datamart.util.Constants.*;

@Slf4j
@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	TwittUserRepository twittUserRepository;

	@Autowired
	private Twitter twitterTemplate;

	@Override
	public UserProfileResponse saveUerProfile(UserProfile user) {
		try {
			TwitterProfile t = twitterTemplate.userOperations().getUserProfile(user.getHandleName());
			TwitterUser userProfile = new TwitterUser();
			if (null != t) {
				buildUserProfile(t, userProfile);
			} else {
				return buildResponse(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND);
			}
			twittUserRepository.save(userProfile);

		} catch (Exception e) {
			log.error(EXCEPTION_MESSAGE, "calling userOperations ","saveUerProfile", e.getMessage(), e.getCause());
			return buildResponse(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND);
		}
		return buildResponse(USER_SAVE_SUCCESSFULLY, HttpStatus.OK);
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

}
