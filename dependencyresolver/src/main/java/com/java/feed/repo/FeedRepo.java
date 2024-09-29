package com.java.feed.repo;

import com.java.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepo extends JpaRepository <Feed,Integer>{
    Feed findByName(String feedName);
}
