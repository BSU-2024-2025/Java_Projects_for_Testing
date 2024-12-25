package com.example.cvbuilder.command.impl;

import com.example.cvbuilder.command.Command;
import java.math.BigDecimal;
import java.util.Map;

public class ValueCommand implements Command {
    private final BigDecimal value;

    public ValueCommand(BigDecimal value) {
        this.value = value;
    }

    @Override
    public BigDecimal execute(Map<String, BigDecimal> variables) {
        return value;
    }
}
