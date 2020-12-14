package com.solutions.datamart.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "record")
@EntityListeners(AuditingEntityListener.class)
public class Record extends AuditUserDate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	private String title;
	@Column
	private String link;
	@Column(length = 5000)
	private String description;
	@Column
	private String content;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "mediaId", nullable = true)
	private Media media;
}
