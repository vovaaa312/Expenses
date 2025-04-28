package com.expenses.controller;

import com.expenses.model.expense.Expense;
import com.expenses.service.AuthService;
import com.expenses.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ExpenseController {

    private final ExpenseService expenseService;
    private final AuthService authService;

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        var user = authService.getAuthenticatedUser(authHeader);
        var expense = expenseService.findById(id);
        if (expense.getUserId().equals(user.getId())) {
            return ResponseEntity.ok(expense);
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @GetMapping("/findByUserId/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        var user = authService.getAuthenticatedUser(authHeader);
        if (user.getId().equals(id)) {
            return ResponseEntity.ok(expenseService.findAllByUserId(id));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestBody Expense expense, @RequestHeader("Authorization") String authHeader) {
        var user = authService.getAuthenticatedUser(authHeader);
        expense.setUserId(user.getId());
        return ResponseEntity.ok(expenseService.save(expense));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateExpense(@RequestBody Expense expense, @RequestHeader("Authorization") String authHeader) {
        var user = authService.getAuthenticatedUser(authHeader);
        if (expense.getUserId().equals(user.getId())) {
            return ResponseEntity.ok(expenseService.update(expense));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        var user = authService.getAuthenticatedUser(authHeader);
        var expense = expenseService.findById(id);
        if (expense.getUserId().equals(user.getId())) {
            return ResponseEntity.ok(expenseService.delete(id));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @GetMapping("/findByDateBetween")
    public ResponseEntity<?> findByDateBetweenAndUserId(@RequestParam String startDate, @RequestParam String endDate, @RequestHeader("Authorization") String authHeader) {
        var user = authService.getAuthenticatedUser(authHeader);
        return ResponseEntity.ok(expenseService.findAllByDateBetweenAndUserId(startDate, endDate, user.getId()));
    }

    @GetMapping("/findByDateAfter")
    public ResponseEntity<?> findByDateAfterAndUserId(@RequestParam String startDate, @RequestHeader("Authorization") String authHeader) {
        var user = authService.getAuthenticatedUser(authHeader);
        return ResponseEntity.ok(expenseService.findAllByDateAfterAndUserId(startDate, user.getId()));
    }

    @GetMapping("/findByDateBefore")
    public ResponseEntity<?> findByDateBeforeAndUserId(@RequestParam String endDate, @RequestHeader("Authorization") String authHeader) {
        var user = authService.getAuthenticatedUser(authHeader);
        return ResponseEntity.ok(expenseService.findAllByDateBeforeAndUserId(endDate, user.getId()));
    }

//    @GetMapping("/findByDateBetweenAndUser")
//    public ResponseEntity<?> findByDateBetweenAndUser(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String userId) {
//        return ResponseEntity.ok(expenseService.findAllByDateBetweenAndUserId(startDate, endDate, userId));
//    }
//
//    @GetMapping("/findByDateAfterAndUser")
//    public ResponseEntity<?> findByDateAfterAndUser(@RequestParam String startDate, @RequestParam String userId) {
//        return ResponseEntity.ok(expenseService.findAllByDateAfterAndUserId(startDate, userId));
//    }
}