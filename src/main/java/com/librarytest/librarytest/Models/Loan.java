package com.librarytest.librarytest.Models;

import java.time.LocalDateTime;

public class Loan {
    private Long id_loan;
    private Long id_user;
    private Long id_book;
    private LocalDateTime dateLoan;
    private LocalDateTime dateLoanExpired;

    public Loan(Long id_loan, Long id_user, Long id_book, LocalDateTime dateLoan) {
        this.id_loan = id_loan;
        this.id_user = id_user;
        this.id_book = id_book;
        this.dateLoan = dateLoan;
        this.dateLoanExpired = dateLoan.plusDays(15);

    }
    public Loan(LocalDateTime dateLoan){
        this.dateLoan = dateLoan;
        this.dateLoanExpired = dateLoan.plusDays(15);
    }



    public Long getId_loan() {
        return id_loan;
    }

    public void setId_loan(Long id_loan) {
        this.id_loan = id_loan;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public Long getId_book() {
        return id_book;
    }

    public void setId_book(Long id_book) {
        this.id_book = id_book;
    }

    public LocalDateTime getDateLoan() {
        return dateLoan;
    }

    public void setDateLoan(LocalDateTime dateLoan) {
        this.dateLoan = dateLoan;
    }

    public LocalDateTime getDateLoanExpired() {
        return dateLoanExpired;
    }

    public void setDateLoanExpired(LocalDateTime dateLoanExpired) {
        this.dateLoanExpired = dateLoanExpired;
    }
    
}
