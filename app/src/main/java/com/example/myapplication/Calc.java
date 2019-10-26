package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Calc extends AppCompatActivity {

    private TextView _screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";

    private Button backspaceButton;
    private Button insertComma;
    private Button changeChar;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("_screen", _screen.getText().toString());
        outState.putString("display", display);
        outState.putString("currentOperator", currentOperator);
        outState.putString("result", result);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        display = savedInstanceState.getString("display");
        currentOperator = savedInstanceState.getString("currentOperator");
        _screen.setText(savedInstanceState.getString("_screen"));
        result = savedInstanceState.getString("result", result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);


        backspaceButton = (Button) findViewById(R.id.button41);
        backspaceButton.setEnabled(false);
        insertComma = (Button) findViewById(R.id.button);
        insertComma.setEnabled(false);
        changeChar = (Button)findViewById(R.id.button42);
        changeChar.setEnabled(false);
        _screen = (TextView)findViewById(R.id.textView);
        _screen.setText(display);
    }

    private void updateScreen() {
        if (display.length() == 0) {
            changeChar.setEnabled(false);
            backspaceButton.setEnabled(false);
        }
        if (currentOperator != "") {
            changeChar.setEnabled(false);
        }
        _screen.setText(display);
    }

    public void onClickNumber(View v){
        changeChar.setEnabled(true);
        backspaceButton.setEnabled(true);
        insertComma.setEnabled(true);

        if(result != ""){
            clear();
            updateScreen();
        }

        Button b = (Button) v;
        display += b.getText();
        updateScreen();
    }

    private boolean isOperator(char op){
        switch (op){
            case '+':
            case '-':
            case 'x':
            case '/': return true;
            default: return false;
        }
    }

    public void onClickOperator(View v){
        backspaceButton.setEnabled(true);
        insertComma.setEnabled(false);
        if(display == "") return;

        Button b = (Button)v;

        if(result != ""){
            String _display = result;
            clear();
            display = _display;
        }

        if(currentOperator != ""){
            Log.d("CalcX", "" + display.charAt(display.length()-1));

            if(isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }
            else{
                getResult();
                display = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }
        display += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }

    private void clear(){
        result = "";
        currentOperator = "";
        display = "";
        backspaceButton.setEnabled(false);
        insertComma.setEnabled(false);
        changeChar.setEnabled(false);
    }

    public void onClickClear(View v){
        backspaceButton.setEnabled(true);
        clear();
        updateScreen();
    }

    public void onClickBackspace(View v){
        if (display.length() != 0 && currentOperator != "") {
            currentOperator = "";
            display = display.substring(0,display.length()-1);
            //display = display.substring(0,display.indexOf(currentOperator));
        }
        else if (display.length() != 0) {
            display = display.substring(0,display.length()-1);
        }
        updateScreen();
    }

    public void onClickInsertComma(View v) {
        if (display.contains(".")) {
            insertComma.setEnabled(false);
        }
        else {
            display += ".";
            updateScreen();
            insertComma.setEnabled(false);
        }
    }

    private BigDecimal operate(String a, String b, String op){
        switch (op){
            case "+": return BigDecimal.valueOf(Double.valueOf(a) + Double.valueOf(b));
            case "-": return BigDecimal.valueOf(Double.valueOf(a) - Double.valueOf(b));
            case "x": return BigDecimal.valueOf(Double.valueOf(a) * Double.valueOf(b));
            case "/":
                try{
                    return BigDecimal.valueOf(Double.valueOf(a) / Double.valueOf(b));
                }
                catch (Exception e) {
                    Log.d("Calc", e.getMessage());
                }
            default: return BigDecimal.valueOf(0);
        }
    }


    private boolean getResult(){
        if(currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if(operation.length < 2) return false;
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }

    public void onClickEqual(View v){
        if(display == "") return;
        if(!getResult()) return;
        _screen.setText(display + "\n" + String.valueOf(result));
        changeChar.setEnabled(false);
        backspaceButton.setEnabled(false);
        insertComma.setEnabled(false);
    }

    public void onClickChangeChar(View v) {
        display = String.valueOf(BigDecimal.valueOf(Double.valueOf(display) * (-1)));
        _screen.setText(display);
    }
}
