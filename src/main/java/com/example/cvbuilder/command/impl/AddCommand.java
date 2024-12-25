package com.example.cvbuilder.command.impl;

import com.example.cvbuilder.command.Command;
import java.math.BigDecimal;
import java.util.Map;

public class AddCommand implements Command {
    private final Command left;
    private final Command right;
    public AddCommand(Command left, Command right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public BigDecimal execute(Map<String, BigDecimal> variables) {
        return left.execute(variables).add(right.execute(variables));
    }
}

