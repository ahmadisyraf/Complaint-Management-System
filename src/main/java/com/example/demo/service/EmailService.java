package com.example.demo.service;

public interface EmailService {
    String sendEmail(String to, String subject, String body);
}
