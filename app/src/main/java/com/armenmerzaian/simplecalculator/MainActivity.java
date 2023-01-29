package com.armenmerzaian.simplecalculator;

import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
        tv_Input = (TextView) findViewById(R.id.calcInput);
        tv_Result = (TextView) findViewById(R.id.calcResult);
        resetTextViews();

        btn_Clear = (Button) findViewById(R.id.buttonClear);
        btn_PlusMinus = (Button) findViewById(R.id.buttonPlusMinus);
        btn_Percent = (Button) findViewById(R.id.buttonPercent);
        btn_Divide = (Button) findViewById(R.id.buttonDivide);

        btn_Seven = (Button) findViewById(R.id.buttonSeven);
        btn_Eight = (Button) findViewById(R.id.buttonEight);
        btn_Nine = (Button) findViewById(R.id.buttonNine);
        btn_Multiply = (Button) findViewById(R.id.buttonMultiply);

        btn_Four = (Button) findViewById(R.id.buttonFour);
        btn_Five = (Button) findViewById(R.id.buttonFive);
        btn_Six = (Button) findViewById(R.id.buttonSix);
        btn_Minus = (Button) findViewById(R.id.buttonMinus);


        btn_One = (Button) findViewById(R.id.buttonOne);
        btn_Two = (Button) findViewById(R.id.buttonTwo);
        btn_Three = (Button) findViewById(R.id.buttonThree);
        btn_Plus = (Button) findViewById(R.id.buttonPlus);

        btn_Zero = (Button) findViewById(R.id.buttonZero);
        btn_Decimal = (Button) findViewById(R.id.buttonDecimal);
        btn_Equals = (Button) findViewById(R.id.buttonEquals);

        //add the button widgets to an array for better developer experience
        Collection<View> widgetListNums = new ArrayList<View>();
        Collection<View> widgetListOps = new ArrayList<View>();
        Collections.addAll(widgetListNums,
                btn_Seven, btn_Eight, btn_Nine,
                btn_Four, btn_Five, btn_Six,
                btn_One, btn_Two, btn_Three,
                btn_Zero);
        Collections.addAll(widgetListOps,
                btn_Percent, btn_Divide, btn_Multiply, btn_Minus, btn_Plus, btn_Decimal);

        //add onClick listeners to each NUMBER widget
        widgetListNums.forEach(widget -> widget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculatorBrain.pushValue(((TextView)v).getText().toString());
                refreshInput();
            }
        }));

        // add onClick listeners to each OPERATION widget
        widgetListOps.forEach(widget -> widget.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(CalculatorBrain.getLastInput().matches("[^%÷x\\-+.]")) { //anything that's not %÷x-+
                    CalculatorBrain.pushValue(((TextView)v).getText().toString());
                    refreshInput();
                }
            }
        }));

        btn_PlusMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(CalculatorBrain.getLastInput().matches("^-?\\d+$")){ // matches any pos or neg integer
                    CalculatorBrain.flipSign();
                    refreshInput();
                }*/
                if(!CalculatorBrain.getInputValues().isEmpty()){
                    CalculatorBrain.flipSign();
                    refreshInput();
                }
            }
        });

        btn_Clear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                resetTextViews();
                return true;
            }
        });

        btn_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CalculatorBrain.getInputValues().isEmpty()) {
                    CalculatorBrain.popBack();
                    refreshInput();
                }
            }
        });

        btn_Equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CalculatorBrain.getLastInput().matches("[^%÷x\\-+.]")) { //anything that's not %÷x-+
                    CalculatorBrain.calculate();
                    refreshOutput();
                }
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
        tv_Result.setText(CalculatorBrain.getOutputValue().toString());
    }






}