package com.java.feed.service;

import com.java.feed.repo.LatestNotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
@Slf4j
@Service
public class LatestNotificationService {
    private LatestNotificationRepository latestNotificationRepository;
    public LatestNotificationService(LatestNotificationRepository latestNotificationRepository) {
        this.latestNotificationRepository = latestNotificationRepository;
    }

    public  void updateLatestNotification(long feedId, LocalDate cob) {
        latestNotificationRepository.updateLatestNotification(feedId,cob);
    }


}
