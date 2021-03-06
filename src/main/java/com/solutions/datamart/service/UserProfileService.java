package com.solutions.datamart.service;

import com.solutions.datamart.dto.UserProfile;
import com.solutions.datamart.entity.TwitterUser;
import org.springframework.social.twitter.api.TwitterProfile;

import java.util.List;

public interface UserProfileService {

    TwitterProfile saveUserInfo(String screenName);

    List<TwitterUser>  saveUserInfo(UserProfile userProfile);

    List<String> getAllScreenNames();

    List<TwitterUser> saveUserProfile(String screenName);

    List<TwitterUser> saveUserProfile(UserProfile userProfile);

    List<TwitterUser> getAllTwitterUsers();

    List<TwitterUser> deleteTwitterUser(long id);


}
