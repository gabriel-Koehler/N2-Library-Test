package com.librarytest.librarytest;

import com.librarytest.librarytest.ConfigTests.ResultTestWatchers;
import com.librarytest.librarytest.Models.Book;
import com.librarytest.librarytest.Models.Loan;
import com.librarytest.librarytest.Models.User;
import com.librarytest.librarytest.Repository.InMemoryBookRepository;
import com.librarytest.librarytest.Repository.InMemoryLoanRepository;
import com.librarytest.librarytest.Repository.InMemoryUserRepository;
import com.librarytest.librarytest.Services.ClockService;
import com.librarytest.librarytest.Services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    @DisplayName("✅ Should create a new loan successfully")
    public void shouldMakeNewLoan(){
        EmailService emailService=mock(EmailService.class);
        assertEquals("Loan Successeful",
                Loan.makeALoan(
                        loanRepository,
                        new User(5L,"user1@gmail.com"),
                        new Book("Book One"),
                        LocalDateTime.of(2025,11,1,0,0),
                        emailService
                        ));
    }
    @Test
    @DisplayName("📧 Should send confirmation email when loan is created")
    public void shouldMakeNewLoanEmail(){
        EmailService emailService=mock(EmailService.class);

        when(emailService.sendEmail(anyString(), anyString(), anyString()))
                .thenReturn("Send Email Successeful");
        Loan.makeALoan(
                loanRepository,
                new User(5L,"user5@gmail.com"),
                new Book("Book One"),
                LocalDateTime.of(2025,11,1,0,0),
                emailService
        );
        verify(emailService).sendEmail(eq("user5@gmail.com"),contains("Book One"),contains("Return date:"));
    }

    @Test
    @DisplayName("🚫 Should not allow new loan when limit (3) is reached")
    void shouldNotAllowLoanWhenLimitReached() {
        EmailService emailService=mock(EmailService.class);
        assertEquals("Loan Failed",
                Loan.makeALoan(
                        loanRepository,
                        new User(1L,"user1@gmail.com"),
                        new Book("Book One"),
                        LocalDateTime.of(2025,11,1,0,0),
                        emailService
                ));
    }


    @Test
    @DisplayName("⚠️ Should apply penalty and send notification email when return is late")
    public void shouldPenaltyAndSendEmail(){
        EmailService emailService= mock(EmailService.class) ;

        new Loan(LocalDateTime.of(1999,1,1,1,1)).calcPenaltyRule(
                LocalDateTime.of(2000,1,1,1,0),
                new User("a"),
                new Book(""),
                emailService
        );
        verify(emailService).sendEmail(eq("a"),contains("Expired"),anyString());
    }
    @Test
    @DisplayName("⚠️ Should apply penalty")
    public void shouldPenaltyApplied(){
        EmailService emailService= mock(EmailService.class) ;
        assertEquals("Penalty is 0.0"
        ,
            new Loan(LocalDateTime.of(1999,1,1,1,1)).calcPenaltyRule(
                    LocalDateTime.of(2000,1,1,1,0),
                    new User(""),
                    new Book(""),
                    emailService
            )
        );
    }

    @Test
    @DisplayName("🕒 Should return correct message when due date has not expired")
    public void shouldPenaltyNotExpired(){
        EmailService emailService= mock(EmailService.class) ;

        assertEquals("The loan date has not yet expired", new Loan(LocalDateTime.of(2001,1,1,1,1)).calcPenaltyRule(
                        LocalDateTime.of(2000,1,1,0,0),
                        new User(""),
                        new Book(""),
                        emailService
                )
        );

    }

    @Test
    @DisplayName("⚡ Should perform loan operation within 300 milliseconds")
    public void shouldNewLoanRunIn300ms(){
        EmailService emailService=mock(EmailService.class);
        assertTimeout(Duration.ofMillis(300),()->{
//                    Thread.sleep(300);
                    Loan.makeALoan(
                            loanRepository,
                            new User(1L,"user1@gmail.com"),
                            new Book("Book One"),
                            LocalDateTime.of(2025,11,1,0,0),
                            emailService
                            );

                }
        );
    }
    @Test
    @DisplayName("🚨 Should throw exception when user is null in makeALoan")
    void shouldThrowExceptionWhenUserIsNull() {
        EmailService emailService = mock(EmailService.class);

        assertThrows(NullPointerException.class, () -> {
            Loan.makeALoan(
                    loanRepository,
                    null,                              // invalid user
                    new Book("Book One"),
                    LocalDateTime.now(),
                    emailService
            );
        });
    }

    @Test
    @DisplayName("⚠️ Should throw exception when current date is null in penalty rule")
    void shouldThrowExceptionWhenCurrentDateIsNull() {
        EmailService emailService = mock(EmailService.class);
        ClockService clockServiceStub=new ClockService();
        Loan loan = new Loan(LocalDateTime.now());

        assertThrows(NullPointerException.class, () -> {
            loan.calcPenaltyRule(null, new User("a"), new Book("A"), emailService);
        });
    }

    @Test
    @DisplayName("⚠️ Should throw exception when EmailService is null in penalty rule")
    void shouldThrowExceptionWhenEmailServiceIsNull() {
        EmailService emailService = mock(EmailService.class);
        Loan loan = new Loan(LocalDateTime.now());

        assertThrows(NullPointerException.class, () -> {
            loan.calcPenaltyRule(LocalDateTime.of(2000,1,1,0,0), new User("a"), new Book("A"), null);
        });
    }
}
