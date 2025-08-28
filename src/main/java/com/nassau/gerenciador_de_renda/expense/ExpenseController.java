package com.nassau.gerenciador_de_renda.expense;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    //@Autowired
   // private ExpenseService expenseService;

    @GetMapping
    public String getExpense() {
        return "List of expenses";
    }


}
