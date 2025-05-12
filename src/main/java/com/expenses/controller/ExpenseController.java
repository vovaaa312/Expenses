package com.expenses.controller;

import com.expenses.model.dTo.ExpenseDto;
import com.expenses.model.dTo.mapper.ExpenseMapper;
import com.expenses.model.expense.Expense;
import com.expenses.model.user.User;
import com.expenses.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/findById/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or authentication.principal.id == @expenseService.findById(#id).userId")
    public ResponseEntity<?> findById(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        Expense expense = expenseService.findById(id);
        return ResponseEntity.ok(ExpenseMapper.toDto(expense));
    }

    @GetMapping("/findByUserId/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or #principal.id == #id")
    public ResponseEntity<?> findByUserId(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(ExpenseMapper.toDtoList(expenseService.findAllByUserId(id)));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addExpense(
            @Valid @RequestBody ExpenseDto expense,
            @AuthenticationPrincipal User principal
    ) {
        expense.setUserId(principal.getId());
        Expense saved = expenseService.save(ExpenseMapper.toEntity(expense));
        return ResponseEntity.ok(ExpenseMapper.toDto(saved));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or #principal.id == #expense.userId")
    public ResponseEntity<?> updateExpense(
            @Valid @RequestBody ExpenseDto expense,
            @AuthenticationPrincipal User principal
    ) {
        Expense updated = expenseService.update(ExpenseMapper.toEntity(expense));
        return ResponseEntity.ok(ExpenseMapper.toDto(updated));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or authentication.principal.id == @expenseService.findById(#id).userId")
    public ResponseEntity<?> deleteExpense(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        Expense deleted = expenseService.delete(id);
        return ResponseEntity.ok(ExpenseMapper.toDto(deleted));
    }

    @GetMapping("/findByDateBetween")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or #principal.id == #principal.id")
    public ResponseEntity<?> findByDateBetweenAndUserId(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(
                ExpenseMapper.toDtoList(expenseService.findAllByDateBetweenAndUserId(startDate, endDate, principal.getId())));
    }

    @GetMapping("/findByDateAfter")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or #principal.id == #principal.id")
    public ResponseEntity<?> findByDateAfterAndUserId(
            @RequestParam String startDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(
                ExpenseMapper.toDtoList(expenseService.findAllByDateAfterAndUserId(startDate, principal.getId())));
    }

    @GetMapping("/findByDateBefore")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or #principal.id == #principal.id")
    public ResponseEntity<?> findByDateBeforeAndUserId(
            @RequestParam String endDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(
                ExpenseMapper.toDtoList(expenseService.findAllByDateBeforeAndUserId(endDate, principal.getId())));
    }
}