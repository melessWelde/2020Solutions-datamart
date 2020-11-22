package com.solutions.datamart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solutions.datamart.model.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {

}
