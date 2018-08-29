package com.patrick.stripecard.repository;

import com.patrick.stripecard.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    /*search by amount*/
    @Query(value = "SELECT id,amount FROM loan l WHERE l.amount= :amount", nativeQuery = true)
    List<Loan> findAmount(@Param("amount") Integer amount);

    List<Loan> findByAmount(String amount);

    /*sample search
    @Query(value = "SELECT * FROM loan t ",
            nativeQuery = true
    )
     List<Loan> findByFname(String fname);*/
}
