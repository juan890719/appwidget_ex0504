package com.tom.widgetmessageboard;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MessageHistoryActivity extends AppCompatActivity {

    private TextView history;
    private SharedPreferencesUtils sharedPreferencesUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_history);

        initialize();
        String message = sharedPreferencesUtils.getMessageHistory();
        String[] arrayMessage = message.split(",");
        for (int i = 0; i < arrayMessage.length; i++) {
            if (i == 0) {
                message = arrayMessage[0];
            } else {
                message += "\n";
                message += arrayMessage[i];
            }
        }
        history.setText(message);
    }

    public void initialize() {
        history = findViewById(R.id.textView2);
        sharedPreferencesUtils = new SharedPreferencesUtils(this);
    }
}