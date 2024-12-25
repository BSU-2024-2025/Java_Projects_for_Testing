package com.example.cvbuilder.command.impl;

import com.example.cvbuilder.command.Command;

import java.math.BigDecimal;

public class SubtractCommand implements Command {
    private final BigDecimal a;
    private final BigDecimal b;

    public SubtractCommand(BigDecimal a, BigDecimal b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public BigDecimal execute() {
        return a.subtract(b);
    }
}