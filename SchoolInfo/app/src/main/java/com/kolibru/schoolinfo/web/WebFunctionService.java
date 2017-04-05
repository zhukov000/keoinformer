package com.kolibru.schoolinfo.web;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kolibru.schoolinfo.ToStringConverterFactory;
import com.kolibru.schoolinfo.models.Person;
import com.kolibru.schoolinfo.models.StudentAttended;
import com.kolibru.schoolinfo.models.StudentExam;
import com.kolibru.schoolinfo.models.StudentHomework;
import com.kolibru.schoolinfo.models.StudentTimeTable;
import com.squareup.okhttp.ResponseBody;

import io.realm.RealmList;
import io.realm.RealmObject;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebFunctionService  {

    private static String BASE_URL = "http://gradebook.keo-rostov.ru/api/";
    private static String TOKEN = null;
    private static WebFunctionService instance;
    private Context context;

    public static WebFunctionService getInstance(Context context) {
        if (null == instance){
            instance = new WebFunctionService();
            instance.context = context;
        }
        return instance;
    }

    Gson gson = new GsonBuilder()
            .setExclusionStrategies(getExclusionStrategy())
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(new ToStringConverterFactory())
            .build();

    Retrofit retrofit_realm = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    WebService webService = retrofit.create(WebService.class);
    WebService webService_realm = retrofit_realm.create(WebService.class);




    public static String getTOKEN() {
        if(TOKEN!=null)
            return TOKEN;
        else
            return null;
    }

    public static void setTOKEN(String TOKEN) {
        WebFunctionService.TOKEN=TOKEN;
    }

    public void registration(String phone,String sms_code, Callback<String> cb) {
         try {
             webService.registration(phone,sms_code).enqueue(cb);
         } catch (Exception e) {
             Log.e("registration","ERROR:"+(e.getMessage()==null?"[NULL]":e.getMessage()));
         }
    }

    public void logout(Callback<String> cb) {
        try {
            webService.logout(getTOKEN()).enqueue(cb);
        } catch (Exception e) {
            Log.e("logout","ERROR:"+(e.getMessage()==null?"[NULL]":e.getMessage()));
        }
    }

    public void getChildren(Callback<RealmList<Person>> cb) {
        try {
            webService_realm.getChildren(getTOKEN()).enqueue(cb);
        } catch (Exception e) {
            Log.e("getChildren","ERROR:"+(e.getMessage()==null?"[NULL]":e.getMessage()));
        }
    }

    public void getTmeTable(Callback<RealmList<StudentTimeTable>> cb) {
        try {
            webService_realm.getTmeTable(getTOKEN()).enqueue(cb);
        } catch (Exception e) {
            Log.e("getTmeTable","ERROR:"+(e.getMessage()==null?"[NULL]":e.getMessage()));
        }
    }

    public void getResult(String date_from,String date_to, Callback<RealmList<StudentExam>> cb) {
        try {
            webService_realm.getResult(getTOKEN(),date_from,date_to).enqueue(cb);
        } catch (Exception e) {
            Log.e("getResult","ERROR:"+(e.getMessage()==null?"[NULL]":e.getMessage()));
        }
    }

    public void getAttendance(String date_from,String date_to, Callback<RealmList<StudentAttended>> cb) {
        try {
            webService_realm.getAttendance(getTOKEN(),date_from,date_to).enqueue(cb);
        } catch (Exception e) {
            Log.e("getAttendance","ERROR:"+(e.getMessage()==null?"[NULL]":e.getMessage()));
        }
    }

    public void getHomework(String date_from,String date_to, Callback<RealmList<StudentHomework>> cb) {
        try {
            webService_realm.getHomework(getTOKEN(),date_from,date_to).enqueue(cb);
        } catch (Exception e) {
            Log.e("getHomework","ERROR:"+(e.getMessage()==null?"[NULL]":e.getMessage()));
        }
    }

    private ExclusionStrategy getExclusionStrategy() {
        return new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };
    }

    /**
     * Активен интернет?
     * @return
     */
    public static boolean isInternetOn(Context mContext) {
        if(mContext!=null) {
            ConnectivityManager connec = (ConnectivityManager) mContext.getApplicationContext()
                    .getSystemService(mContext.getApplicationContext().CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connec.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }
        return true;
    }


}