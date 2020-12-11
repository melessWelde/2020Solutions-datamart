package com.solutions.datamart.service;

import com.solutions.datamart.dto.UserProfile;
import com.solutions.datamart.dto.UserProfileResponse;

import java.util.List;

import org.springframework.social.twitter.api.TwitterProfile;

public interface UserProfileService {

	UserProfileResponse saveUerProfile(UserProfile screenName);

	List<String> getAllScreenNames();
	
	public TwitterProfile saveUserProfile(String screenName);
}
