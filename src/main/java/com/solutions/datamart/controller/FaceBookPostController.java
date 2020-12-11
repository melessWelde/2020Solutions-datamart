package com.solutions.datamart.controller;

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

import com.solutions.datamart.entity.TweetEntity;
import com.solutions.datamart.model.FaceBookPost;
import com.solutions.datamart.service.FacebookService;
import com.solutions.datamart.service.TweetService;

@RestController
public class FaceBookPostController {

	@Autowired
	private FacebookService facebookService;
	
	@GetMapping("/allFBPosts")
	public List<FaceBookPost> getAllFaceBookPosts(){
		try {
			return facebookService.getAllLatestFaceBookPosts();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	@GetMapping("/searchallfacebookpost")
	public List<FaceBookPost> getAllTweetEntities(@RequestParam("content") String content, @RequestParam("fromdate") String fromdate,@RequestParam("todate") String todate) {
		try 
		{
			Date toDate = StringUtils.isEmpty(todate)? new Date(): convertToDate(todate);
			
			if(!StringUtils.isEmpty(content) && !StringUtils.isEmpty(fromdate) && !StringUtils.isEmpty(todate)) {
				return facebookService.getAllFaceBookPostByContentAndDate(content, convertToDate(fromdate), convertToDate(todate));
			}else if(!StringUtils.isEmpty(content) && StringUtils.isEmpty(fromdate) && StringUtils.isEmpty(todate)) {
				return facebookService.getAllFaceBookPostByDate(convertToDate(fromdate), toDate);
			} 
			else if( !StringUtils.isEmpty(fromdate) && !StringUtils.isEmpty(todate)) {
				return facebookService.getAllFaceBookPostByDate(convertToDate(fromdate), toDate);
			} else {
				return facebookService.getAllLatestFaceBookPosts();
			}

		} catch (Exception e) {
			e.printStackTrace();
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
