package com.solutions.datamart.service;

import java.util.List;

import com.solutions.datamart.entity.HashTag;

public interface HashTagService {

	public void saveHashTag(String tagName);
	
	public List<String> getHashTags();
	
	public List<HashTag> getAllHashTags();
	
	public void deleteHashTag(int id);
	
}
