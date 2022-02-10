package com.tom.widgetmessageboard;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    Context context;

    public SharedPreferencesUtils(Context context) {
        this.context = context;
    }

    public void setMessageHistory(String message) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String oldMessage = sharedPreferences.getString("message", "");
        String newMessage = oldMessage + "," + message;
        editor.putString("message", newMessage).commit();
    }

    public String getMessageHistory() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        String message = sharedPreferences.getString("message", "");
        return message;
    }
}
