package com.example.myapplication;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class AdvCalc extends AppCompatActivity {

    private TextView _screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";

    private Button backspaceButton;
    private Button insertComma;

    private Button sinus;
    private Button cosinus;
    private Button tangens;
    private Button lognat;
    private Button logarytm;
    private Button potega;
    private Button squareRoot;
    private Button potegaDoDwoch;
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
        setContentView(R.layout.activity_adv_calc);
        backspaceButton = (Button) findViewById(R.id.button41);
        backspaceButton.setEnabled(false);

        changeChar = (Button)findViewById(R.id.button42);
        changeChar.setEnabled(false);

        sinus = (Button) findViewById(R.id.button8);
        cosinus = (Button)findViewById(R.id.button7);
        tangens = (Button) findViewById(R.id.button6);
        lognat = (Button)findViewById(R.id.button5);
        logarytm = (Button)findViewById(R.id.button9);
        potega = (Button)findViewById(R.id.button10);
        squareRoot = (Button)findViewById(R.id.button12);
        potegaDoDwoch = (Button)findViewById(R.id.button11);

        sinus.setEnabled(false);
        cosinus.setEnabled(false);
        tangens.setEnabled(false);
        lognat.setEnabled(false);
        logarytm.setEnabled(false);
        potega.setEnabled(false);
        squareRoot.setEnabled(false);
        potegaDoDwoch.setEnabled(false);

        insertComma = (Button) findViewById(R.id.button);
        insertComma.setEnabled(false);
        _screen = (TextView)findViewById(R.id.textView);
        _screen.setText(display);
    }

    private void updateScreen()
    {
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
        backspaceButton.setEnabled(true);
        insertComma.setEnabled(true);
        changeChar.setEnabled(true);

        sinus.setEnabled(true);
        cosinus.setEnabled(true);
        tangens.setEnabled(true);
        lognat.setEnabled(true);
        logarytm.setEnabled(true);
        potega.setEnabled(true);
        squareRoot.setEnabled(true);
        potegaDoDwoch.setEnabled(true);

        if(result != ""){
            clear();
            updateScreen();
        }

        Button b = (Button) v;
        display += b.getText();
        updateScreen();
    }

    private boolean isOperator(String op){
        switch (op){
            case "+":
            case "-":
            case "x":
            case "/": return true;
            case "^":
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
            Log.d("CalcX", ""+display.substring(display.length()-1));

            if(isOperator(display.substring(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }
            else {
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

        sinus.setEnabled(false);
        cosinus.setEnabled(false);
        tangens.setEnabled(false);
        lognat.setEnabled(false);
        logarytm.setEnabled(false);
        potega.setEnabled(false);
        squareRoot.setEnabled(false);
        potegaDoDwoch.setEnabled(false);
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
        }
        else if (display.length() != 0){
            display = display.substring(0,display.length()-1);
        }
        else if (display.length() == 0){
            backspaceButton.setEnabled(false);
            insertComma.setEnabled(false);
            sinus.setEnabled(false);
            cosinus.setEnabled(false);
            tangens.setEnabled(false);
            lognat.setEnabled(false);
            logarytm.setEnabled(false);
            potega.setEnabled(false);
            squareRoot.setEnabled(false);
            potegaDoDwoch.setEnabled(false);
        }
        else {
        }
        updateScreen();
    }

    public void onClickInsertComma(View v) {
        display += ".";
        updateScreen();
        insertComma.setEnabled(false);
    }

    private BigDecimal operate(String a, String b, String op){
        switch (op){
            case "+": return BigDecimal.valueOf(Double.valueOf(a) + Double.valueOf(b));
            case "-": return BigDecimal.valueOf(Double.valueOf(a) - Double.valueOf(b));
            case "x": return BigDecimal.valueOf(Double.valueOf(a) * Double.valueOf(b));
            case "/":
                try { return BigDecimal.valueOf(Double.valueOf(a) / Double.valueOf(b)); }
                catch (Exception e) { Log.d("Calc", e.getMessage()); }
            case "^": try {
                if (b.equals("0") || b == "0") {
                    return BigDecimal.valueOf(1);
                }
                else {
                    return BigDecimal.valueOf(Math.pow(Double.valueOf(a),Double.valueOf(b)));
                }
            }
            catch (Exception ex) {
                Log.d("Calc",ex.getMessage());
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

    public void onClickSin(View v) {
        result = String.valueOf(BigDecimal.valueOf(Math.sin(Double.valueOf(display))));
        _screen.setText("Sin(" + display + ")" + "\n" + String.valueOf(result));
        backspaceButton.setEnabled(false);
        insertComma.setEnabled(false);
    }

    public void onClickChangeChar(View v){
        display = String.valueOf(BigDecimal.valueOf(Double.valueOf(display)*(-1)));
        _screen.setText(display);
    }

    public void onClickCos(View v) {
        result = String.valueOf(BigDecimal.valueOf(Math.cos(Double.valueOf(display))));
        _screen.setText("Cos(" + display + ")" + "\n" + String.valueOf(result));
        backspaceButton.setEnabled(false);
        insertComma.setEnabled(false);
    }

    public void onClickTan(View v) {
        result = String.valueOf(BigDecimal.valueOf(Math.tan(Double.valueOf(display))));
        _screen.setText("Tan(" + display + ")" + "\n" + String.valueOf(result));
        backspaceButton.setEnabled(false);
        insertComma.setEnabled(false);
    }

    public void onClickLn(View v) {
        result = String.valueOf(BigDecimal.valueOf(Math.log(Double.valueOf(display))));
        _screen.setText("Log"+"ε".toLowerCase()+"(" + display + ")" + "\n" + String.valueOf(result));
        backspaceButton.setEnabled(false);
        insertComma.setEnabled(false);
    }

    public void onClickRoot(View v) {
        result = String.valueOf(BigDecimal.valueOf(Math.sqrt(Double.valueOf(display))));
        _screen.setText("√" + display + "\n" + String.valueOf(result));
        backspaceButton.setEnabled(false);
        insertComma.setEnabled(false);
    }

    public void onClickPowerOfTwo(View v) {
        result = String.valueOf(BigDecimal.valueOf(Math.pow(Double.valueOf(display),2)));
        _screen.setText(display + "\n" + String.valueOf(result));
        backspaceButton.setEnabled(false);
        insertComma.setEnabled(false);
    }

    public void onClickLogTen(View v) {
        result = String.valueOf(BigDecimal.valueOf(Math.log10(Double.valueOf(display))));
        _screen.setText(display + "\n" + String.valueOf(result));
        backspaceButton.setEnabled(false);
        insertComma.setEnabled(false);
    }
}