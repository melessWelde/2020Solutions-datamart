package com.solutions.datamart.controller;

import static com.solutions.datamart.util.Constants.EXCEPTION_MESSAGE;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.datamart.model.Record;
import com.solutions.datamart.service.RecordService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class NewsController {

	@Autowired
	private RecordService recordService;

	@GetMapping("/news")
	public List<Record> getAllNews() {
		try {
			return recordService.getAllNews();

		} catch (Exception e) {
			log.error(EXCEPTION_MESSAGE, "fetching data from database ", "getAllNews", e.getMessage(), e.getCause());
		}
		return null;
	}

	@GetMapping("/searchnews")
	public List<Record> searchNews(@RequestParam(required = false) String content,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate) {
		try {
			Date toDate = StringUtils.isEmpty(todate) ? new Date() : convertToDate(todate);

			if (!StringUtils.isEmpty(content) && !StringUtils.isEmpty(fromdate) && !StringUtils.isEmpty(todate)) {
				return recordService.getAllNewsByContentAndDate(content, convertToDate(fromdate),
						convertToDate(todate));

			} else if (!StringUtils.isEmpty(fromdate) && !StringUtils.isEmpty(todate)) {
				return recordService.getAllNewsByDate(convertToDate(fromdate), toDate);

			} else if (!StringUtils.isEmpty(content)) {
				return recordService.getAllNewsByContent(content);

			} else {
				return recordService.getAllNews();
			}

		} catch (Exception e) {
			log.error(EXCEPTION_MESSAGE, "fetching data from database ", "searchNews", e.getMessage(), e.getCause());
		}
		return null;
	}

	private Date convertToDate(String inputDate) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(inputDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(date);
		return date;
	}

}
