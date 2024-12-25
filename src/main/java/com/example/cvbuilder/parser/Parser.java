package com.example.cvbuilder.parser;

import com.example.cvbuilder.command.Command;
import com.example.cvbuilder.command.impl.*;
import java.math.BigDecimal;
import java.util.*;

public class Parser {

    private static final Map<Character, Integer> OPERATOR_PRECEDENCE;

    static {
        OPERATOR_PRECEDENCE = new HashMap<>();
        OPERATOR_PRECEDENCE.put('+', 1);
        OPERATOR_PRECEDENCE.put('-', 1);
        OPERATOR_PRECEDENCE.put('*', 2);
        OPERATOR_PRECEDENCE.put('/', 2);
    }

    public List<Command> parse(String expression) throws Exception {
        expression = expression.replaceAll("\\s+", ""); // Убираем пробелы из выражения
        List<Command> commands = new ArrayList<>();
        Deque<Character> operators = new ArrayDeque<>();
        Deque<BigDecimal> operands = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                i--; // компенсируем цикл while
                operands.push(new BigDecimal(sb.toString()));
            } else if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    processOperator(operators.pop(), operands, commands);
                }
                operators.pop(); // удаляем '('
            } else if (OPERATOR_PRECEDENCE.containsKey(ch)) {
                while (!operators.isEmpty() && operators.peek() != '(' && OPERATOR_PRECEDENCE.get(operators.peek()) >= OPERATOR_PRECEDENCE.get(ch)) {
                    processOperator(operators.pop(), operands, commands);
                }
                operators.push(ch);
            } else {
                throw new Exception("Unsupported operation: " + ch);
            }
        }

        while (!operators.isEmpty()) {
            processOperator(operators.pop(), operands, commands);
        }

        return commands;
    }

    private void processOperator(char operator, Deque<BigDecimal> operands, List<Command> commands) throws Exception {
        BigDecimal b = operands.pop();
        BigDecimal a = operands.pop();
        Command command;
        switch (operator) {
            case '+' -> command = new AddCommand(a, b);
            case '-' -> command = new SubtractCommand(a, b);
            case '*' -> command = new MultiplyCommand(a, b);
            case '/' -> command = new DivideCommand(a, b);
            default -> throw new Exception("Unsupported operation: " + operator);
        }
        commands.add(command);
        operands.push(command.execute());
    }
}
