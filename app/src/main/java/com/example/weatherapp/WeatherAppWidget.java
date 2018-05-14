package com.example.weatherapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.weatherapp.api.weather.WeatherApiManager;
import com.example.weatherapp.data.local.LocalStorageManager;
import com.example.weatherapp.models.CurrentWeatherInfo;
import com.example.weatherapp.models.WeatherDescription;
import com.example.weatherapp.screens.MainActivity;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherAppWidget extends AppWidgetProvider {

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                               final int appWidgetId) {
        WeatherApiManager weatherApiManager = new WeatherApiManager();
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_app_widget);

        weatherApiManager.getWeatherAtCoordinates(33.8938, 35.5018).enqueue(new Callback<CurrentWeatherInfo>() {
                @Override
                public void onResponse(Call<CurrentWeatherInfo> call, Response<CurrentWeatherInfo> response) {
                    if (response.isSuccessful()) {
                        CurrentWeatherInfo currentWeatherInfo = response.body();
                        String ok = String.valueOf(currentWeatherInfo.getTempInfo().getTemp());

                        WeatherDescription currentWeatherDescription = currentWeatherInfo.getWeatherDescriptions().get(0);
                        String weatherDescription = currentWeatherDescription.getDescription();

                        String iconUrl = "http://openweathermap.org/img/w/" + currentWeatherDescription.getIcon() + ".png";
                      //  Picasso.with(context).load(iconUrl).into(views.setBitmap());

                        views.setTextViewText(R.id.current_temperature_widget, ok);
                        appWidgetManager.updateAppWidget(appWidgetId, views);

                    }
                }

                @Override
                public void onFailure(Call<CurrentWeatherInfo> call, Throwable t) {
                }
            });
        appWidgetManager.updateAppWidget(appWidgetId, views);
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
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

