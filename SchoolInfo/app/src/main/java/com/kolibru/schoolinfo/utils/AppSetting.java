package com.kolibru.schoolinfo.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.ArraySet;

import java.util.List;
import java.util.Set;

/**
 * Created by Andrej_Maligin on 23.01.2016.
 */
public class AppSetting {

    public static final String APP_PREFERENCES = "schoool2016";
    public static final String SETTING_TOKEN = "SETTING_TOKEN";
    public static final String SETTING_FIO = "SETTING_FIO";
    public static final String SETTING_PHONE = "SETTING_PHONE";
    public static final String SETTING_SMS = "SETTING_SMS";


    private static AppSetting instance;
    private static SharedPreferences mSettings;
    private static Context context;

    private AppSetting (){
    }

    public static AppSetting getInstance(Context context){
        if (null == instance){
            instance = new AppSetting();
            mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            AppSetting.context = context;
        }
        return instance;
    }

    public boolean haveKey(String key){
        try {
            return mSettings.contains(key);
        }
        catch (Exception e){
            return false;
        }
    }

    public String getString(String key){
        try {
            return mSettings.getString(key, null);
        }
        catch (Exception e){
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public Set<String> getStringSet(Context context, String key){
        try {
            return mSettings.getStringSet(key, null);
        }
        catch (Exception e){
            return new ArraySet<String>();
        }
    }

    public String getStringNotNull(String key){
        try {
            return mSettings.getString(key, "");
        }
        catch (Exception e){
            return "";
        }
    }

    public int getInt(String key){
        try {
            return mSettings.getInt(key, 0);
        }
        catch (Exception e){
            return 0;
        }
    }

    public long getLong(String key){
        try {
            return mSettings.getLong(key, 0);
        }
        catch (Exception e){
            return 0;
        }
    }

    public Boolean getBoolean(String key){
        try {
            return mSettings.getBoolean(key, false);
        }
        catch (Exception e){
            return false;
        }
    }
    public boolean saveSharedPreferences(String prefName, String value) {
        try {
            SharedPreferences.Editor edit = mSettings.edit();
            edit.putString(prefName, value);
            edit.apply();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean saveSharedPreferences(String prefName, long value) {
        try {
            SharedPreferences.Editor edit = mSettings.edit();
            edit.putLong(prefName, value);
            edit.apply();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean saveSharedPreferences(String prefName, int value) {
        try {
            SharedPreferences.Editor edit = mSettings.edit();
            edit.putInt(prefName, value);
            edit.apply();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean saveSharedPreferences(String prefName, Set<String> value) {
        try {
            SharedPreferences.Editor edit = mSettings.edit();
            edit.putStringSet(prefName, value);
            edit.apply();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean saveSharedPreferences(String prefName, boolean value) {
        try {
            SharedPreferences.Editor edit = mSettings.edit();
            edit.putBoolean(prefName, value);
            edit.apply();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean removeKey(String key){
        try{
            SharedPreferences.Editor edit = mSettings.edit();
            edit.remove(key);
            edit.apply();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static boolean removeKeys(List<String> keys){
        try{
            SharedPreferences.Editor edit = mSettings.edit();
            for(String key: keys ){
                edit.remove(key);
            }
            edit.apply();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static boolean removeAllKeys(){
        try{
            SharedPreferences.Editor edit = mSettings.edit();
            edit.clear();
            edit.apply();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


}
