package com.xcelerateit.expensetracker.service;

import com.xcelerateit.expensetracker.exception.ExpenseNotFoundException;
import com.xcelerateit.expensetracker.model.Expense;
import com.xcelerateit.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id = " + id));
    }

    @Override
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Long id, Expense expenseDetails) {
        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id = " + id));

        existing.setDescription(expenseDetails.getDescription());
        existing.setAmount(expenseDetails.getAmount());
        existing.setCategory(expenseDetails.getCategory());
        existing.setExpenseDate(expenseDetails.getExpenseDate());

        return expenseRepository.save(existing);
    }

    @Override
    public void deleteExpense(Long id) {
        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id = " + id));
        expenseRepository.delete(existing);
    }

    @Override
    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findAllByCategory(category);
    }

    @Override
    public List<Expense> getExpensesByDateRange(LocalDate start, LocalDate end) {
        return expenseRepository.findAllByExpenseDateBetween(start, end);
    }
}
