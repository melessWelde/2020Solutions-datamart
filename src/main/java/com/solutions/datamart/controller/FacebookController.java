package com.solutions.datamart.controller;

import static com.solutions.datamart.util.Constants.EXCEPTION_MESSAGE;
import static com.solutions.datamart.util.DateUtil.convertToDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.datamart.model.FaceBookPost;
import com.solutions.datamart.service.FacebookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FacebookController {

	@Autowired
	private FacebookService facebookService;

	@GetMapping("/allLatestPosts")
	public List<FaceBookPost> getAllLatestPosts() {
		try {
			return facebookService.getAllLatestFBPost();

		} catch (Exception e) {
			log.error(EXCEPTION_MESSAGE, "fetching data from database ", "getAllLatestPosts", e.getMessage(),
					e.getCause());
		}
		return null;
	}

	@GetMapping("/searchPosts")
	public List<FaceBookPost> searchPosts(@RequestParam("SearchKey") String SearchKey,
			@RequestParam("DateFrom") String DateFrom, @RequestParam("DateTo") String DateTo) {
		System.out.println("searchPosts.....");
		try {
			if (!StringUtils.isEmpty(SearchKey) && StringUtils.isEmpty(DateFrom) || StringUtils.isEmpty(DateTo)) {
				System.out.println("searchPostsByKey........");
				return facebookService.searchPostsByKey(SearchKey);
			} else if (!StringUtils.isEmpty(SearchKey) && !StringUtils.isEmpty(DateFrom)
					&& !StringUtils.isEmpty(DateTo)) {
				System.out.println("searchPostsByKeyAndDate........");
				return facebookService.searchPostsByKeyAndDate(SearchKey, convertToDate(DateFrom),
						convertToDate(DateTo));
			} else if (StringUtils.isEmpty(SearchKey) && !StringUtils.isEmpty(DateFrom)
					&& !StringUtils.isEmpty(DateTo)) {
				System.out.println("searchPostsBydDate........");
				return facebookService.searchPostsBydDate(convertToDate(DateFrom), convertToDate(DateTo));
			}

		} catch (Exception e) {
			log.error(EXCEPTION_MESSAGE, "fetching data from database ", "getAllLatestPosts", e.getMessage(),
					e.getCause());
		}
		return null;
	}

}