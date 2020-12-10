package com.solutions.datamart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solutions.datamart.model.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

}