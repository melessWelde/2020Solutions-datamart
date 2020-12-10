package com.solutions.datamart.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entities implements Serializable{

	@JsonProperty("hashtags")
	private List<HashTagEntity> tags = new LinkedList<HashTagEntity>();

	public Entities() {
		super();
	}
	
	
}
