package com.solutions.datamart.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "facebook_posts")
public class FBPost {
	@Column
	private String id;
	@Column
	private Date createdTime;
	@Column
	private String message;
	@Column
	private String description;
	@Column
	private String link;
	@Column
	private String source;
	@Column
	private String story;	
}
