package com.solutions.datamart.job;

import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.solutions.datamart.model.Property;
import com.solutions.datamart.repository.PropertyRepository;
import com.solutions.datamart.repository.RecordRepository;
import com.solutions.datamart.service.RecordService;

@Configuration
@EnableScheduling
public class NewsPullerJob {
	private static final Logger logger = LogManager.getLogger(NewsPullerJob.class);

	@Autowired
	private RecordService recordService;

	@Autowired
	private PropertyRepository propertyRepository;

	@Scheduled(fixedDelayString = "${news.fixedDelay}", initialDelay = 10000)
	public void saveNews() {

		if (propertyRepository.findByPropertyName("NEWS_JOB").isPresent()) {
			Optional<Property> property = propertyRepository.findByPropertyName("NEWS_JOB");

			if (property.get().getPropertyValue().equals("ON")) {

				logger.info("News batch has been started at: {}", new Date());
				recordService.getAllMedias();
				logger.info("News batch has been completed at: {}", new Date());

			} else {
				logger.info("Batch has been disabled in the property table for the NEWS_JOB as OFF ");
			}

		} else {
			logger.info("There has not been an entry for the NEWS_JOB in the property table");
		}
	}
}