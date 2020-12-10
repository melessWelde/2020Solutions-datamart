package com.solutions.datamart.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "FBPost")
public class FBPost extends AuditUserDate implements Serializable {
	
	@Column
	private String facebookId;
//	@Column
//	@Temporal(TemporalType.DATE)
//	private Date createdTime;
	
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
