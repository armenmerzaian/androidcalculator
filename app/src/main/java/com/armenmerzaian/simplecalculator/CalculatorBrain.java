package com.armenmerzaian.simplecalculator;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorBrain {

    private static final int MAXSIZE = 16;
    //initialize member variables
    private static final ArrayList<String> m_inputValues = new ArrayList<>();
    private static String m_outputValue = "0";

    public static void resetAll(){
        m_inputValues.clear();
        m_outputValue = "0";
    }

    public static void popBack(){
        if(!m_inputValues.isEmpty()){
            m_inputValues.remove(m_inputValues.size() - 1);
        }
    }

    private static void setOutputValue(Object val){
        m_outputValue = String.valueOf(val);
    }

    public static String getInputValues() {
        StringBuilder composite = new StringBuilder();
        for(String a : m_inputValues){
            if(a.equals("%") || a.equals("÷") || a.equals("x") || a.equals("-") || a.equals("+")){
                composite.append(" ").append(a).append(" ");
            } else {
                composite.append(a);
            }
        }
        return composite.toString();
    }

    public static String getLastInput(){
        if(!m_inputValues.isEmpty()){
            return m_inputValues.get(m_inputValues.size()-1);
        }
        return "";
    }

    public static String getOutputValue() {
        return m_outputValue;
    }

    public static void flipSign(){
        if(!m_inputValues.isEmpty()){
            int lastIndex = 0;
            // Starting from the last index of the list
            for (lastIndex = m_inputValues.size()-1; lastIndex>=0; lastIndex--) {
                if (m_inputValues.get(lastIndex).matches("^[+\\-x÷%]$")) { // matches one of ops as a single character
                    break;
                }
            }
            lastIndex++;
            if(m_inputValues.get(lastIndex).matches("^-\\d+$")){ //matches '-' at the beginning of the string
                m_inputValues.set(lastIndex, m_inputValues.get(lastIndex).substring(1));
            } else {
                m_inputValues.set(lastIndex, "-" + m_inputValues.get(lastIndex));
            }
        }
    }

    public static void pushValue(String val){
        if (m_inputValues.size() < MAXSIZE){
            m_inputValues.add(val);
        }
    }

    public static void calculate(){
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<String> operators = new ArrayList<>();

        Log.d("CalculatorBrain", "m_input: " + m_inputValues);

        for(int i = 0, index = 0; i < m_inputValues.size(); i++){
            String current = m_inputValues.get(i);
            if(current.matches("^[+\\-x÷%]$")){ //is operator
                operators.add(current);
                index++;
            } else { // not operator
                String curr;
                try{
                    curr = numbers.get(index);
                    numbers.set(index, curr + current);
                } catch (IndexOutOfBoundsException err){
                    numbers.add(current);
                }
            }
        }

        Log.d("CalculatorBrain", "numbers: " + numbers);
        Log.d("CalculatorBrain", "operators: " + operators);

        try {
            setOutputValue(calculate(numbers, operators));
        } catch (IllegalArgumentException err){
            setOutputValue(err.getMessage());
        }
    }

    private static double calculate(ArrayList<String> numbers, ArrayList<String> operators) throws IllegalArgumentException{
        Stack<Double> numStack = new Stack<>();
        Stack<String> opStack = new Stack<>();

        for (int i = 0; i < numbers.size(); i++) {
            double num = Double.parseDouble(numbers.get(i));
            numStack.push(num);
            if (i < operators.size()) {
                while (!opStack.isEmpty() && hasPrecedence(operators.get(i), opStack.peek())) {
                    numStack.push(applyOp(opStack.pop(), numStack.pop(), numStack.pop()));
                }
                opStack.push(operators.get(i));
            }
        }

        while (!opStack.isEmpty()) {
            numStack.push(applyOp(opStack.pop(), numStack.pop(), numStack.pop()));
        }

        return numStack.pop();
    }

    private static boolean hasPrecedence(String op1, String op2) {
        if (op2.equals("x") || op2.equals("÷") || op2.equals("%")) {
            return true;
        }
        if (op1.equals("x") || op1.equals("÷")) {
            return false;
        }
        return true;
    }

    private static double applyOp(String op, double b, double a) throws IllegalArgumentException {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "x":
                return a * b;
            case "%":
                if (b == 0) {
                    throw new IllegalArgumentException("Error! Percent Zero");
                }
                return a % b;
            case "÷":
                if (b == 0) {
                    throw new IllegalArgumentException("Error! Divide Zero");
                }
                return a / b;
        }
        return 0;
    }
}
