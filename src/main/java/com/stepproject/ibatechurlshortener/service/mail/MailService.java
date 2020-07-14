package com.stepproject.ibatechurlshortener.service.mail;

import org.springframework.stereotype.Service;

@Service
public interface MailService {

    boolean send(String emailTo, String activationCode);

    String generateToken();
}
