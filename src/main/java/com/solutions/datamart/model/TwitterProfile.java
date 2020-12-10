package com.solutions.datamart.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterProfile implements Serializable {

	private String name;

	@JsonProperty("screen_name")
	private String screenName;
	
	public TwitterProfile() {
		super();
	}
	
	
}
