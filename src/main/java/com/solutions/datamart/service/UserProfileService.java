package com.solutions.datamart.service;

import com.solutions.datamart.dto.UserProfile;
import com.solutions.datamart.dto.UserProfileResponse;

import java.util.List;

public interface UserProfileService {

	UserProfileResponse saveUerProfile(UserProfile screenName);

	List<String> getAllScreenNames();
}
