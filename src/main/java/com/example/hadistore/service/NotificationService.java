package com.example.hadistore.service;

import com.example.hadistore.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> findAll();
    void realAll();
    Notification createNotification(Notification notification);
    Notification updateNotification(Long id);
}
