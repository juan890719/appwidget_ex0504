package com.tom.widgetmessageboard;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {
    // pass image id 陣列
    static int[] passIds = new int[]{R.drawable.qr_code, R.drawable.quack, R.drawable.main};
    // pass name 陣列
    static String[] passName = new String[]{"疫把照 - 疫苗pass", "疫把照 - 檢驗pass", "疫把照 - 康復pass"};
    // pass 索引，位置（0: vaccine pass, 1: test pass, 2: recovery pass）
    static int passIndex = 0;
    private static final String LeftOnClick = "leftOnClickTag";
    private static final String RightOnClick = "rightOnClickTag";
    private static final String ImgOnClick = "imgOnClickTag";

    protected static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, AppWidget.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (LeftOnClick.equals(intent.getAction())) {
            if (passIndex > 0) {
                passIndex--;
            } else {
                passIndex = passIds.length - 1;
            }
        } else if (RightOnClick.equals(intent.getAction())) {
            // your onClick action is here
            if (passIndex < passIds.length - 1) {
                passIndex++;
            } else {
                passIndex = 0;
            }
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), AppWidget.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

        onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        // Get all ids
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        ComponentName thisWidget = new ComponentName(context, AppWidget.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        Intent intent = new Intent(context, MessageHistoryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.img, pendingIntent);
        views.setImageViewResource(R.id.img, passIds[passIndex]);
        views.setTextViewText(R.id.appwidget_text, passName[passIndex]);

        for (int widgetId : allWidgetIds) {
            views.setOnClickPendingIntent(R.id.tv_left, getPendingSelfIntent(context, LeftOnClick));
            views.setOnClickPendingIntent(R.id.tv_right, getPendingSelfIntent(context, RightOnClick));

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(widgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public void onUpdateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), AppWidget.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
        passIndex = 0;
        onUpdate(context, appWidgetManager, appWidgetIds);
    }
}