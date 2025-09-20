package com.nassau.gerenciador_de_renda.revenue.controller;

import com.nassau.gerenciador_de_renda.expense.dto.ExpenseFullDTO;
import com.nassau.gerenciador_de_renda.expense.dto.ExpenseUpdateDTO;
import com.nassau.gerenciador_de_renda.revenue.dto.RevenueFullDTO;
import com.nassau.gerenciador_de_renda.revenue.dto.RevenueUpdateDTO;
import com.nassau.gerenciador_de_renda.revenue.service.RevenueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/revenues")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @GetMapping("/{id}")
    public ResponseEntity<?> revenueById(@PathVariable("id") Long id) {
        RevenueFullDTO revenueFullDTO = revenueService.getRevenueById(id);
        return ResponseEntity.ok(revenueFullDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RevenueFullDTO> revenueUpdate(@PathVariable("id") Long id, @Valid @RequestBody RevenueUpdateDTO revenueUpdateDTO) {
        RevenueFullDTO revenueFullDTO = revenueService.updateRevenue(id, revenueUpdateDTO);
        return ResponseEntity.ok(revenueFullDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> revenueDelete(@PathVariable("id") Long id) {
        revenueService.deleteRevenue(id);
        return ResponseEntity.noContent().build();
    }
}
