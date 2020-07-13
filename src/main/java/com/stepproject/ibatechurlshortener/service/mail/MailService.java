package com.stepproject.ibatechurlshortener.service.mail;

import org.springframework.stereotype.Service;

@Service
public interface MailService {

    void send(String emailTo, String subject, String message);

    String generateToken();
}
