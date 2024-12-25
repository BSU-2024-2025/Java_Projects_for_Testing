package com.example.cvbuilder.command.impl;

import com.example.cvbuilder.command.Command;

import java.math.BigDecimal;
import java.util.Map;

public class VariableCommand implements Command {
    private final String variable;

    public VariableCommand(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    public BigDecimal execute(Map<String, BigDecimal> variables) {
        return variables.getOrDefault(variable, BigDecimal.ZERO);
    }
}
