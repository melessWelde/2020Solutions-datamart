package com.solutions.datamart.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.solutions.datamart.service.FacebookService;

@Configuration
@EnableScheduling
public class FBPostPullerJob {

	@Autowired
	private FacebookService facebookService;

	@Scheduled(fixedDelayString = "${fbpost.fixedDelay}", initialDelay = 10000)
	public void saveFBPost() {
		facebookService.createPosts();
	}
}
