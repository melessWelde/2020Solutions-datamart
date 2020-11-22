package com.solutions.datamart.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "media")
@EntityListeners(AuditingEntityListener.class)
public class Media extends AuditUserDate implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column
	private String name;
	@Column
	private String url;
	@Column
	private String type;
	@Column
	private String language;
	@Column
	private String country;
	@Column
	private boolean crawel;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "media", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE })
	private Set<Record> record = new HashSet<>();

}
