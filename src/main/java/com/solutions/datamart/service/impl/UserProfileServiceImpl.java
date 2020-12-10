package com.solutions.datamart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solutions.datamart.entity.TwitterUser;
import com.solutions.datamart.repository.TwittUserRepository;
import com.solutions.datamart.service.UserProfileService;

@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	TwittUserRepository twittUserRepository;

	@Autowired
	private Twitter twitterTemplate;

	@Override
	public String saveUerProfile(String screenName) {
		TwitterProfile t = twitterTemplate.userOperations().getUserProfile(screenName);
		try {
			TwitterUser userProfile = new TwitterUser();
			if (null != t) {
				userProfile.setName(t.getName());
				userProfile.setScreenName(t.getScreenName());
				userProfile.setUserUrl(t.getProfileUrl());
				userProfile.setDescription(t.getDescription());
				userProfile.setUserId(t.getId());
				userProfile.setLocation(t.getLocation());
				userProfile.setImageUrl(t.getProfileImageUrl());
			} else {
				return "user profile doesn't exist with this screenName. please provide correct one";
			}
			twittUserRepository.save(userProfile);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "User Profile successfully saved!";
	}

	@Override
	public List<String> getAllScreenNames() {
		List<TwitterUser> twitterUsers = twittUserRepository.findAll();
		List<String> screenNames = new ArrayList<String>();
		for (TwitterUser user : twitterUsers) {
			screenNames.add(user.getScreenName());
		}
		return screenNames;
	}

}
