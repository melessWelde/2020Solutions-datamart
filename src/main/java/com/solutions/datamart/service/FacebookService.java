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

}
