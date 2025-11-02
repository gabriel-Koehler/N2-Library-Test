package com.librarytest.librarytest.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.librarytest.librarytest.Models.Loan;

public class InMemoryLoanRepository {
    private final Map<Long,Loan> loans = new HashMap<>();

    public Loan findById(Long id){
        return Optional.ofNullable(loans.get(id)).get();
    }
    public List<Loan> findByUser(Long id_user){
        return loans.values().stream().filter((e)-> e.getId_user().equals(id_user)).toList();
    }
    public void save(Loan loan){
        loan.setId_loan(loans.size()+1L);
        loans.put(loan.getId_loan(),loan);
    }

    public InMemoryLoanRepository(){
        loans.put(1L,new Loan(1L, 1L, 1L, LocalDateTime.of(2025,9,12,0,0)));

        loans.put(2L,new Loan(2L, 2L, 2L, LocalDateTime.of(2025,10,12,0,0)));

        loans.put(3L,new Loan(3L, 3L, 3L, LocalDateTime.of(2025,11,12,0,0)));
        
        loans.put(4L,new Loan(4L, 4L, 4L, LocalDateTime.of(2025,10,12,0,0)));

        loans.put(5L,new Loan(5L, 5L, 5L, LocalDateTime.of(2025,10,2,0,0)));

        loans.put(6L,new Loan(6L, 6L, 6L, LocalDateTime.of(2025,10,11,0,0)));

        loans.put(7L,new Loan(7L, 7L, 7L, LocalDateTime.of(2025,11,2,0,0)));

        loans.put(8L,new Loan(8L, 8L, 8L, LocalDateTime.of(2025,11,1,0,0)));

        loans.put(9L,new Loan(9L, 9L, 9L, LocalDateTime.of(2025,11,8,0,0)));

        loans.put(10L,new Loan(10L, 10L, 10L, LocalDateTime.of(2025,9,12,0,0)));

        loans.put(11L,new Loan(11L, 1L, 11L, LocalDateTime.of(2025,9,12,0,0)));
        
        loans.put(12L,new Loan(12L, 1L, 12L, LocalDateTime.of(2025,9,12,0,0)));

        loans.put(13L,new Loan(13L, 2L, 13L, LocalDateTime.of(2025,9,12,0,0)));

        loans.put(14L,new Loan(14L, 3L, 14L, LocalDateTime.of(2025,9,12,0,0)));

        loans.put(15L,new Loan(15L, 4L, 15L, LocalDateTime.of(2025,9,12,0,0)));

    }
}
