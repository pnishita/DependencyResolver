package com.java.dependencyresolver.service;

import com.java.feed.repo.FeedConfigRepo;
import com.java.feed.repo.FeedRepo;
import com.java.feed.repo.NotificationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;

import java.util.Map;

@Service
@AllArgsConstructor
public class DependencyResolverService {

                private final NotificationRepo notificationRepository;
                private final FeedConfigRepo feedConfigRepository;
                private final FeedRepo feedRepository;

                private final Map<Long, Integer> groupVersionMap = new HashMap<>();


        }