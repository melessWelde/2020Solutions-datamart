package com.solutions.datamart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solutions.datamart.entity.HashTag;
import com.solutions.datamart.repository.HashTagRepository;
import com.solutions.datamart.service.HashTagService;

@Service("hashTagService")
@Transactional
public class HashTagServiceImpl implements HashTagService{

	@Autowired
	private HashTagRepository hashTagRepository;
	
	@Override
	public void saveHashTag(String tagName) {
		HashTag hashTag = new HashTag();
		hashTag.setHashTagText(tagName);
		hashTagRepository.save(hashTag);
	}

	@Override
	public List<String> getHashTags() {
		List<String> hashTexts = new ArrayList<String>();
		List<HashTag> hashTags = hashTagRepository.findAll();
		for(HashTag tagText : hashTags) {
			hashTexts.add(tagText.getHashTagText());
		}
		return hashTexts;
	}

	@Override
	public void deleteHashTag(int id) {
		hashTagRepository.deleteById(id);
		
	}

	@Override
	public List<HashTag> getAllHashTags() {
		return hashTagRepository.findAll();
	}

}
