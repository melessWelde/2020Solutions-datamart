package com.solutions.datamart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solutions.datamart.entity.TweetEntity;

@Repository
public interface TweetRepository extends JpaRepository<TweetEntity, Long>{

}
