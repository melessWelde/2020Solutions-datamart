package com.solutions.datamart.service.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

				System.err.println(media);

				createRecord(media);
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public void createRecord(Media media) {

		Record record = null;
		try {
			URL url = new URL("http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk#");
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
				// if (entry.getPublishedDate() == date) {
				record = new Record();
				record.setTitle(entry.getTitle());
				record.setLink(entry.getLink());
				record.setDescription(entry.getDescription().toString());
				record.setCreatedDate(entry.getPublishedDate());
				record.setUpdatedDate(entry.getUpdatedDate());

				record.setMedia(media);

				records.add(record);
				// }
			}

			recordRepository.saveAll(records);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}