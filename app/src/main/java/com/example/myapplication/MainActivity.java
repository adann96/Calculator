package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button simpleCalculator = findViewById(R.id.sim);
        final Button advancedCalculator = findViewById(R.id.adv);
        final Button about = findViewById(R.id.abo);
        final Button exit = findViewById(R.id.ex);

        View.OnClickListener calculatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        simpleCalculator.setOnClickListener(calculatorListener);
        advancedCalculator.setOnClickListener(calculatorListener);
        about.setOnClickListener(calculatorListener);
        exit.setOnClickListener(calculatorListener);

        simpleCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calc.class);
                startActivity(intent);
            }
        });

        advancedCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdvCalc.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutMeAndProject.class);
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
}