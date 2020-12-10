package com.solutions.datamart.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HashTagEntity  implements Serializable{

	private String text;

	public HashTagEntity() {
		super();
	}
	
	
}
