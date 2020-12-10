package com.solutions.datamart.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.solutions.datamart.service.MediaService;
import com.solutions.datamart.service.RecordService;

@Configuration
@EnableScheduling
public class NewsPullerJob {
	private static final Logger logger = LogManager.getLogger(NewsPullerJob.class);

	@Autowired
	private RecordService recordService;

	@Autowired
	private MediaService mediaService;

	@Scheduled(fixedRate = 60 * 1000, initialDelay = 1000)
	public void saveNews() {
		recordService.getAllMedias();
	}
}