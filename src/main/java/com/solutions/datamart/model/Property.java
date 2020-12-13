package com.solutions.datamart.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "property")
@EntityListeners(AuditingEntityListener.class)
public class Property extends AuditUserDate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	private String propertyName;

	@Column
	private String propertyValue;

}
