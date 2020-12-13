package com.solutions.datamart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solutions.datamart.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

	Optional<Property> findByPropertyName(String propertyName);
	
	

}
