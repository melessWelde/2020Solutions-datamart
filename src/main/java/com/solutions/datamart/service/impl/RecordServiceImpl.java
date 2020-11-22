package com.solutions.datamart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solutions.datamart.model.Media;
import com.solutions.datamart.model.Record;
import com.solutions.datamart.service.RecordService;

@Service("recordService")
@Transactional
public class RecordServiceImpl implements RecordService {
	@Autowired
	private RecordDao recordDao;

	public long createRecord(Media media) throws IOException, IllegalArgumentException, FeedException {
		String filePath;
		Document doc = null;
		Record record = null;
		URL url = new URL("http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk#");
		HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
		// Reading the feed
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(httpcon));
		@SuppressWarnings("unchecked")
		List<SyndEntry> entries = feed.getEntries();
		Iterator<SyndEntry> itEntries = entries.iterator();
		Date today = Calendar.getInstance().getTime();
		List<Record> records = new ArrayList<>();
		// getting all records
		List<Record> drRecords = recordDao.getAllRecords();
		List<String> links = new ArrayList<>();
		for (int i = 0; i < drRecords.size(); i++) {
			links.add(drRecords.get(i).getLink());
		}

		while (itEntries.hasNext()) {
			SyndEntry entry = itEntries.next();

			if (!links.contains(entry.getLink())) {
				record = new Record();
				record.setTitle(entry.getTitle());
				record.setLink(entry.getLink());
				record.setDescription(entry.getDescription().toString());
				record.setDate(today);
				record.setMedia(media);

				records.add(record);
			}
		}
		return recordDao.createRecord(records);
	}

}