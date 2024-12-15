package com.example.hadistore.service;

import com.example.hadistore.dtos.MailInfo;
import com.example.hadistore.entity.Order;
import jakarta.mail.MessagingException;

public interface SendMailService {
    void queue(String to, String subject, String body);
    void queue(MailInfo mail);
    void send(MailInfo mail) throws MessagingException;
    void sendOtp(String email, int otp, String title);
    void run();
    void sendMaiToken(String email, String title);
    void sendMailOrder(Order order);
    public void sendMailOrderSuccess(Order order);
    public void sendMailOrderDeliver(Order order);
    public void sendMailOrderCancel(Order order);
}
