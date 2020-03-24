package com.wepon.gradleplugindemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.wepon.docannotation.GDoc;


@GDoc(name = "主页", author = "Wepon", time = "2020-03-24")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
