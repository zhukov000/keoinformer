package com.kolibru.schoolinfo.models;

import com.kolibru.schoolinfo.ConstClass;

import org.parceler.Parcel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.ExamRealmProxy;
import io.realm.HomeworkRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 *
 */
@RealmClass      // required if using JDK 1.6 (unrelated to Parceler issue)
@Parcel(implementations = {HomeworkRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = {Homework.class})
public class Homework extends RealmObject {

    private String date;
    private String state;

    public String getDate() {
        return ConstClass.GetDateFromWeb(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}



