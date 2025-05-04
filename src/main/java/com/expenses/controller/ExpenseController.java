package com.expenses.controller;

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
            return ResponseEntity.ok(expense);
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @GetMapping("/findByUserId/{id}")
    public ResponseEntity<?> findByUserId(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        if (principal.getId().equals(id)) {
            return ResponseEntity.ok(expenseService.findAllByUserId(id));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addExpense(
            @RequestBody Expense expense,
            @AuthenticationPrincipal User principal
    ) {
        expense.setUserId(principal.getId());
        return ResponseEntity.ok(expenseService.save(expense));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateExpense(
            @RequestBody Expense expense,
            @AuthenticationPrincipal User principal
    ) {
        if (expense.getUserId().equals(principal.getId())) {
            return ResponseEntity.ok(expenseService.update(expense));
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
            return ResponseEntity.ok(expenseService.delete(id));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @GetMapping("/findByDateBetween")
    public ResponseEntity<?> findByDateBetweenAndUserId(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(expenseService.findAllByDateBetweenAndUserId(startDate, endDate, principal.getId()));
    }

    @GetMapping("/findByDateAfter")
    public ResponseEntity<?> findByDateAfterAndUserId(
            @RequestParam String startDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(expenseService.findAllByDateAfterAndUserId(startDate, principal.getId()));
    }

    @GetMapping("/findByDateBefore")
    public ResponseEntity<?> findByDateBeforeAndUserId(@RequestParam String endDate,  @AuthenticationPrincipal User principal) {
        return ResponseEntity.ok(expenseService.findAllByDateBeforeAndUserId(endDate, principal.getId()));
    }



}