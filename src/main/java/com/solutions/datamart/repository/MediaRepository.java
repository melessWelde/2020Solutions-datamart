package com.solutions.datamart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solutions.datamart.model.Media;
import com.solutions.datamart.model.User;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    Media findByUrl(String url);
}
