package com.armenmerzaian.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity{

    TextView tv_Input;
    TextView tv_Result;

    Button btn_Clear;
    Button btn_PlusMinus;
    Button btn_Percent;
    Button btn_Divide;

    Button btn_Seven;
    Button btn_Eight;
    Button btn_Nine;
    Button btn_Multiply;

    Button btn_Four;
    Button btn_Five;
    Button btn_Six;
    Button btn_Minus;

    Button btn_One;
    Button btn_Two;
    Button btn_Three;
    Button btn_Plus;

    Button btn_Zero;
    Button btn_Decimal;
    Button btn_Equals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize everything
        initCalculator();
    }

    public void initCalculator(){
        //init calculator brain
        //initialize the widgets
        tv_Input = findViewById(R.id.calcInput);
        tv_Result = findViewById(R.id.calcResult);
        resetTextViews();

        btn_Clear = findViewById(R.id.buttonClear);
        btn_PlusMinus = findViewById(R.id.buttonPlusMinus);
        btn_Percent = findViewById(R.id.buttonPercent);
        btn_Divide = findViewById(R.id.buttonDivide);

        btn_Seven = findViewById(R.id.buttonSeven);
        btn_Eight = findViewById(R.id.buttonEight);
        btn_Nine = findViewById(R.id.buttonNine);
        btn_Multiply = findViewById(R.id.buttonMultiply);

        btn_Four = findViewById(R.id.buttonFour);
        btn_Five = findViewById(R.id.buttonFive);
        btn_Six = findViewById(R.id.buttonSix);
        btn_Minus = findViewById(R.id.buttonMinus);


        btn_One = findViewById(R.id.buttonOne);
        btn_Two = findViewById(R.id.buttonTwo);
        btn_Three = findViewById(R.id.buttonThree);
        btn_Plus = findViewById(R.id.buttonPlus);

        btn_Zero = findViewById(R.id.buttonZero);
        btn_Decimal = findViewById(R.id.buttonDecimal);
        btn_Equals = findViewById(R.id.buttonEquals);

        //add the button widgets to an array for better developer experience
        Collection<View> widgetListNums = new ArrayList<>();
        Collection<View> widgetListOps = new ArrayList<>();
        Collections.addAll(widgetListNums,
                btn_Seven, btn_Eight, btn_Nine,
                btn_Four, btn_Five, btn_Six,
                btn_One, btn_Two, btn_Three,
                btn_Zero);
        Collections.addAll(widgetListOps,
                btn_Percent, btn_Divide, btn_Multiply, btn_Minus, btn_Plus, btn_Decimal);

        //add onClick listeners to each NUMBER widget
        widgetListNums.forEach(widget -> widget.setOnClickListener(v -> {
            CalculatorBrain.pushValue(((TextView)v).getText().toString());
            refreshInput();
        }));

        // add onClick listeners to each OPERATION widget
        widgetListOps.forEach(widget -> widget.setOnClickListener(v -> {
            if(CalculatorBrain.getLastInput().matches(".*[^%÷x\\-+]$")) { //anything that's not %÷x-+
                CalculatorBrain.pushValue(((TextView)v).getText().toString());
                refreshInput();
            }
        }));

        btn_PlusMinus.setOnClickListener(v -> {
            if(CalculatorBrain.getInputValues().isEmpty() || CalculatorBrain.getLastInput().matches("^[+\\-x÷%]$")){
                return;
            }
            CalculatorBrain.flipSign();
            refreshInput();
        });

        btn_Clear.setOnLongClickListener(v -> {
            resetTextViews();
            return true;
        });

        btn_Clear.setOnClickListener(v -> {
            if(!CalculatorBrain.getInputValues().isEmpty()) {
                CalculatorBrain.popBack();
                refreshInput();
            }
        });

        btn_Equals.setOnClickListener(v -> {
            if(!CalculatorBrain.getLastInput().matches("[%÷x\\-+.]$")) { //anything that's not %÷x-+
                CalculatorBrain.calculate();
                refreshOutput();
            }
        });
    }

    public void refreshInput(){
        tv_Input.setText(CalculatorBrain.getInputValues());
    }

    public void refreshOutput(){
        tv_Result.setText(CalculatorBrain.getOutputValue());
    }

    private void resetTextViews(){
        CalculatorBrain.resetAll();
        tv_Input.setText("");
        tv_Result.setText(CalculatorBrain.getOutputValue());
    }






}