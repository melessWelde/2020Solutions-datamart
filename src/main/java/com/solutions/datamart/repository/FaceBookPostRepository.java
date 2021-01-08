package com.solutions.datamart.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.solutions.datamart.model.FaceBookPost;

@Repository
public interface FaceBookPostRepository extends JpaRepository<FaceBookPost, String> {

	@Query(value = "Select * FROM face_book_post order by createdDate desc limit 200", nativeQuery = true)
	public List<FaceBookPost> getAllLatestFaceBookPost();

	@Query(value = "Select * FROM face_book_post WHERE (message like :content OR description like :content) order by created_date desc limit 200", nativeQuery = true)
	public List<FaceBookPost> getAllFaceBookPostByPostContent(@Param("content") String content);

	@Query(value = "Select * FROM face_book_post WHERE createdDate > :fromDate and created_date < :toDate order by created_date desc limit 200", nativeQuery = true)
	public List<FaceBookPost> getAllFaceBookPostByDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	@Query(value = "Select * FROM face_book_post WHERE (message like :content OR description like :content) AND created_date > :fromDate and created_date < :toDate order by created_date desc limit 200", nativeQuery = true)
	public List<FaceBookPost> getAllFaceBookPostContentAndDate(@Param("content") String content,
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	@Query(value = "Select * FROM datamart.face_book_post order by created_date desc limit 200", nativeQuery = true)
	public List<FaceBookPost> getAllLatestFBPosts();

	@Query(value = "Select * FROM datamart.face_book_post WHERE message LIKE :SearchKey OR description like :SearchKey order by created_date desc", nativeQuery = true)
	public List<FaceBookPost> getFBPostsBySearchKey(@Param("SearchKey") String SearchKey);

	@Query(value = "Select * FROM datamart.face_book_post WHERE created_date > :DateFrom and created_date < :DateTo order by created_date desc", nativeQuery = true)
	public List<FaceBookPost> getFBPostsByDate(@Param("DateFrom") Date DateFrom, @Param("DateTo") Date DateTo);

	@Query(value = "Select * FROM datamart.face_book_post WHERE message LIKE :SearchKey OR description like :SearchKey AND created_date > :DateFrom and created_date < :DateTo order by created_date desc", nativeQuery = true)
	public List<FaceBookPost> getFBPostsByKeyAndDate(@Param("SearchKey") String SearchKey,
			@Param("DateFrom") Date DateFrom, @Param("DateTo") Date DateTo);
}
