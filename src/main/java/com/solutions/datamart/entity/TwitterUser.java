package com.solutions.datamart.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;


@Entity
@Data
public class TwitterUser implements Serializable{

	@Id
	private long userId;
	
	@Column(name = "fullName")
	private String name;
	
	private String screenName;
	
	private String location;
	
	private String description;
	
	private String userUrl;
	
	private String imageUrl;
	
	private String historyFlag;
}
