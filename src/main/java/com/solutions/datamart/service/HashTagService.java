package com.solutions.datamart.service;

import java.util.List;

public interface HashTagService {

	public void saveHashTag(String tagName);
	
	public List<String> getHashTags();
}
