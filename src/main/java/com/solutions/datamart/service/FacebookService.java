package com.solutions.datamart.service;


import com.solutions.datamart.model.FaceBookPost;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface FacebookService {
    void createPosts();

    List<FaceBookPost> getAllLatestFaceBookPosts();

    List<FaceBookPost> getAllFaceBookPostByPostContent(String content);

    List<FaceBookPost> getAllFaceBookPostByDate(Date fromDate, Date toDate);

    List<FaceBookPost> getAllFaceBookPostByContentAndDate(String content, Date fromDate, Date toDate);
    
   // public void getPosts();
	public List<FaceBookPost> getAllLatestFBPost();
	public List<FaceBookPost> searchPostsByKey(String SearchKey);
	public List<FaceBookPost> searchPostsByKeyAndDate(String SearchKey,Date DateFrom,Date DateTo);
	public List<FaceBookPost> searchPostsBydDate(Date DateFrom,Date DateTo);

}
