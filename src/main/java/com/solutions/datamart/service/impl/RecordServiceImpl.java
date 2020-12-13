package com.solutions.datamart.service.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.solutions.datamart.model.Media;
import com.solutions.datamart.model.Record;
import com.solutions.datamart.repository.RecordRepository;
import com.solutions.datamart.service.MediaService;
import com.solutions.datamart.service.RecordService;

@Service("recordService")
@Transactional
public class RecordServiceImpl implements RecordService {
	@Autowired
	private RecordRepository recordRepository;

	@Autowired
	private MediaService mediaService;

	public void getAllMedias() {
		List<Media> mediaList = mediaService.getAllMedias();

		try {
			for (Media media : mediaList) {

				System.err.println(media.getName() + " Crawling started");

				createRecord(media);
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public void createRecord(Media media) {

		Record record = null;
		try {
			URL url = new URL(media.getUrl());
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			// Reading the feed
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(httpcon));

			List<SyndEntry> entries = feed.getEntries();
			Iterator<SyndEntry> itEntries = entries.iterator();

			List<Record> records = new ArrayList<>();

			Date date = new Date();

			while (itEntries.hasNext()) {
				SyndEntry entry = itEntries.next();

				if (DateUtils.isSameDay(entry.getPublishedDate(), date)
						|| DateUtils.isSameDay(entry.getUpdatedDate(), date)) {
					if (StringUtils.containsIgnoreCase(entry.getTitle(), "tigray")
							|| StringUtils.containsIgnoreCase(entry.getDescription().getValue(), "tigray")) {
						record = new Record();
						record.setTitle(entry.getTitle());
						record.setLink(entry.getLink());
						// record.setDescription(entry.getDescription().toString());
						record.setCreatedDate(entry.getPublishedDate());
						record.setUpdatedDate(entry.getUpdatedDate());

						record.setMedia(media);

						records.add(record);
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