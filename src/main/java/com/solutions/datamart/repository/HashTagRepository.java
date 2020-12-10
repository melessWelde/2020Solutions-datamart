package com.solutions.datamart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solutions.datamart.entity.HashTag;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Integer> {

}
