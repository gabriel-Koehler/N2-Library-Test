package com.librarytest.librarytest;

import com.librarytest.librarytest.ConfigTests.ResultTestWatchers;
import com.librarytest.librarytest.Services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(ResultTestWatchers.class)
public class EmailServiceTest {
    private EmailService emailService;

    @BeforeEach
    public void setUp(){
        emailService= new EmailService();
    }

    @Test
    public void shouldSendEmail(){
        assertEquals("Send Email Successeful",emailService.sendEmail("","",""));
    }
}
