package com.example.hadistore.service;

import com.example.hadistore.dtos.MailInfo;
import jakarta.mail.MessagingException;

public interface SendMailService {
    void queue(String to, String subject, String body);
    void queue(MailInfo mail);
    void send(MailInfo mail) throws MessagingException;
    void sendOtp(String email, int otp, String title);
    void run();
}
