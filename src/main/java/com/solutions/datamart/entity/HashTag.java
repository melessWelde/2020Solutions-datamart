package com.solutions.datamart.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class HashTag implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	private String hashTagText;
}
