package com.librarytest.librarytest.Services;

import com.librarytest.librarytest.Models.Book;
import com.librarytest.librarytest.Models.Loan;
import com.librarytest.librarytest.Models.User;
import com.librarytest.librarytest.Repository.InMemoryLoanRepository;

import java.util.ArrayList;

public class LoanService {
    EmailService emailService;
    public LoanService(EmailService emailService){
        this.emailService=emailService;
    }
    public String makeALoan(InMemoryLoanRepository repo, User lendingUser, Book lendingBook){
        ArrayList<Loan> loansLendingUser =new ArrayList<>(repo.findByUser(lendingUser.getId_user()));
        if(loansLendingUser.size()<3 && loansLendingUser.size()>=0){
            emailService.sendEmail(lendingUser.getEmail(),"Loan of "+lendingBook.getTitle(),"You borrowed the book"+0+" \n Return date:"+0);
            return "Loan Successeful";
        }else{
            return "Loan Failed";
        }

    }
}
