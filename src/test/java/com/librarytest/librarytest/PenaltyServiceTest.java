package com.librarytest.librarytest;

import com.librarytest.librarytest.ConfigTests.ResultTestWatchers;
import com.librarytest.librarytest.Models.Book;
import com.librarytest.librarytest.Models.Loan;
import com.librarytest.librarytest.Models.User;
import com.librarytest.librarytest.Repository.InMemoryBookRepository;
import com.librarytest.librarytest.Repository.InMemoryLoanRepository;
import com.librarytest.librarytest.Repository.InMemoryUserRepository;
import com.librarytest.librarytest.Services.EmailService;
import com.librarytest.librarytest.Services.PenaltyRuleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(ResultTestWatchers.class)
public class PenaltyServiceTest {
    InMemoryLoanRepository loanRepository;
    InMemoryUserRepository userRepository;
    InMemoryBookRepository bookRepository;


    @BeforeEach
    public void setUp(){
        bookRepository=new InMemoryBookRepository();
        userRepository=new InMemoryUserRepository();
        loanRepository=new InMemoryLoanRepository();
    }

    @Test
    public void shouldPenaltyEmail(){
        EmailService emailService= mock(EmailService.class) ;
        PenaltyRuleService penaltyRuleService=new PenaltyRuleService(emailService);
        penaltyRuleService.penalty(
                LocalDateTime.of(2000,1,1,0,0),
                new Loan(LocalDateTime.of(1999,1,1,1,1)),
                new User(""),
                new Book("")
                );
        verify(emailService).sendEmail(eq("a"),contains("0.0"),anyString());
    }

    @Test
    public void shouldPenaltyNotExpired(){
        EmailService emailService= mock(EmailService.class) ;
        PenaltyRuleService penaltyRuleService=new PenaltyRuleService(emailService);
        assertEquals("The loan date has not yet expired.", penaltyRuleService.penalty(
                LocalDateTime.of(2000,1,1,0,0),
                new Loan(LocalDateTime.of(1999,1,1,1,1)),
                new User(""),
                new Book("")
        )
        );

    }

}
