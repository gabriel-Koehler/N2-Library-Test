package com.librarytest.librarytest;

import com.librarytest.librarytest.ConfigTests.ResultTestWatchers;
import com.librarytest.librarytest.Models.Book;
import com.librarytest.librarytest.Models.Loan;
import com.librarytest.librarytest.Models.User;
import com.librarytest.librarytest.Repository.InMemoryLoanRepository;
import com.librarytest.librarytest.Services.ClockService;
import com.librarytest.librarytest.Services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(ResultTestWatchers.class)
public class LoanRulesTest {
    InMemoryLoanRepository loanRepository;


    @BeforeEach
    public void setUp(){
        loanRepository=new InMemoryLoanRepository();
    }


    @DisplayName("✅ Should create a new loan successfully")
    @ParameterizedTest(name = "📚 Should make new loan for userId={0} -> Expected: {1} and Loans Before: {2} and Loans After: {3}")
    @CsvSource({
            "5, Loan Successeful,1,2",
            "2, Loan Successeful,2,3",
            "1, Loan Failed,3,3"
    })
    public void shouldMakeNewLoan(long userId,String expectedResult,int loansBefore,int loansAfter){
        EmailService emailService=mock(EmailService.class);
        ClockService clockServiceStub=new ClockService();
        int sizeOfLoanPerUserBeforeAdd= loanRepository.findByUser(userId).size();
        assertEquals(loansBefore,sizeOfLoanPerUserBeforeAdd);
        assertEquals(expectedResult,
                Loan.makeALoan(
                        loanRepository,
                        new User(userId,"user"+userId+"@gmail.com"),
                        new Book("Book One"),
                        clockServiceStub.currentTime(),
                        emailService
                        ));
        int sizeOfLoanPerUserAfterAdd= loanRepository.findByUser(userId).size();
        assertEquals(loansAfter,sizeOfLoanPerUserAfterAdd);

    }

    @DisplayName("📧 Should send confirmation email when loan is created")
    @ParameterizedTest(name = "📚 Should confirmation for userId={0} -> Email contains: {1} and Date expired: {2}")
    @CsvSource({
            "5, user5@gmail.com,11/17/2025",
            "2, user2@gmail.com,11/17/2025",
            "1,  ,  "
    })
    public void shouldMakeNewLoanEmail(long userId,String expecteEmail, String dateExpired){
        EmailService emailService=mock(EmailService.class);
        ClockService clockServiceStub=new ClockService();
//        when(emailService.sendEmail(anyString(), anyString(), anyString()))
//                .thenReturn("Send Email Successeful");
        LocalDateTime loanTime=clockServiceStub.currentTime();
        Loan.makeALoan(
                loanRepository,
                new User(userId,"user"+userId+"@gmail.com"),
                new Book("Book Any"),
                loanTime,
                emailService
        );
        LocalDateTime loanExpiredeDate =loanTime.plusDays(15);
        verify(emailService).sendEmail(eq(expecteEmail),contains("Book Any"),contains(dateExpired));
    }


    private static Stream<Arguments> providePenaltyCases() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2025, 10, 17, 1, 1),
                        LocalDateTime.of(2025, 11, 2, 1, 1),
                        "Penalty is 0.5"),
                Arguments.of(LocalDateTime.of(2025, 10, 16, 1, 1),
                        LocalDateTime.of(2025, 11, 2, 1, 1),
                        "Penalty is 1.0"),
                Arguments.of(LocalDateTime.of(2025, 10, 14, 1, 1),
                        LocalDateTime.of(2025, 11, 2, 1, 1),
                        "Penalty is 2.0"),
                Arguments.of(LocalDateTime.of(2025, 10, 2, 1, 1),
                        LocalDateTime.of(2025, 11, 2, 1, 1),
                        "Penalty is 8.0"),
                Arguments.of(LocalDateTime.of(2025, 11, 13, 1, 1),
                        LocalDateTime.of(2025, 11, 14, 1, 1),
                        "The loan date has not yet expired")
        );
    }

    private static Stream<Arguments> providePenaltyCasesEmail() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2025, 10, 17, 1, 1),
                        LocalDateTime.of(2025, 11, 2, 1, 1),
                        "0.5","user1@gmail"),
                Arguments.of(LocalDateTime.of(2025, 10, 11, 1, 1),
                        LocalDateTime.of(2025, 11, 2, 1, 1),
                        "0.5","user2@gmail"),
                Arguments.of(LocalDateTime.of(2025, 11, 13, 1, 1),
                        LocalDateTime.of(2025, 11, 14, 1, 1),
                        "The loan date has not yet expired","user3@gmail")
        );
    }


    @MethodSource("providePenaltyCases")
    @ParameterizedTest(name = "⏰ Should calculate penalty between {0} and {1} -> Expected: {2}")
    @DisplayName("⚠️ Should apply penalty")
    public void shouldPenaltyApplied(LocalDateTime loanDate,LocalDateTime currenTime,String expected){
        EmailService emailService= mock(EmailService.class) ;
        assertEquals(expected
                ,
                new Loan(loanDate).calcPenaltyRule(
                        currenTime,
                        new User(""),
                        new Book(""),
                        emailService
                )
        );
    }

    @MethodSource("providePenaltyCasesEmail")
    @ParameterizedTest(name = "⏰ Should calculate penalty between {0} and {1} -> Expected: {2}, user: {3}")
    @DisplayName("⚠️ Should apply penalty and send notification email when return is late")
    public void shouldPenaltyAndSendEmail(LocalDateTime loanDate,LocalDateTime currenTime,String expected,String user){
        EmailService emailService= mock(EmailService.class) ;
        new Loan(loanDate).calcPenaltyRule(
                currenTime,
                new User(user),
                new Book(""),
                emailService
        );
        verify(emailService).sendEmail(eq(user),contains("Expired"),contains(expected));
    }

    @DisplayName("⚡ Should perform loan operation within 300 milliseconds")
    @RepeatedTest(value = 5)
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
