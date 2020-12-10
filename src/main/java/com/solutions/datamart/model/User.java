package com.solutions.datamart.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable{

	private long userId;
	
	private String name;
	
	private String screenName;
	
	private String location;
	
	private String description;
	
	private String userUrl;
}
