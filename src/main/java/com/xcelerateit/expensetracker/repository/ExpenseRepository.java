package com.xcelerateit.expensetracker.repository;

import com.xcelerateit.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Custom query methods:
    List<Expense> findAllByCategory(String category);
    List<Expense> findAllByExpenseDateBetween(LocalDate start, LocalDate end);
}
