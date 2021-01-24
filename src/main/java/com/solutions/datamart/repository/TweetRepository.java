package com.solutions.datamart.repository;

import com.solutions.datamart.entity.TweetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<TweetEntity, Long> {

    @Query(value = "Select * FROM tweet_entity order by created_dt desc limit 200", nativeQuery = true)
	List<TweetEntity> getAllLatestTweets();

    @Query(value = "Select * FROM tweet_entity WHERE user_name like %:userName%  order by created_dt desc", nativeQuery = true)
	List<TweetEntity> getAllTweetsByUser(@Param("userName") String userName);

    @Query(value = "Select * FROM tweet_entity WHERE tweet_text like %:hashText% order by created_dt desc", nativeQuery = true)
	List<TweetEntity> getAllTweetsByHashTag(@Param("hashText") String hashText);

	@Query(value = "Select tweet_id, user_name, hash_tags, tweet_text, created_dt, retweet_count, favourite_count, tweet_url FROM tweet_entity t, twitter_user u WHERE t.user_name = u.full_name and u.screen_name like %:handleName% order by created_dt desc", nativeQuery = true)
	List<TweetEntity> getAllTweetsByHandleName(@Param("handleName") String handleName);

	@Query(value = "Select tweet_id, user_name, hash_tags, tweet_text, created_dt, retweet_count, favourite_count, tweet_url FROM tweet_entity t, twitter_user u WHERE t.user_name = u.full_name and u.screen_name like %:handleName% AND created_dt >=:fromDate and created_dt <=:toDate order by created_dt desc", nativeQuery = true)
	List<TweetEntity> getAllTweetsByHandleNameAndDate(@Param("handleName") String handleName, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query(value = "Select * FROM tweet_entity WHERE userName like %:userName%  AND tweet_text like %:hashText% order by created_dt desc", nativeQuery = true)
	List<TweetEntity> getAllTweetsByNameAndHashTag(@Param("userName") String userName, @Param("hashText") String tweetText);

    @Query(value = "Select * FROM tweet_entity WHERE userName like %:userName%  AND tweet_text like %:hashText% AND created_dt >=:fromDate and created_dt <=:toDate order by created_dt desc", nativeQuery = true)
	List<TweetEntity> getAllTweetsByNameHashAndDate(@Param("userName") String userName, @Param("hashText") String tweetText, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query(value = "Select * FROM tweet_entity WHERE user_name like %:userName%  AND created_dt >= :fromDate and created_dt <= :toDate order by created_dt desc", nativeQuery = true)
	List<TweetEntity> getAllTweetsByUserAndDate(@Param("userName") String userName, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query(value = "Select * FROM tweet_entity WHERE tweet_text like %:hashText% AND created_dt >= :fromDate and created_dt <=:toDate order by created_dt desc", nativeQuery = true)
	List<TweetEntity> getAllTweetsByHashTagAndDate(@Param("hashText") String hashText, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query(value = "Select * FROM tweet_entity WHERE created_dt >= :fromDate and created_dt <= :toDate order by created_dt desc", nativeQuery = true)
	List<TweetEntity> getAllTweetsByDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
