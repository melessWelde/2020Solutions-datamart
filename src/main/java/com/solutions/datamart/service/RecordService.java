package com.solutions.datamart.service;

import java.util.Date;
import java.util.List;

import com.solutions.datamart.model.Media;
import com.solutions.datamart.model.Record;

public interface RecordService {

	public void createRecord(Media media);

	public void getAllMedias();

	public List<Record> getAllNews();

	public List<Record> getAllNewsByContent(String content);

	public List<Record> getAllNewsByDate(Date fromDate, Date toDate);

	public List<Record> getAllNewsByContentAndDate(String content, Date fromDate, Date toDate);

}
