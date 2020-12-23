package com.example.lslibiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.loglibrary.LsLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    private void printLog(){
        LsLog.a("9900");
    }
}