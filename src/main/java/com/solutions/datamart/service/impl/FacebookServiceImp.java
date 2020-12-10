package com.solutions.datamart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import com.solutions.datamart.model.FBPost;
import com.solutions.datamart.repository.FBPostRepository;
import com.solutions.datamart.service.FacebookService;

@Component
public class FacebookServiceImp implements FacebookService {
	@Autowired
	private FBPostRepository fbPostRepository;

	@Value("${facebook.app-token}")
	private String fbToken;
	
	Facebook facebook;

	@Scheduled(fixedRate = 100000)
	public void getPosts() {
		try {
			if(facebook == null)
			   facebook = new FacebookTemplate(fbToken);
			List<Post> posts = facebook.feedOperations().getPosts().stream()
					.filter(post -> post.getCreatedTime().after(new DateTime().minusHours(48).toDate()))
					.collect(Collectors.toList());
			List<FBPost> FBPosts = new ArrayList<>();
			
			for (Post post : posts) {
				FBPost FBPost = new FBPost();
				FBPost.setCreatedDate(post.getCreatedTime());
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

}
