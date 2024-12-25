package com.example.cvbuilder.command.impl;

import com.example.cvbuilder.command.Command;
import java.math.MathContext;
import java.math.BigDecimal;
import java.util.Map;
public class DivideCommand implements Command {
    private final Command left;
    private final Command right;
    public DivideCommand(Command left, Command right) {
        this.left = left; this.right = right;
    }
    @Override
    public BigDecimal execute(Map<String, BigDecimal> variables) {
        return left.execute(variables).divide(right.execute(variables), 10, BigDecimal.ROUND_DOWN);
    }
}