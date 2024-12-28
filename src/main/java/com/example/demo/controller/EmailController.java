package com.example.demo.controller;

import com.example.demo.service.EmailService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "send-email")
    public String sendTestEmail() {
        return emailService.sendEmail("test@example.com", "Test Subject", "Test Body");
    }
}
