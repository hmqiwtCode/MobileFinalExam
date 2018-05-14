package com.example.weatherapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocalStorageManager {

    private final String temp = "temp";
    private final String iconUrl = "icon";
    private static LocalStorageManager localStorageManager;
    private SharedPreferences sharedPreferences;

    private LocalStorageManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static LocalStorageManager getInstance(Context context) {
        if (localStorageManager == null) {
            localStorageManager = new LocalStorageManager(context);
        }
        return localStorageManager;
    }

    public String getTempInfo() {
        String tempInfo = sharedPreferences.getString(temp, null);
        if(tempInfo == null)
            return "not available";
        return tempInfo;
    }

    public String getIconUrl()  {
        String iconURL = sharedPreferences.getString(iconUrl, null);
        if(iconUrl == null)
            return "not available";
        return iconURL;
    }

    public void saveTemp(String currentTemp)   {
        sharedPreferences.edit().putString(temp, currentTemp).commit();
    }

    public void saveIcon(String url)    {
        sharedPreferences.edit().putString(iconUrl, url).commit();
    }

    public void deleteData()    {
        sharedPreferences.edit().putString(temp, null).putString(iconUrl, null).commit();
    }

}
