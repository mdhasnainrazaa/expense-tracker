package com.xcelerateit.expensetracker.controller;

import com.xcelerateit.expensetracker.model.Expense;
import com.xcelerateit.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@Validated
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // 1. GET /api/expenses → list all expenses
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    // 2. GET /api/expenses/{id} → get one by ID
    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    // 3. POST /api/expenses → create new expense (validation on JSON)
    @PostMapping
    public Expense createExpense(@Valid @RequestBody Expense expense) {
        return expenseService.createExpense(expense);
    }

    // 4. PUT /api/expenses/{id} → update an existing expense
    @PutMapping("/{id}")
    public Expense updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody Expense expenseDetails) {
        return expenseService.updateExpense(id, expenseDetails);
    }

    // 5. DELETE /api/expenses/{id} → delete an expense
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    // 6. GET /api/expenses/category/{category} → get by category
    @GetMapping("/category/{category}")
    public List<Expense> getExpensesByCategory(@PathVariable String category) {
        return expenseService.getExpensesByCategory(category);
    }

    // 7. GET /api/expenses/date-range?from=2023-01-01&to=2023-12-31
    @GetMapping("/date-range")
    public List<Expense> getExpensesByDateRange(
            @RequestParam("from") LocalDate from,
            @RequestParam("to") LocalDate to) {
        return expenseService.getExpensesByDateRange(from, to);
    }
}
