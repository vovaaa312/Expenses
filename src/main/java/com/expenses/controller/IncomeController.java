package com.expenses.controller;

import com.expenses.model.incomes.Income;
import com.expenses.service.AuthService;
import com.expenses.service.IncomesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incomes")
@RequiredArgsConstructor
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class IncomeController {

    private final IncomesService incomesService;
    private final AuthService authService;

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        var user = authService.getAuthenticatedUser(authHeader);
        var income = incomesService.findById(id);
        if (income.getUserId().equals(user.getId())) {
            return ResponseEntity.ok(income);
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @GetMapping("/findByUserId/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        var user = authService.getAuthenticatedUser(authHeader);
        if (user.getId().equals(id)) {
            return ResponseEntity.ok(incomesService.findAllByUserId(id));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addIncome(@RequestBody Income income, @RequestHeader("Authorization") String authHeader) {
        var user = authService.getAuthenticatedUser(authHeader);
        income.setUserId(user.getId());
        return ResponseEntity.ok(incomesService.save(income));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateIncome(@RequestBody Income income, @RequestHeader("Authorization") String authHeader) {
        var user = authService.getAuthenticatedUser(authHeader);
        if (income.getUserId().equals(user.getId())) {
            return ResponseEntity.ok(incomesService.update(income));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        var user = authService.getAuthenticatedUser(authHeader);
        var income = incomesService.findById(id);
        if (income.getUserId().equals(user.getId())) {
            return ResponseEntity.ok(incomesService.delete(id));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }
}