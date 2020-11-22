package com.solutions.datamart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solutions.datamart.model.Media;

public interface MediaRepository extends JpaRepository<Media, Long> {

}
