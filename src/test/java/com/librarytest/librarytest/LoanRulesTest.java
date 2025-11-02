package com.librarytest.librarytest;

import com.librarytest.librarytest.ConfigTests.ResultTestWatchers;
import com.librarytest.librarytest.Models.Book;
import com.librarytest.librarytest.Models.Loan;
import com.librarytest.librarytest.Models.User;
import com.librarytest.librarytest.Repository.InMemoryBookRepository;
import com.librarytest.librarytest.Repository.InMemoryLoanRepository;
import com.librarytest.librarytest.Repository.InMemoryUserRepository;
import com.librarytest.librarytest.Services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(ResultTestWatchers.class)
public class LoanRulesTest {
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
    public void shouldMakeNewLoan(){
        EmailService emailService=mock(EmailService.class);
        assertEquals("Loan Successeful",
                Loan.makeALoan(
                        loanRepository,
                        new User(1L,"user1@gmail.com"),
                        new Book("Book One"),
                        LocalDateTime.of(2025,11,1,0,0),
                        emailService
                        ));
    }
    @Test
    public void shouldMakeNewLoanEmail(){
        EmailService emailService=mock(EmailService.class);

        Loan.makeALoan(
                loanRepository,
                new User(1L,"user1@gmail.com"),
                new Book("Book One"),
                LocalDateTime.of(2025,11,1,0,0),
                emailService
        );
        verify(emailService).sendEmail(eq("user1@gmail.com"),contains("Book One"),contains("Return Date:"));
    }

    @Test
    public void shouldPenaltyEmail(){
        EmailService emailService= mock(EmailService.class) ;

        new Loan(LocalDateTime.of(1999,1,1,1,1)).calcPenaltyRule(
                LocalDateTime.of(2000,1,1,1,0),
                new User(""),
                new Book(""),
                emailService
        );
        verify(emailService).sendEmail(eq("a"),contains("0.0"),anyString());
    }

    @Test
    public void shouldPenaltyNotExpired(){
        EmailService emailService= mock(EmailService.class) ;

        assertEquals("The loan date has not yet expired", new Loan(LocalDateTime.of(1999,1,1,1,1)).calcPenaltyRule(
                        LocalDateTime.of(1998,1,1,0,0),
                        new User(""),
                        new Book(""),
                        emailService
                )
        );

    }

    @Test
    public void shouldRunIn300ms(){

    }
}
