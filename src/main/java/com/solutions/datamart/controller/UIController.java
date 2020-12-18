package com.solutions.datamart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UIController {

	@RequestMapping(value = "/")
    public String twitterSreachAsHome() {
        return "TweetSearch";
    }
	
	
	
	@RequestMapping(value = "/FBPostSearch")
    public String fbPostSreach() {
        return "FBPostSearch";
    }
	
	@RequestMapping(value = "/NewsDataSearch")
    public String newsDataSreach() {
		System.out.println("******************News************************");
        return "NewsDataSearch";
    }
	
}
