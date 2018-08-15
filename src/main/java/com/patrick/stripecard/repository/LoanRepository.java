package com.patrick.stripecard.repository;

import com.patrick.stripecard.model.Loan;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, Integer> {
}
