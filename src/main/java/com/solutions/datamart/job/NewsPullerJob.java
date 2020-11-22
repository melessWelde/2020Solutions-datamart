package com.solutions.datamart.job;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.solutions.datamart.model.Media;
import com.solutions.datamart.service.MediaService;
import com.solutions.datamart.service.RecordService;

public class NewsPullerJob {
	private static final Logger logger = LogManager.getLogger(NewsPullerJob.class);

	@Autowired
	private RecordService recordService;

	@Autowired
	private MediaService mediaService;

	/* @Scheduled(fixedRate = 60 * 60 * 1000, initialDelay = 10 * 60 * 1000) */
	public void saveNews() {
		List<Media> mediaList = mediaService.getAllMedias();

		try {
			for (Media media : mediaList) {
				recordService.createRecord(media);
			}

		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
		}
	}
}