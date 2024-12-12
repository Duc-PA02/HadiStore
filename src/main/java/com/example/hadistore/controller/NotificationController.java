package com.example.hadistore.controller;

import com.example.hadistore.entity.Notification;
import com.example.hadistore.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/notification")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NotificationController {
    NotificationService notificationService;
    @GetMapping
    public ResponseEntity<List<Notification>> getAll(){
        return ResponseEntity.ok(notificationService.findAll());
    }
    @GetMapping("read-all")
    public ResponseEntity<Void> readAll(){
        notificationService.realAll();
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification){
        return ResponseEntity.ok(notificationService.createNotification(notification));
    }
    @PutMapping("readed/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id){
        return ResponseEntity.ok(notificationService.updateNotification(id));
    }
}
