package com.expenses.controller;

import com.expenses.model.dTo.IncomeDto;
import com.expenses.model.dTo.mapper.IncomeMapper;
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
            return ResponseEntity.ok(IncomeMapper.toDto(income));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }


    @GetMapping("/findByUserId/{id}")
    public ResponseEntity<?> findByUserId(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        if (principal.getId().equals(id)) {
            return ResponseEntity.ok(IncomeMapper.toDtoList(incomesService.findAllByUserId(id)));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }


    @PostMapping("/add")
    public ResponseEntity<?> addIncome(
            @RequestBody IncomeDto incomeDto,
            @AuthenticationPrincipal User principal
    ) {
        incomeDto.setUserId(principal.getId());
        Income income = IncomeMapper.toEntity(incomeDto);
        return ResponseEntity.ok(IncomeMapper.toDto(incomesService.save(income)));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateIncome(
            @RequestBody IncomeDto incomeDto,
            @AuthenticationPrincipal User principal
    ) {
        if (incomeDto.getUserId().equals(principal.getId())) {
            Income updated = incomesService.update(IncomeMapper.toEntity(incomeDto));
            return ResponseEntity.ok(IncomeMapper.toDto(updated));
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
            return ResponseEntity.ok(IncomeMapper.toDto(incomesService.delete(id)));
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

    @GetMapping("/findByDateBetweenAndUserId")
    public ResponseEntity<?> findByDateBetweenAndUserId(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(IncomeMapper.toDtoList(
                incomesService.findAllByDateBetweenAndUserId(startDate, endDate, principal.getId())
        ));
    }

    @GetMapping("/findByDateAfterAndUserId")
    public ResponseEntity<?> findByDateAfterAndUserId(
            @RequestParam String startDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(
                IncomeMapper.toDtoList(
                        incomesService.findAllByDateAfterAndUserId(startDate, principal.getId())
                )
        );
    }

    @GetMapping("/findByDateBeforeAndUserId")
    public ResponseEntity<?> findByDateBeforeAndUserId(
            @RequestParam String endDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(
                IncomeMapper.toDtoList(
                        incomesService.findAllByDateBeforeAndUserId(endDate, principal.getId())
                )
        );
    }
}