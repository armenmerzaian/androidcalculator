package com.armenmerzaian.simplecalculator;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorBrain {

    private static final int MAXSIZE = 16;
    //initialize member variables
    private static final ArrayList<String> m_inputValues = new ArrayList<>();
    private static Number m_outputValue = 0;

    public static void resetAll(){
        m_inputValues.clear();
        m_outputValue = 0;
    }

    public static void popBack(){
        if(!m_inputValues.isEmpty()){
            m_inputValues.remove(m_inputValues.size() - 1);
        }
    }

    private static void setOutputValue(Number val){
        m_outputValue = val;
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
        return m_outputValue.toString();
    }

    public static void flipSign(){
        if(!m_inputValues.isEmpty()){
            int lastIndex;
            // Starting from the last index of the list
            for (lastIndex = m_inputValues.size()-1; lastIndex>=0; lastIndex--) {
                if (m_inputValues.get(lastIndex).matches("[+\\-x÷%]")) { // matches one of ops
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
        //TODO: Calculator logic
        // mainActivity pushes user inputs to m_inputValues array
        // this function should parse the array and perform the calculation
        // once finished call setOutputValue(value) with the calculated value
        String operatorRegex = "[+\\-x÷%/]";
        Pattern operatorPattern = Pattern.compile(operatorRegex);
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<String> operators = new ArrayList<>();
        for (int i = 0; i < m_inputValues.size(); i++) {
            String current = m_inputValues.get(i);
            Matcher operatorMatcher = operatorPattern.matcher(current);
            if (operatorMatcher.matches()) {
                operators.add(current);
            } else {
                numbers.add(current);
            }
        }
        double currentNumber = Double.parseDouble(numbers.get(0));
        for (int i = 0; i < operators.size(); i++) {
            String operator = operators.get(i);
            double nextNumber = Double.parseDouble(numbers.get(i + 1));
            if (operator.equals("+")) {
                currentNumber += nextNumber;
            } else if (operator.equals("-")) {
                currentNumber -= nextNumber;
            } else if (operator.equals("x")) {
                currentNumber *= nextNumber;
            } else if (operator.equals("÷")) {
                currentNumber /= nextNumber;
            } else if (operator.equals("%")) {
                currentNumber %= nextNumber;
            }
        }
        setOutputValue(currentNumber);
    }
}
