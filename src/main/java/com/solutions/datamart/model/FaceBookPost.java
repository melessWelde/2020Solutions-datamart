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
public class FaceBookPost extends AuditUserDate implements Serializable {
	
	@Column
	private String facebookId;
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
