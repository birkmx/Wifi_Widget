package com.birkmxApps.maksim.wifi_widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class WifiOnOff extends AppWidgetProvider {
    private static final String ACTION_CLICK = "WidgetWifiActionClick";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, WifiOnOff.class);
        intent.setAction(ACTION_CLICK);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.wifi_on_off);

        int image = 0;
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()) image = setImage(0); else image = setImage(1);

        remoteViews.setImageViewResource(R.id.imageView, image);
        remoteViews.setOnClickPendingIntent(R.id.imageView, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_CLICK.equals(intent.getAction()))
        {


            WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

            int image = 0;

            if (wifi.isWifiEnabled()) wifi.setWifiEnabled(false); else wifi.setWifiEnabled(true);
            if (wifi.isWifiEnabled()) image = setImage(1); else image = setImage(0);

            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.wifi_on_off);
            views.setImageViewResource(R.id.imageView, image);

            ComponentName appWidget = new ComponentName(context, WifiOnOff.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidget, views);


        }

    }
    public static int setImage(int num){
        int image = 0;
        switch (num){
            case 0:
                image = R.drawable.heart;
                break;
            case 1:
                image = R.drawable.heart2;
                break;
        }
        return image;
    }
}

