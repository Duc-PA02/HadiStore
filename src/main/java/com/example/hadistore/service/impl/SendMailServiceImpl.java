package com.example.hadistore.service.impl;

import com.example.hadistore.dtos.MailInfo;
import com.example.hadistore.service.SendMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SendMailServiceImpl implements SendMailService {
    JavaMailSender javaMailSender;
    List<MailInfo> list = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void queue(String to, String subject, String body) {
        queue(new MailInfo(to, subject, body));
    }

    @Override
    public void queue(MailInfo mail) {
        list.add(mail);
    }

    @Override
    public void send(MailInfo mail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBody(), true);
        helper.setReplyTo(mail.getFrom());

        if (mail.getAttachments() != null) {
            FileSystemResource file = new FileSystemResource(new File(mail.getAttachments()));
            helper.addAttachment(mail.getAttachments(), file);
        }
        javaMailSender.send(message);
    }

    @Override
    public void sendOtp(String email, int otp, String title) {
        String body = "<div>\r\n" + "        <h3>Mã OTP của bạn là: <span style=\"color:red; font-weight: bold;\">"
                + otp + "</span></h3>\r\n" + "    </div>";
        queue(email, title, body);
    }

    @Override
    @Scheduled(fixedDelay = 5000)
    public void run() {
        synchronized (list) {
            while (!list.isEmpty()) {
                MailInfo mail = list.remove(0);
                try {
                    this.send(mail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
