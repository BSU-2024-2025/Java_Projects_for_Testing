package com.example.cvbuilder.controller;

import com.udojava.evalex.Expression;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;

@Controller
public class CalculatorController {

    @GetMapping("/")
    public String showForm() {
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam("expression") String expression, Model model) {
        try {
            BigDecimal result = evaluateExpression(expression);
            calculate(expression);
            model.addAttribute("result", result);
        } catch (Exception e) {
            model.addAttribute("error", "Invalid expression: " + e.getMessage());
        }
        return "calculator";
    }
    @PostMapping("/textinput")
    public String textInput(@RequestParam("textinput") String expression, Model model) {
        try {
            String textresult = expression;
            calculate(expression);
            model.addAttribute("textresult", textresult);
        } catch (Exception e) {
            model.addAttribute("error", "Invalid expression: " + e.getMessage());
        }
        return "calculator";
    }

    private BigDecimal evaluateExpression(String expression) throws Exception {
        return new Expression(expression).eval();
    }
    private int calculate(String expression){
        String exp = expression.replaceAll(" ","");
        int result = 0;
        for(int i = 0; i < exp.length(); i++){
            result = Character.getNumericValue(exp.charAt(i));
        }
        System.out.println("result = "+result);
        return result;
    }
}
