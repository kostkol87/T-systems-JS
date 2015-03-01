package com.tsystems.javaschool.tasks;

import java.math.BigDecimal;

/**
 * Created by kostkol87 on 10.12.14.
 */
public class CalculatorImpl implements Calculator {

    private final int NUMBER = 1;
    private final String END_OF_EXPRESSION = "\0";

    private String expression;
    private int currentIndex;
    private String token;
    private int tokenType;

    @Override
    public String evaluate(String str) {
        str = str.replace(" ","");
        double result;
        expression = str;
        currentIndex = 0;
        getToken();

        try {
            if(token.equals(END_OF_EXPRESSION)){
                return null;
            }

            result = evalExp2();

            if(!token.equals(END_OF_EXPRESSION)) {
                return null;
            }
            //math round
            result = new BigDecimal(result).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

            return String.valueOf(result);
        }catch (ArithmeticException e){
            return null;
        }
    }

    // sum & difference
    private double evalExp2() {
        char operator;
        double result = evalExp3(), partialResult;

        while ((operator = token.charAt(0)) == '+' || operator == '-') {
            getToken();
            partialResult = evalExp3();
            result += operator == '+' ? + partialResult : - partialResult;
        }
        return result;
    }

    // multiplication & division
    private double evalExp3() {
        char operator;
        double result = evalExp4(), partialResult;

        while((operator = token.charAt(0)) == '*' || operator == '/') {
            getToken();
            partialResult = evalExp4();

            if (operator == '*') result *= partialResult;
            else {
                if (partialResult != 0) result /= partialResult;
                else throw new ArithmeticException();
            }
        }
        return result;
    }

    // parentheses (   )
    private double evalExp4() {
        double result;

        if (token.equals("(")) {
            getToken();
            result = evalExp2();
            if (!token.equals(")"))  throw new ArithmeticException();
            getToken();
        } else result = atom();

        return result;
    }

    // get numeric lexem
    private double atom() {
        double result = 0;

        if (tokenType == NUMBER){
            result = Double.parseDouble(token);
            getToken();
        }
        return result;
    }

    // lexem
    private void getToken() {
        tokenType = 0;
        token = "";

        // end of expression
        if (currentIndex == expression.length()) {
            token = END_OF_EXPRESSION;
            return;
        }

        if (isOperator(expression.charAt(currentIndex))) {
            token += expression.charAt(currentIndex);
            currentIndex++;
        } else if (Character.isDigit(expression.charAt(currentIndex))) {
            while (!isOperator(expression.charAt(currentIndex))) {
                token += expression.charAt(currentIndex);
                currentIndex++;
                if (currentIndex >= expression.length()) break;
            }
            tokenType = NUMBER;
        }
    }

    // operator
    private boolean isOperator(char c) {
        return  "+-/*()".indexOf(c) != -1;
    }
}
