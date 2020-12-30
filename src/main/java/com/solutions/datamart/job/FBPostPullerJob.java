package com.solutions.datamart.job;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.solutions.datamart.model.Property;
import com.solutions.datamart.repository.PropertyRepository;
import com.solutions.datamart.service.FacebookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableScheduling
public class FBPostPullerJob {

	@Autowired
	private FacebookService facebookService;

	@Autowired
	private PropertyRepository propertyRepository;

	@Scheduled(fixedDelayString = "${service.facebook.fixedDelay}", initialDelay = 0)
	public void saveFBPost() {
		
		if (propertyRepository.findByPropertyName("FACEBOOK_JOB").isPresent()) {
			Optional<Property> property = propertyRepository.findByPropertyName("FACEBOOK_JOB");

			if (property.get().getPropertyValue().equals("ON")) {
				log.info("FaceBook batch has been started at: {}", new Date());

				facebookService.createPosts();

				log.info("FaceBook batch has been end at: {}", new Date());

			}else {
				log.info("Batch has been disabled in the property table for the FACEBOOK_JOB as OFF ");
			}

		} else {
			log.info("There has not been an entry for the FACEBOOK_JOB in the property table");
		}
	}
}
