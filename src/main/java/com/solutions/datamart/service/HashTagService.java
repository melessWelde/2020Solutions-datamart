package com.solutions.datamart.service;

import com.solutions.datamart.entity.HashTag;

import java.util.List;

public interface HashTagService {

    void saveHashTag(String tagName);

    List<String> getHashTags();

    List<HashTag> getAllHashTags();

    void deleteHashTag(int id);

}
