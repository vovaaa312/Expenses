package com.expenses.controller;

import com.expenses.model.dTo.IncomeDto;
import com.expenses.model.dTo.mapper.IncomeMapper;
import com.expenses.model.incomes.Income;
import com.expenses.model.user.User;
import com.expenses.service.IncomeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/incomes")
@RequiredArgsConstructor
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping("/findById/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or authentication.principal.id == @incomeService.findById(#id).userId")
    public ResponseEntity<?> findById(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        Income income = incomeService.findById(id);
        return ResponseEntity.ok(IncomeMapper.toDto(income));
    }

    @GetMapping("/findByUserId/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or #principal.id == #id")
    public ResponseEntity<?> findByUserId(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(IncomeMapper.toDtoList(incomeService.findAllByUserId(id)));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addIncome(
            @Valid @RequestBody IncomeDto incomeDto,
            @AuthenticationPrincipal User principal
    ) {
        incomeDto.setUserId(principal.getId());
        Income saved = incomeService.save(IncomeMapper.toEntity(incomeDto));
        return ResponseEntity.ok(IncomeMapper.toDto(saved));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or #principal.id == #incomeDto.userId")
    public ResponseEntity<?> updateIncome(
            @Valid @RequestBody IncomeDto incomeDto,
            @AuthenticationPrincipal User principal
    ) {
        Income updated = incomeService.update(IncomeMapper.toEntity(incomeDto));
        return ResponseEntity.ok(IncomeMapper.toDto(updated));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or authentication.principal.id == @incomeService.findById(#id).userId")
    public ResponseEntity<?> deleteIncome(
            @PathVariable String id,
            @AuthenticationPrincipal User principal
    ) {
        Income deleted = incomeService.delete(id);
        return ResponseEntity.ok(IncomeMapper.toDto(deleted));
    }

    @GetMapping("/findByDateBetweenAndUserId")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or #principal.id == #principal.id")
    public ResponseEntity<?> findByDateBetweenAndUserId(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(
                IncomeMapper.toDtoList(
                        incomeService.findAllByDateBetweenAndUserId(startDate, endDate, principal.getId())
                ));
    }

    @GetMapping("/findByDateAfterAndUserId")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or #principal.id == #principal.id")
    public ResponseEntity<?> findByDateAfterAndUserId(
            @RequestParam String startDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(
                IncomeMapper.toDtoList(
                        incomeService.findAllByDateAfterAndUserId(startDate, principal.getId())
                ));
    }

    @GetMapping("/findByDateBeforeAndUserId")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') or #principal.id == #principal.id")
    public ResponseEntity<?> findByDateBeforeAndUserId(
            @RequestParam String endDate,
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(
                IncomeMapper.toDtoList(
                        incomeService.findAllByDateBeforeAndUserId(endDate, principal.getId())
                ));
    }
}