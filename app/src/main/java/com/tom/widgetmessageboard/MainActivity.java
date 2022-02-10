package com.tom.widgetmessageboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText text;
    private Button enter;
    private SharedPreferencesUtils sharedPreferencesUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text.getText().equals("") || text.getText() == null) {
                    Toast.makeText(MainActivity.this, "Please enter some words!", Toast.LENGTH_SHORT).show();
                } else {
                    String message = text.getText().toString();
                    sharedPreferencesUtils.setMessageHistory(message);
                    Intent intent = new Intent(MainActivity.this, AppWidget.class);
                    intent.putExtra("message", message);
                    sendBroadcast(intent);
                }
            }
        });
    }

    public void initialize() {
        text = findViewById(R.id.et_text);
        enter = findViewById(R.id.btn_enter);
        sharedPreferencesUtils = new SharedPreferencesUtils(this);
    }
}