package com.solutions.datamart.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import com.solutions.datamart.model.FaceBookPost;
import com.solutions.datamart.repository.FaceBookPostRepository;
import com.solutions.datamart.service.FacebookService;

@Component
public class FacebookServiceImp implements FacebookService {
	@Autowired
	private FaceBookPostRepository fbPostRepository;

	@Value("${facebook.app-token}")
	private String fbToken;
	
	Facebook facebook;

	@Override
	public void createPosts() {
		try {
			if(facebook == null)
			   facebook = new FacebookTemplate(fbToken);
			List<Post> posts = facebook.feedOperations().getPosts().stream()
					.filter(post -> post.getCreatedTime().after(new DateTime().minusHours(248).toDate()))
					.collect(Collectors.toList());
			List<FaceBookPost> FBPosts = new ArrayList<>();
			
			for (Post post : posts) {
				FaceBookPost FBPost = new FaceBookPost();
				FBPost.setCreatedDate(post.getCreatedTime());
				FBPost.setCreatedBy(post.getName());
				FBPost.setDescription(post.getDescription());
				FBPost.setFacebookId(post.getId());
				FBPost.setMessage(post.getMessage());
				FBPost.setSource(post.getSource());
				FBPost.setLink(post.getLink());
				FBPost.setStory(post.getStory());
				FBPosts.add(FBPost);

			}
			fbPostRepository.saveAll(FBPosts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<FaceBookPost> getAllLatestFaceBookPosts() {
		return fbPostRepository.getAllLatestFaceBookPost();
	}	

	@Override
	public List<FaceBookPost> getAllFaceBookPostByDate(Date fromDate, Date toDate) {
		return fbPostRepository.getAllFaceBookPostByDate(fromDate, toDate);
	}
	

	@Override
	public List<FaceBookPost> getAllFaceBookPostByContentAndDate(String content, Date fromDate, Date toDate) {
		
		return fbPostRepository.getAllFaceBookPostContentAndDate(content, fromDate, toDate);
	}

	@Override
	public List<FaceBookPost> getAllFaceBookPostByPostContent(String content) {
		
		return fbPostRepository.getAllFaceBookPostByPostContent(content);
	}

	@Override
	public List<FaceBookPost> getAllLatestFBPost() {
		List<FaceBookPost> posts =fbPostRepository.getAllLatestFBPosts();
		return posts;
	}
	
	@Override
	public List<FaceBookPost> searchPostsByKey(String SearchKey) {
		List<FaceBookPost> posts =fbPostRepository.getFBPostsBySearchKey("%"+SearchKey+"%");
		return posts;
	}
	
	@Override
	public List<FaceBookPost> searchPostsByKeyAndDate(String SearchKey,Date DateFrom,Date DateTo) {
		List<FaceBookPost> posts =fbPostRepository.getFBPostsByKeyAndDate("%"+SearchKey+"%",DateFrom,DateTo);
		return posts;
	}
	
	@Override
	public List<FaceBookPost> searchPostsBydDate(Date DateFrom,Date DateTo) {
		List<FaceBookPost> posts =fbPostRepository.getFBPostsByDate(DateFrom,DateTo);
		return posts;
	}
	
	
}
