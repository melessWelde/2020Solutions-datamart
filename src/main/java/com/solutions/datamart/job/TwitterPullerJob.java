package com.solutions.datamart.job;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.solutions.datamart.model.Property;
import com.solutions.datamart.repository.PropertyRepository;
import com.solutions.datamart.service.TweetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableScheduling
public class TwitterPullerJob {

	@Autowired
	private TweetService tweetService;

	@Autowired
	private PropertyRepository propertyRepository;

	@Scheduled(fixedDelayString = "${service.twitter.fixedDelay}", initialDelay = 7 * 60 * 1000)
	public void saveTweets() {

		if (propertyRepository.findByPropertyName("TWEETS_JOB").isPresent()) {
			Optional<Property> property = propertyRepository.findByPropertyName("TWEETS_JOB");

			if (property.get().getPropertyValue().equals("ON")) {

				// log.info("News batch has been started at: {}", new Date());
				tweetService.createTweetsRecord();
				// log.info("News batch has been completed at: {}", new Date());

			} else {
				log.info("Batch has been disabled in the property table for the TWEETS_JOB as OFF ");
			}

		} else {
			log.info("There has not been an entry for the TWEETS_JOB in the property table");
		}
	}
}
