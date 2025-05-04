package com.expenses.controller;

import com.expenses.model.incomes.Income;
import com.expenses.model.user.User;
import com.expenses.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incomes")
@RequiredArgsConstructor
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class IncomeController {

    private final IncomeService incomesService;


    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        var income = incomesService.findById(id);
        if (income.getUserId().equals(principal.getId())) {
            return ResponseEntity.ok(income);
        }
        return ResponseEntity.status(403).body("Access Denied");
    }


    @GetMapping("/findByUserId/{id}")
    public ResponseEntity<?> findByUserId(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        if (principal.getId().equals(id)) {
            return ResponseEntity.ok(incomesService.findAllByUserId(id));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }


    @PostMapping("/add")
    public ResponseEntity<?> addIncome(
            @RequestBody Income income,
            @AuthenticationPrincipal User principal
    ) {
        income.setUserId(principal.getId());
        return ResponseEntity.ok(incomesService.save(income));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateIncome(
            @RequestBody Income income,
            @AuthenticationPrincipal User principal
    ) {
        if (income.getUserId().equals(principal.getId())) {
            return ResponseEntity.ok(incomesService.update(income));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteIncome(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        var income = incomesService.findById(id);
        if (income.getUserId().equals(principal.getId())) {
            return ResponseEntity.ok(incomesService.delete(id));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @GetMapping("/findByDateBetweenAndUserId")
    public ResponseEntity<?> findByDateBetweenAndUserId(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(incomesService.findAllByDateBetweenAndUserId(startDate, endDate, principal.getId()));
    }

    @GetMapping("/findByDateAfterAndUserId")
    public ResponseEntity<?> findByDateAfterAndUserId(
            @RequestParam String startDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(incomesService.findAllByDateAfterAndUserId(startDate, principal.getId()));
    }

    @GetMapping("/findByDateBeforeAndUserId")
    public ResponseEntity<?> findByDateBeforeAndUserId(
            @RequestParam String endDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(incomesService.findAllByDateBeforeAndUserId(endDate, principal.getId()));
    }
}