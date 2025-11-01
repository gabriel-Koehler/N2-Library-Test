package com.librarytest.librarytest;

import com.librarytest.librarytest.ConfigTests.ResultTestWatchers;
import com.librarytest.librarytest.Models.Book;
import com.librarytest.librarytest.Models.User;
import com.librarytest.librarytest.Repository.InMemoryLoanRepository;
import com.librarytest.librarytest.Services.EmailService;
import com.librarytest.librarytest.Services.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(ResultTestWatchers.class)
public class LoanServiceTest {
    InMemoryLoanRepository loanRepository;

    @BeforeEach
    public void setUp(){
            loanRepository=new InMemoryLoanRepository();
    }

    @Test
    public void shouldMakeNewLoan(){
        EmailService emailService=mock(EmailService.class);
        LoanService loanService=new LoanService(emailService);
        assertEquals("Loan Successeful",loanService.makeALoan(loanRepository,new User(1L,"user1@gmail.com"),new Book("Book One")));
    }
    @Test
    public void shouldMakeNewLoanEmail(){
        EmailService emailService=mock(EmailService.class);
        LoanService loanService=new LoanService(emailService);
        loanService.makeALoan(loanRepository,new User(1L,"user1@gmail.com"),new Book("Book One"));
        verify(emailService).sendEmail(eq("user1@gmail.com"),contains("Book One"),contains("Return Date:"));
    }
}
