package com.java.feed.repo;

import com.java.feed.entity.FeedGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedGroupRepo  extends JpaRepository<FeedGroup,Integer> {
}
