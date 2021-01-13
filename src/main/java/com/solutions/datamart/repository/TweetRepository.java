package com.solutions.datamart.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.solutions.datamart.entity.TweetEntity;

@Repository
public interface TweetRepository extends JpaRepository<TweetEntity, Long>{
	
	@Query(value = "Select * FROM tweet_entity order by created_dt desc limit 200", nativeQuery = true)
	public List<TweetEntity> getAllLatestTweets();

	@Query(value = "Select * FROM tweet_entity WHERE user_name like %:userName%  order by created_dt desc", nativeQuery = true)
	public List<TweetEntity> getAllTweetsByUser(@Param("userName") String userName);
	
//	@Query(value = "Select * FROM tweet_entity WHERE FIND_IN_SET(:hashText,hash_tags) order by created_dt desc", nativeQuery = true)
	@Query(value = "Select * FROM tweet_entity WHERE tweet_text like %:hashText% order by created_dt desc", nativeQuery = true)
	public List<TweetEntity> getAllTweetsByHashTag(@Param("hashText") String hashText);
	
//	@Query(value = "Select * FROM tweet_entity WHERE user_name = :userName AND FIND_IN_SET(:hashText,hash_tags)order by created_dt desc", nativeQuery = true)
	@Query(value = "Select * FROM tweet_entity WHERE userName like %:userName%  AND tweet_text like %:hashText% order by created_dt desc", nativeQuery = true)
	public List<TweetEntity> getAllTweetsByNameAndHashTag(@Param("userName") String userName,@Param("hashText") String tweetText);
	
//	@Query(value = "Select * FROM tweet_entity WHERE user_name = :userName AND FIND_IN_SET(:hashText,hash_tags) AND created_dt > :fromDate and created_dt < :toDate order by created_dt desc", nativeQuery = true)
	@Query(value = "Select * FROM tweet_entity WHERE userName like %:userName%  AND tweet_text like %:hashText% AND created_dt >=:fromDate and created_dt <=:toDate order by created_dt desc", nativeQuery = true)
	public List<TweetEntity> getAllTweetsByNameHashAndDate(@Param("userName") String userName,@Param("hashText") String tweetText, @Param("fromDate") Date fromDate,@Param("toDate") Date toDate );
	
	@Query(value = "Select * FROM tweet_entity WHERE user_name like %:userName%  AND created_dt >= :fromDate and created_dt <= :toDate order by created_dt desc", nativeQuery = true)
	public List<TweetEntity> getAllTweetsByUserAndDate(@Param("userName") String userName, @Param("fromDate") Date fromDate,@Param("toDate") Date toDate );
	
//	@Query(value = "Select * FROM tweet_entity WHERE FIND_IN_SET(:hashText,hash_tags) AND created_dt > :fromDate and created_dt < :toDate order by created_dt desc", nativeQuery = true)
	@Query(value = "Select * FROM tweet_entity WHERE tweet_text like %:hashText% AND created_dt >= :fromDate and created_dt <=:toDate order by created_dt desc", nativeQuery = true)
	public List<TweetEntity> getAllTweetsByHashTagAndDate(@Param("hashText") String hashText, @Param("fromDate") Date fromDate,@Param("toDate") Date toDate );
	
	@Query(value = "Select * FROM tweet_entity WHERE created_dt >= :fromDate and created_dt <= :toDate order by created_dt desc", nativeQuery = true)
	public List<TweetEntity> getAllTweetsByDate(@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);
	
	
}
