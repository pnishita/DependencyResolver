package com.java.feed.repo;

import com.java.feed.entity.FeedConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedConfigRepo extends JpaRepository<FeedConfig,Long> {
}
