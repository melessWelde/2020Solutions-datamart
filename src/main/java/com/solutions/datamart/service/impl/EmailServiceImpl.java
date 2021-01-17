package com.solutions.datamart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.solutions.datamart.service.EmailService;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    private static String subject = "welcome to Tigrayhub Community";

    @Async
    public void sendMessage(String username, String password) {

	SimpleMailMessage message = new SimpleMailMessage();
	message.setFrom("noreply@tigrayhub.com");
	message.setTo(username);
	message.setSubject(subject);

	String text = "you are registered to login to Tigrayhub community(http://localhost:5000/datamart/). Please use your username as: " + username
		+ "and your password as: " + password;
	message.setText(text);

	emailSender.send(message);

    }

}