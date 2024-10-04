package com.java.feed.service;

import com.java.feed.entity.FeedGroup;
import com.java.feed.repo.FeedGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FeedGroupService {
    private FeedGroupRepo feedGroupRepo;
    @Autowired
    public FeedGroupService(FeedGroupRepo feedGroupRepo){
        this.feedGroupRepo=feedGroupRepo;
    }
    public Optional<FeedGroup> findGroupByGroupId(Long groupId) {
        return feedGroupRepo.findById(groupId);
    }
    @Cacheable("feedGroups")
    public List<FeedGroup> getAllGroups() {
        return feedGroupRepo.findAll();
    }
}
