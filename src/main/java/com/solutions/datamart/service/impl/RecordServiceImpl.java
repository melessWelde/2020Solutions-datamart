package com.solutions.datamart.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.solutions.datamart.model.Media;
import com.solutions.datamart.model.Property;
import com.solutions.datamart.model.Record;
import com.solutions.datamart.repository.PropertyRepository;
import com.solutions.datamart.repository.RecordRepository;
import com.solutions.datamart.service.MediaService;
import com.solutions.datamart.service.RecordService;

import static com.solutions.datamart.util.Constants.EXCEPTION_MESSAGE;
import static com.solutions.datamart.util.DataMartUtil.addDays;
import static com.solutions.datamart.util.DataMartUtil.same;

@Service("recordService")
@Transactional
public class RecordServiceImpl implements RecordService {
	private static final Logger logger = LogManager.getLogger(RecordServiceImpl.class);
	public static final String EXTRA_STRING = "SyndContentImpl.mode=null\nSyndContentImpl.type=text/html\nSyndContentImpl.interface=interface com.rometools.rome.feed.synd.SyndContent\nSyndContentImpl.value=";

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
			if (property.isPresent() && property.get().getPropertyValue().equals("ON")) {
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
			logger.error(EXCEPTION_MESSAGE, "NA","getAllMedias",e.getMessage(), e.getStackTrace() );
		}
	}

	public void createAllRecords(Media media) {

		try {
			Iterator<SyndEntry> itEntries = extractNewsFromRssFeed(media);

			List<Record> records = new ArrayList<>();

			List<String> linksFromDB = recordRepository.getNewsLinkforMedia(media.getId());

			while (itEntries.hasNext()) {
				SyndEntry entry = itEntries.next();

				if (shouldCreateRecord(entry)) {
					if (!linksFromDB.contains(entry.getLink())) {
						recordsObjectforDB(media, records, entry);
					}
				}
			}

			recordRepository.saveAll(records);
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, "NA","createAllRecords",e.getMessage(), e.getStackTrace() );
		}

	}

	private Iterator<SyndEntry> extractNewsFromRssFeed(Media media)
			throws  IOException, FeedException {
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
					if (shouldCreateRecord(entry)) {
						if (!linksFromDB.contains(entry.getLink())) {
							recordsObjectforDB(media, records, entry);
						}
					}
				}
			}

			recordRepository.saveAll(records);
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, "NA","createAllRecords",e.getMessage(), e.getStackTrace() );
		}

	}

	@Override
	public List<Record> getAllNews() {

		List<Record> records =recordRepository.getAllNews();
		return updateDesc(records);
	}

	private List<Record> updateDesc(List<Record> records) {
		records.forEach(o -> o.setDescription(o.getDescription().replace(EXTRA_STRING,"")));
		 return records;
	}

	@Override
	public List<Record> getAllNewsByDate(Date fromDate, Date toDate) {
		if(same(fromDate,toDate)){
			return recordRepository.getAllNewsByDate(fromDate,addDays(toDate,1));
		}
		return recordRepository.getAllNewsByDate(fromDate, toDate);
	}

	@Override
	public List<Record> getAllNewsByContentAndDate(String content, Date fromDate, Date toDate) {
		if(same(fromDate,toDate)) {
			return recordRepository.getAllNewsByContentAndDate(content, fromDate,addDays(toDate,1));
		}
		return recordRepository.getAllNewsByContentAndDate(content, fromDate, toDate);

	}

	@Override
	public List<Record> getAllNewsByContent(String content) {
		return recordRepository.getAllNewsByContent(content);
	}

	private boolean shouldCreateRecord(SyndEntry entry) {
		return StringUtils.containsIgnoreCase(entry.getTitle(), "tigray")
				|| StringUtils.containsIgnoreCase(entry.getDescription().getValue(), "tigray")
				|| StringUtils.containsIgnoreCase(entry.getTitle(), "Ethiopia")
				|| StringUtils.containsIgnoreCase(entry.getDescription().getValue(), "Ethiopia");
	}
}