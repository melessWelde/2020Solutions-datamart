package com.solutions.datamart.service.impl;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.solutions.datamart.job.NewsPullerJob;
import com.solutions.datamart.model.Media;
import com.solutions.datamart.model.Property;
import com.solutions.datamart.model.Record;
import com.solutions.datamart.repository.PropertyRepository;
import com.solutions.datamart.repository.RecordRepository;
import com.solutions.datamart.service.MediaService;
import com.solutions.datamart.service.RecordService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service("recordService")
@Transactional
public class RecordServiceImpl implements RecordService {
	private static final Logger logger = LogManager.getLogger(RecordServiceImpl.class);

	@Autowired
	private RecordRepository recordRepository;

	@Autowired
	private MediaService mediaService;

	@Autowired
	private PropertyRepository propertyRepository;

	public void getAllMedias() {
		List<Media> mediaList = mediaService.getAllMedias();
		Optional<Property> property = propertyRepository.findByPropertyName("NEWS_GET_ALL");

		try {
			if (null != property && property.get().getPropertyValue().equals("ON")) {
				for (Media media : mediaList) {
					logger.info(media.getName() + " all news crawling started");

					createAllRecords(media);

					logger.info(media.getName() + " all news crawling ended");
				}
			} else {
				for (Media media : mediaList) {
					logger.info(media.getName() + " daily news crawling started");

					createDailyRecords(media);

					logger.info(media.getName() + " daily news crawling ended");
				}
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public void createAllRecords(Media media) {

		try {
			Iterator<SyndEntry> itEntries = extractNewsFromRssFeed(media);

			List<Record> records = new ArrayList<>();

			List<String> linksFromDB = recordRepository.getNewsLinkforMedia(media.getId());

			while (itEntries.hasNext()) {
				SyndEntry entry = itEntries.next();

				if (StringUtils.containsIgnoreCase(entry.getTitle(), "tigray")
						|| StringUtils.containsIgnoreCase(entry.getDescription().getValue(), "tigray")) {
					if (!linksFromDB.contains(entry.getLink())) {
						recordsObjectforDB(media, records, entry);
					}
				}
			}

			recordRepository.saveAll(records);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Iterator<SyndEntry> extractNewsFromRssFeed(Media media)
			throws MalformedURLException, IOException, FeedException {
		URL url = new URL(media.getUrl());
		HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
		// Reading the feed
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(httpcon));

		List<SyndEntry> entries = feed.getEntries();

		return entries.iterator();
	}

	private void recordsObjectforDB(Media media, List<Record> records, SyndEntry entry) {
		Record record = new Record();
		record.setTitle(entry.getTitle());
		record.setLink(entry.getLink());
		record.setDescription(entry.getDescription().toString());
		if (null != entry.getPublishedDate()) {
			record.setCreatedDate(entry.getPublishedDate());
		}

		if (null != entry.getUpdatedDate()) {
			record.setUpdatedDate(entry.getUpdatedDate());
		}

		record.setMedia(media);

		records.add(record);
	}

	public void createDailyRecords(Media media) {

		try {
			Iterator<SyndEntry> itEntries = extractNewsFromRssFeed(media);

			List<Record> records = new ArrayList<>();

			List<String> linksFromDB = recordRepository.getNewsLinkforMedia(media.getId());

			Date date = new Date();

			while (itEntries.hasNext()) {
				SyndEntry entry = itEntries.next();

				if ((null != entry.getPublishedDate() && DateUtils.isSameDay(entry.getPublishedDate(), date))
						|| (null != entry.getUpdatedDate() && DateUtils.isSameDay(entry.getUpdatedDate(), date))) {
					if (StringUtils.containsIgnoreCase(entry.getTitle(), "tigray")
							|| StringUtils.containsIgnoreCase(entry.getDescription().getValue(), "tigray")) {
						if (!linksFromDB.contains(entry.getLink())) {
							recordsObjectforDB(media, records, entry);
						}
					}
				}
			}

			recordRepository.saveAll(records);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<Record> getAllNews() {
		return recordRepository.getAllNews();
	}

	@Override
	public List<Record> getAllNewsByDate(Date fromDate, Date toDate) {
		return recordRepository.getAllNewsByDate(fromDate, toDate);
	}

	@Override
	public List<Record> getAllNewsByContentAndDate(String content, Date fromDate, Date toDate) {

		return recordRepository.getAllNewsByContentAndDate(content, fromDate, toDate);
	}

	@Override
	public List<Record> getAllNewsByContent(String content) {

		return recordRepository.getAllNewsByContent(content);
	}

}