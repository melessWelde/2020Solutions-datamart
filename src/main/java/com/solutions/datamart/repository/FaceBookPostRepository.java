package com.solutions.datamart.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.solutions.datamart.entity.TweetEntity;
import com.solutions.datamart.model.FaceBookPost;

@Repository
public interface FaceBookPostRepository extends JpaRepository<FaceBookPost, Long> {

	@Query(value = "Select * FROM face_book_post order by createdDate desc limit 200", nativeQuery = true)
	public List<FaceBookPost> getAllLatestFaceBookPost();
	
	@Query(value = "Select * FROM face_book_post WHERE (message like :content OR description like :description) order by createdDate desc limit 200", nativeQuery = true)
	public List<FaceBookPost> getAllFaceBookPostByPostContent(@Param("content") String content);
	
	@Query(value = "Select * FROM face_book_post WHERE createdDate > :fromDate and createdDate < :toDate order by createdDate desc limit 200", nativeQuery = true)
	public List<FaceBookPost> getAllFaceBookPostByDate(@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);

	@Query(value = "Select * FROM tweet_entity WHERE (message like :content OR description like :description) AND created_dt > :fromDate and created_dt < :toDate order by created_dt desc limit 200", nativeQuery = true)
	public List<FaceBookPost> getAllFaceBookPostContentAndDate(@Param("content") String content,@Param("fromDate") Date fromDate,@Param("toDate") Date toDate );
	
}
