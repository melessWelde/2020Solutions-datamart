package com.solutions.datamart.service;


import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.solutions.datamart.model.FaceBookPost;

@Component
public interface FacebookService {
	public void createPosts();
    public List<FaceBookPost> getAllLatestFaceBookPosts();	
	public List<FaceBookPost> getAllFaceBookPostByPostContent(String content);
    public List<FaceBookPost> getAllFaceBookPostByDate(Date fromDate, Date toDate);
	public List<FaceBookPost> getAllFaceBookPostByContentAndDate(String content, Date fromDate, Date toDate);

}
