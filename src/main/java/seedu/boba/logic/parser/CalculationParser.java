package seedu.boba.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Class for reading and understanding the arithmetic expression.
 * Adapted from: https://www.daniweb.com/programming/software-development/
 *      threads/442690/java-expression-parser-calculator
 * with modifications
 */
public class CalculationParser {
    private static final int HIGH_PRECEDENCE = 2;
    private static final int LOW_PRECEDENCE = 0;

    // All operators set with their precedence
    private static final Map<String, Integer> OPERATORS = new HashMap<>();

    static {
        // Map<"token", precedence>
        OPERATORS.put("+", LOW_PRECEDENCE);
        OPERATORS.put("-", LOW_PRECEDENCE);
        OPERATORS.put("*", HIGH_PRECEDENCE);
        OPERATORS.put("/", HIGH_PRECEDENCE);
    }

    // Test if token is an operator
    private static boolean isOperator(String token) {
        return OPERATORS.containsKey(token);
    }

    // Compare precedence of operators.
    private static int comparePrecedence(String token1, String token2) {
        if (!isOperator(token1) || !isOperator(token2)) {
            throw new IllegalArgumentException("Invalid tokens: " + token1
                    + " " + token2);
        }
        return OPERATORS.get(token1) - OPERATORS.get(token2);
    }

    /**
     * Convert infix expression format into Reverse Polish notation (RPN),
     * this will reorder to tokens so that it's easier for program to understand.
     * @param inputTokens Tokens in the expression
     * @return Converted RPN
     */
    public static String[] expressionToRpn(String[] inputTokens) {
        // Reverse Polish notation
        ArrayList<String> rpn = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        // For each token
        for (String token : inputTokens) {
            if (isOperator(token)) {
                // If token is an operator.
                // While stack not empty AND stack top element
                // is an operator and have higher precedence
                while (!stack.empty()
                        && isOperator(stack.peek())
                        && comparePrecedence(token, stack.peek()) <= 0) {
                    rpn.add(stack.pop());
                }
                // Push the new operator on the stack
                stack.push(token);
            } else if (token.equals("(")) {
                // If token is a left parenthesis
                stack.push(token);
            } else if (token.equals(")")) {
                // If token is a right parenthesis
                while (!stack.empty() && !stack.peek().equals("(")) {
                    rpn.add(stack.pop());
                }
                stack.pop();
            } else {
                // If token is a number
                rpn.add(token);
            }
        }
        while (!stack.empty()) {
            rpn.add(stack.pop());
        }

        assert stack.empty() : "Stack is not empty at the end";

        String[] rpnStrArr = new String[rpn.size()];
        return rpn.toArray(rpnStrArr);
    }

    /**
     * Do calculation, turn RPN into result in type double
     * @param tokens RPN
     * @return Calculation result
     */
    public static double rpnToDouble(String[] tokens) {
        Stack<String> stack = new Stack<>();

        for (String token : tokens) {
            // If the token is a value, push it onto the stack
            if (!isOperator(token)) {
                stack.push(token);
            } else {
                // Token is an operator: pop top two entries
                Double d2 = Double.valueOf(stack.pop());
                Double d1 = Double.valueOf(stack.pop());

                // Calculate the result
                Double result = token.compareTo("*") == 0
                        ? d1 * d2
                        : token.compareTo("/") == 0
                        ? d1 / d2
                        : token.compareTo("+") == 0
                        ? d1 + d2
                        : d1 - d2;
                // Push result onto stack
                stack.push(String.valueOf(result));
            }
        }

        return Double.parseDouble(stack.pop());
    }

    /**
     * Main logic (method) for the calculation reader.
     * @param userInput Cashier's input in String
     * @return The result of calculation, rounded to 2 d.p.
     */
    public static String parseCalculation(String userInput) {
        String regex = "((?<=[(|)|\\+|\\*|\\-|/])|(?=[(|)|\\+|\\*|\\-|/]))";
        String resultStr;
        // System.out.println(userInput);
        assert OPERATORS.containsKey("+") : "Don't have addition";
        assert OPERATORS.containsKey("-") : "Don't have subtraction";
        assert OPERATORS.containsKey("*") : "Don't have multiplication";
        assert OPERATORS.containsKey("/") : "Don't have division";

        String[] input = userInput.split(regex);
        String[] output = expressionToRpn(input);
        System.out.println(" ");
        // Feed the rpn string to rpntoDouble to give result
        Double result = rpnToDouble(output);
        resultStr = String.format("%.2f", result);

        return resultStr;
    }
    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof CalculationParser;
    }
}
