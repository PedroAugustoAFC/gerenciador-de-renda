package com.nassau.gerenciador_de_renda.api.revenue.controller;

import com.nassau.gerenciador_de_renda.api.revenue.dto.RevenueDTO;
import com.nassau.gerenciador_de_renda.api.revenue.dto.RevenueUpdateDTO;
import com.nassau.gerenciador_de_renda.api.revenue.service.RevenueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/revenues")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @PatchMapping("/{id}")
    public ResponseEntity<RevenueDTO> revenueUpdate(@PathVariable("id") UUID id, @Valid @RequestBody RevenueUpdateDTO revenueUpdateDTO) {
        RevenueDTO revenueDTO = revenueService.updateRevenue(id, revenueUpdateDTO);
        return ResponseEntity.ok(revenueDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> revenueDelete(@PathVariable("id") UUID id) {
        revenueService.deleteRevenue(id);
        return ResponseEntity.noContent().build();
    }
}
