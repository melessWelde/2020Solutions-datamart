package com.solutions.datamart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solutions.datamart.entity.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>{

}
