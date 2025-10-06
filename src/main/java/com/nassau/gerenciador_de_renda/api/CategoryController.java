package com.nassau.gerenciador_de_renda.api;

import com.nassau.gerenciador_de_renda.api.expense.model.categoryEnum.ExpenseCategory;
import com.nassau.gerenciador_de_renda.api.revenue.model.categoryEnum.RevenueCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping("/expenses")
    public ResponseEntity<List<String>> getExpenseCategories() {
        List<String> categories = Arrays.stream(ExpenseCategory.values())
                .map(ExpenseCategory::getDisplayName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/revenues")
    public ResponseEntity<List<String>> getRevenueCategories() {
        List<String> categories = Arrays.stream(RevenueCategory.values())
                .map(RevenueCategory::getDisplayName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }
}
