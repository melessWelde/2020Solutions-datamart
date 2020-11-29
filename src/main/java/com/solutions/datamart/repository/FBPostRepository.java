package com.solutions.datamart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solutions.datamart.model.FBPost;

@Repository
public interface FBPostRepository extends JpaRepository<FBPost, Long> {

}
