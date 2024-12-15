package com.example.hadistore.service.impl;

import com.example.hadistore.entity.Notification;
import com.example.hadistore.exceptions.DataNotFoundException;
import com.example.hadistore.repository.NotificationRepository;
import com.example.hadistore.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NotificationServiceImpl implements NotificationService {
    NotificationRepository notificationRepository;
    @Override
    public List<Notification> findAll() {
        return notificationRepository.findByOrderByIdDesc();
    }

    @Override
    public void realAll() {
        notificationRepository.readAll();
    }

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification updateNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Notification not found"));
        notification.setStatus(true);
        return notificationRepository.save(notification);
    }
}
