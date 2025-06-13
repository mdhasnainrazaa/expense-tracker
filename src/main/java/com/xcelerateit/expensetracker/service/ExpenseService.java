package com.xcelerateit.expensetracker.service;

import com.xcelerateit.expensetracker.model.Expense;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    List<Expense> getAllExpenses();
    Expense getExpenseById(Long id);
    Expense createExpense(Expense expense);
    Expense updateExpense(Long id, Expense expenseDetails);
    void deleteExpense(Long id);

    List<Expense> getExpensesByCategory(String category);
    List<Expense> getExpensesByDateRange(LocalDate start, LocalDate end);
}
