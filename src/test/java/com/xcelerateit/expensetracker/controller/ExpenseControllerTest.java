package com.xcelerateit.expensetracker.controller;

import com.xcelerateit.expensetracker.exception.ExpenseNotFoundException;
import com.xcelerateit.expensetracker.model.Expense;
import com.xcelerateit.expensetracker.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getExpenseById_ExistingId_ReturnsJson() throws Exception {
        Expense sample = new Expense("Lunch", new BigDecimal("8.75"), "Food", LocalDate.now());
        sample.setId(10L);
        when(expenseService.getExpenseById(10L)).thenReturn(sample);

        mockMvc.perform(get("/api/expenses/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.description").value("Lunch"));
    }

    @Test
    void getExpenseById_NonExistingId_Returns404() throws Exception {
        when(expenseService.getExpenseById(anyLong()))
                .thenThrow(new ExpenseNotFoundException("Expense not found"));

        mockMvc.perform(get("/api/expenses/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Expense not found"));
    }

    @Test
    void createExpense_InvalidData_Returns400() throws Exception {
        // Create JSON with a negative amount (invalid)
        Expense invalid = new Expense("Dinner", new BigDecimal("-5.00"), "Food", LocalDate.now());
        String invalidJson = objectMapper.writeValueAsString(invalid);

        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.errors.amount").value("Amount must be positive"));
    }
}
