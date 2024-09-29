package com.java.feed.repo;

import com.java.feed.entity.Feed;
import com.java.feed.entity.FeedConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedConfigRepo extends JpaRepository<FeedConfig,Long> {
    List<FeedConfig> findByFeed_FeedIdIn(List<Long> feedIds);
}
