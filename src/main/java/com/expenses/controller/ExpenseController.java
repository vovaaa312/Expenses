package com.expenses.controller;

import com.expenses.model.dTo.ExpenseDto;
import com.expenses.model.dTo.mapper.ExpenseMapper;
import com.expenses.model.expense.Expense;
import com.expenses.model.user.User;
import com.expenses.service.AuthService;
import com.expenses.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        var expense = expenseService.findById(id);
        if (expense.getUserId().equals(principal.getId())) {
            return ResponseEntity.ok(ExpenseMapper.toDto(expense));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @GetMapping("/findByUserId/{id}")
    public ResponseEntity<?> findByUserId(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        if (principal.getId().equals(id)) {
//            return ResponseEntity.ok(expenseService.findAllByUserId(id));
            return ResponseEntity.ok(ExpenseMapper.toDtoList(expenseService.findAllByUserId(id)));

        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addExpense(
            @RequestBody ExpenseDto expense,
            @AuthenticationPrincipal User principal
    ) {
        expense.setUserId(principal.getId());
        Expense saved = expenseService.save(ExpenseMapper.toEntity(expense));
        return ResponseEntity.ok(ExpenseMapper.toDto(saved));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateExpense(
            @RequestBody ExpenseDto expense,
            @AuthenticationPrincipal User principal
    ) {

        if (expense.getUserId().equals(principal.getId())) {
            Expense updated = expenseService.update(ExpenseMapper.toEntity(expense));
            return ResponseEntity.ok(ExpenseMapper.toDto(updated));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExpense(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        var expense = expenseService.findById(id);
        if (expense.getUserId().equals(principal.getId())) {
            return ResponseEntity.ok(ExpenseMapper.toDto(expenseService.delete(id)));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @GetMapping("/findByDateBetween")
    public ResponseEntity<?> findByDateBetweenAndUserId(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok
                (ExpenseMapper.toDtoList(expenseService.findAllByDateBetweenAndUserId(startDate, endDate, principal.getId())));
    }

    @GetMapping("/findByDateAfter")
    public ResponseEntity<?> findByDateAfterAndUserId(
            @RequestParam String startDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(
                ExpenseMapper.toDtoList(expenseService.findAllByDateAfterAndUserId(startDate, principal.getId())));
    }

    @GetMapping("/findByDateBefore")
    public ResponseEntity<?> findByDateBeforeAndUserId(@RequestParam String endDate,  @AuthenticationPrincipal User principal) {
        return ResponseEntity.ok(
                ExpenseMapper.toDtoList(expenseService.findAllByDateBeforeAndUserId(endDate, principal.getId())));
    }



}