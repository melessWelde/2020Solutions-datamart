package com.solutions.datamart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagRepository extends JpaRepository<HashTagEntity, Integer> {

}
