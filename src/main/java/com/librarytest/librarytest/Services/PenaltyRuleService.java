package com.librarytest.librarytest.Services;

import com.librarytest.librarytest.Models.Book;
import com.librarytest.librarytest.Models.Loan;
import com.librarytest.librarytest.Models.User;

import java.time.LocalDateTime;

public class PenaltyRuleService {
    EmailService emailService;
    public PenaltyRuleService(EmailService emailService){
        this.emailService=emailService;
    }
    public String penalty(
            LocalDateTime currentTime,
            Loan currentLoan,
            User user,
            Book book
            ){
        if(currentTime.isAfter(currentLoan.getDateLoanExpired())){
            double resultPenalty=0.0;
            emailService.sendEmail(user.getEmail(),"Your Loan Date Expired" ,"Your loan of the book "+book.getTitle()+"is overdue. The fine is"+resultPenalty);
            return "Penalty is "+resultPenalty;
        }
        return "The loan date has not yet expired";
    }
}
