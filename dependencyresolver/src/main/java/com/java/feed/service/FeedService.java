package com.java.feed.service;

import com.java.feed.entity.Feed;
import com.java.feed.repo.FeedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedService {
    private final FeedRepo feedRepo;
    @Autowired
    public FeedService(FeedRepo feedRepo) {
        this.feedRepo = feedRepo;
    }
    public Feed findFeedByName(String feedName) {
        return feedRepo.findByName(feedName);
    }

    public void saveFeed(Feed feed) {  feedRepo.save(feed);
    }

}
