package de.othr.fitnessapp.service;

import org.springframework.mail.MailException;

public interface EmailServiceI {

    void sendEmail(String to, String subject, String text) throws MailException;
}
