package com.fodapi.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Service
public class EmailService {

    @Autowired
    JavaMailSenderImpl mailSender;

    public void sendRegistrationConfirmationEmail(String to) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setFrom("travelchan@gmail.com");
        msg.setSubject("Registration confirmation email");
        msg.setText("Hi, an account for this email has just been registered, you can now use travelchan web application!");
        mailSender.send(msg);

    }
}
