package com.solutions.datamart.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class HashTag implements Serializable {

	
	private int id;
	
	private String text;
}
