package com.solutions.datamart.service;

import java.util.List;

public interface UserProfileService {

	public String saveUerProfile(String screenName);
	
	public List<String> getAllScreenNames();
}
