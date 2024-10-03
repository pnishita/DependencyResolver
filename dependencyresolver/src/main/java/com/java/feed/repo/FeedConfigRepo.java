package com.java.feed.repo;

import com.java.feed.entity.Feed;
import com.java.feed.entity.FeedConfig;
import com.java.feed.entity.FeedGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedConfigRepo extends JpaRepository<FeedConfig,Long> {

    List<FeedConfig> findByFeedGroup(FeedGroup feedGroup);

    List<FeedConfig> findByFeed(Feed feed);
}
