package com.solutions.datamart.service;

import java.util.List;

import org.springframework.social.twitter.api.TwitterProfile;

import com.solutions.datamart.entity.TwitterUser;

public interface UserProfileService {

	public TwitterProfile saveUserInfo(String screenName);

	public List<String> getAllScreenNames();
	
	public List<TwitterUser> saveUserProfile(String screenName);
	
	public List<TwitterUser> getAllTwitterUsers();
	
	public List<TwitterUser> deleteTwitterUser(long id);
	
	
}
