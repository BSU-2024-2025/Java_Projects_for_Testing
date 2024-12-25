package com.example.cvbuilder.command.impl;

import com.example.cvbuilder.command.Command;
import java.math.BigDecimal;
import java.util.Map;

public class AssignCommand implements Command {
    private final String variable;
    private final Command value;

    public AssignCommand(String variable, Command value) {
        this.variable = variable;
        this.value = value;
    }

    @Override
    public BigDecimal execute(Map<String, BigDecimal> variables) {
        BigDecimal result = value.execute(variables);
        variables.put(variable, result);
        return result;
    }
}
