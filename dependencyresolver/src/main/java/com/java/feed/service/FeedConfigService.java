package com.java.feed.service;
import com.java.feed.entity.FeedConfig;
import com.java.feed.entity.FeedGroup;
import com.java.feed.repo.FeedConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FeedConfigService {
    private FeedConfigRepo feedConfigRepo;
    @Autowired
    public FeedConfigService(FeedConfigRepo feedConfigRepo){
        this.feedConfigRepo=feedConfigRepo;
    }
    public List<FeedConfig> getFeedConfigsByGroup(FeedGroup group) {
        return feedConfigRepo.findByFeedGroup(group);
    }
    public List<FeedConfig> findAll() {
        return feedConfigRepo.findAll();
    }
}
