package com.solutions.datamart.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "role")
@EntityListeners(AuditingEntityListener.class)
public class Role extends AuditUserDate implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<User> users = new HashSet<>();

}