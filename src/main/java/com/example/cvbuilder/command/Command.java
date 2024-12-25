package com.example.cvbuilder.command;

import java.math.BigDecimal;
import java.util.Map;

public interface Command {
    BigDecimal execute(Map<String, BigDecimal> variables);
}
