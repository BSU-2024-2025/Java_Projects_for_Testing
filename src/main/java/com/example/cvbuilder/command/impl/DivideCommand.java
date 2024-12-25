package com.example.cvbuilder.command.impl;

import com.example.cvbuilder.command.Command;
import java.math.MathContext;
import java.math.BigDecimal;

public class DivideCommand implements Command {
    private final BigDecimal a;
    private final BigDecimal b;

    public DivideCommand(BigDecimal a, BigDecimal b) {
        this.a = a;
        this.b = b;
    }

    @Override public BigDecimal execute() {
        MathContext mc = new MathContext(10); // используем MathContext для более точного округления
        return a.divide(b, mc);
    }
}