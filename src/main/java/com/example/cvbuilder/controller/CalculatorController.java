package com.example.cvbuilder.controller;

import com.example.cvbuilder.command.Command;
import com.example.cvbuilder.parser.Parser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
//(5+3/2-(8+16)/(5*10))*(10*20/2+8)/(1/10-10/4+5*5) TODO
@Controller
public class CalculatorController {

    private final List<String> expressions = new ArrayList<>();
    private final List<String> results = new ArrayList<>();
    private final Parser parser = new Parser();

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("expressions", expressions);
        model.addAttribute("results", results);
        return "calculator";
    }

    @PostMapping("/save")
    public String saveExpression(@RequestParam("expression") String expression, Model model) {
        if (validateExpression(expression)) {
            expressions.add(expression);
        } else {
            model.addAttribute("error", "Invalid expression.");
        }
        model.addAttribute("expressions", expressions);
        model.addAttribute("results", results);
        return "calculator";
    }

    @PostMapping("/run")
    public String runExpression(@RequestParam("expression") String expression, Model model) {
        try {
            List<Command> commands = parser.parse(expression);
            BigDecimal result = commands.get(commands.size() - 1).execute();
            results.add("Expression: " + expression + ", Result: " + result);
        } catch (Exception e) {
            results.add("Error: " + e.getMessage());
        }
        model.addAttribute("expressions", expressions);
        model.addAttribute("results", results);
        return "calculator";
    }

    private boolean validateExpression(String expression) {
        try {
            parser.parse(expression);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
