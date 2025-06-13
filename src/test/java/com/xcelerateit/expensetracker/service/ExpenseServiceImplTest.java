package com.xcelerateit.expensetracker.service;

import com.xcelerateit.expensetracker.exception.ExpenseNotFoundException;
import com.xcelerateit.expensetracker.model.Expense;
import com.xcelerateit.expensetracker.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private Expense sampleExpense;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleExpense = new Expense(
                "Coffee",
                new BigDecimal("3.50"),
                "Food",
                LocalDate.now().minusDays(1)
        );
        sampleExpense.setId(1L);
    }

    @Test
    void getExpenseById_ExistingId_ReturnsExpense() {
        // Arrange
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(sampleExpense));

        // Act
        Expense found = expenseService.getExpenseById(1L);

        // Assert
        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals("Coffee", found.getDescription());
        verify(expenseRepository, times(1)).findById(1L);
    }

    @Test
    void getExpenseById_NonExistingId_ThrowsException() {
        // Arrange
        when(expenseRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ExpenseNotFoundException.class, () -> {
            expenseService.getExpenseById(2L);
        });
        verify(expenseRepository, times(1)).findById(2L);
    }

    @Test
    void createExpense_ValidExpense_ReturnsSavedExpense() {
        // Arrange
        when(expenseRepository.save(any(Expense.class))).thenAnswer(invocation -> {
            Expense e = invocation.getArgument(0);
            e.setId(5L);
            return e;
        });

        // Act
        Expense toSave = new Expense("Taxi", new BigDecimal("12.00"), "Transport", LocalDate.now());
        Expense saved = expenseService.createExpense(toSave);

        // Assert
        assertNotNull(saved.getId());
        assertEquals("Taxi", saved.getDescription());
        assertEquals("Transport", saved.getCategory());
        verify(expenseRepository, times(1)).save(toSave);
    }
}
