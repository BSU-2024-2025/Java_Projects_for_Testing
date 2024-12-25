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

        String[] statements = expression.split(";"); // Разделяем на отдельные выражения
        for (String statement : statements) {
            Deque<Character> operators = new ArrayDeque<>();
            Deque<Command> operands = new ArrayDeque<>();

            for (int i = 0; i < statement.length(); i++) {
                char ch = statement.charAt(i);

                if (Character.isDigit(ch) || ch == '.') {
                    StringBuilder sb = new StringBuilder();
                    while (i < statement.length() && (Character.isDigit(statement.charAt(i)) || statement.charAt(i) == '.')) {
                        sb.append(statement.charAt(i));
                        i++;
                    }
                    i--; // компенсируем цикл while
                    operands.push(new ValueCommand(new BigDecimal(sb.toString())));
                } else if (Character.isLetter(ch)) {
                    StringBuilder sb = new StringBuilder();
                    while (i < statement.length() && Character.isLetter(statement.charAt(i))) {
                        sb.append(statement.charAt(i));
                        i++;
                    }
                    i--; // компенсируем цикл while
                    operands.push(new VariableCommand(sb.toString()));
                } else if (ch == '(') {
                    operators.push(ch);
                } else if (ch == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        processOperator(operators.pop(), operands);
                    }
                    operators.pop(); // удаляем '('
                } else if (OPERATOR_PRECEDENCE.containsKey(ch)) {
                    while (!operators.isEmpty() && operators.peek() != '(' && OPERATOR_PRECEDENCE.get(operators.peek()) >= OPERATOR_PRECEDENCE.get(ch)) {
                        processOperator(operators.pop(), operands);
                    }
                    operators.push(ch);
                } else if (ch == '=') {
                    String variable = ((VariableCommand) operands.pop()).getVariable();
                    i++;
                    if (i < statement.length() && statement.charAt(i) == ' ') {
                        i++; // Пропускаем пробел после знака равенства
                    }
                    List<Command> rightCommands = parse(statement.substring(i));
                    Command right = rightCommands.get(rightCommands.size() - 1);
                    commands.add(new AssignCommand(variable, right));
                    break;
                } else {
                    throw new Exception("Unsupported operation: " + ch);
                }
            }

            while (!operators.isEmpty()) {
                processOperator(operators.pop(), operands);
            }

            if (!operands.isEmpty()) {
                commands.add(operands.pop());
            }
        }

        return commands;
    }

    private void processOperator(char operator, Deque<Command> operands) throws Exception {
        Command right = operands.pop();
        Command left = operands.pop();
        Command command;
        switch (operator) {
            case '+' -> command = new AddCommand(left, right);
            case '-' -> command = new SubtractCommand(left, right);
            case '*' -> command = new MultiplyCommand(left, right);
            case '/' -> command = new DivideCommand(left, right);
            default -> throw new Exception("Unsupported operation: " + operator);
        }
        operands.push(command);
    }
}
