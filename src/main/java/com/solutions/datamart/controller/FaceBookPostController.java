package com.solutions.datamart.controller;

import com.solutions.datamart.model.FaceBookPost;
import com.solutions.datamart.service.FacebookService;
import lombok.extern.slf4j.Slf4j;
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

import com.solutions.datamart.model.FaceBookPost;
import com.solutions.datamart.service.FacebookService;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
import static com.solutions.datamart.util.Constants.EXCEPTION_MESSAGE;
import static com.solutions.datamart.util.DateUtil.convertToDate;

@Slf4j
@RestController
public class FaceBookPostController {

    @Autowired
    private FacebookService facebookService;

    @GetMapping("/allFBPosts")
    public List<FaceBookPost> getAllFaceBookPosts() {
        try {
            return facebookService.getAllLatestFaceBookPosts();

        } catch (Exception e) {
			log.error(EXCEPTION_MESSAGE, "fetching data from database ", "allFBPosts", e.getMessage(), e.getCause());
        }
        return null;
    }

    @GetMapping("/searchallfacebookpost")
	public List<FaceBookPost> getAllFaceBookEntities(@RequestParam(required = false) String content, @RequestParam(required = false) String fromdate,@RequestParam(required = false) String todate) {
		try 
		{
            Date toDate = StringUtils.isEmpty(todate) ? new Date() : convertToDate(todate);

            if (!StringUtils.isEmpty(content) && !StringUtils.isEmpty(fromdate) && !StringUtils.isEmpty(todate)) {
                return facebookService.getAllFaceBookPostByContentAndDate(content, convertToDate(fromdate), convertToDate(todate));
            } else if (!StringUtils.isEmpty(content) && StringUtils.isEmpty(fromdate) && StringUtils.isEmpty(todate)) {
                return facebookService.getAllFaceBookPostByDate(convertToDate(fromdate), toDate);
            } else if (!StringUtils.isEmpty(fromdate) && !StringUtils.isEmpty(todate)) {
                return facebookService.getAllFaceBookPostByDate(convertToDate(fromdate), toDate);
            } else {
                return facebookService.getAllLatestFaceBookPosts();
            }

        } catch (Exception e) {
			log.error(EXCEPTION_MESSAGE, "fetching facebook data from database ","getAllFaceBookEntities", e.getMessage(), e.getCause());

		}
        }
        return null;
    }

}
