package com.solutions.datamart.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
public class FaceBookPost implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(nullable = false, unique = true)
	private String facebookId;	
	@Column
	protected String createdBy;	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdDate;
	@Column(length = 5000)
	private String message;
	@Column(length = 5000)
	private String description;
	@Column
	private String link;
	@Column
	private String source;
	@Column
	private String story;	
}
