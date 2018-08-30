package com.patrick.stripecard.repository;

import com.patrick.stripecard.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    /*search by amount*/

    @Transactional
    @Query(value = "SELECT *  FROM loan l WHERE l.amount= :amount", nativeQuery = true)
    Optional<Loan> findAmount(@Param("amount") String amount);

    @Transactional
    @Query(value = "select * from loan l where l.amount=?1 ", nativeQuery = true)
    List<Loan> test(String amount);

    List<Loan> findByAmount(String amount);

    /*sample search
    @Query(value = "SELECT * FROM loan t ",
            nativeQuery = true
    )
     List<Loan> findByFname(String fname);*/
}
