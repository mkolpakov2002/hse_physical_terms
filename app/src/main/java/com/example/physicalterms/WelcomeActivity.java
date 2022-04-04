package com.example.physicalterms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class WelcomeActivity extends AppCompatActivity {

    MaterialButton mainLanguageButton;
    MaterialButton learningLanguageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


    }
}