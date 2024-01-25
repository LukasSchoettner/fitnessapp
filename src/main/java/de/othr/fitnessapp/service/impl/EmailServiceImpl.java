package de.othr.fitnessapp.service.impl;

import de.othr.fitnessapp.config.EmailConfig;
import de.othr.fitnessapp.service.EmailServiceI;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class EmailServiceImpl implements EmailServiceI {
    private EmailConfig emailConfig;

    @Override
    public void sendEmail(String to, String subject, String text) throws MailException {
        JavaMailSender emailSender = emailConfig.getJavaMailSender();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailConfig.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
