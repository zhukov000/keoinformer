package com.kolibru.schoolinfo.models;

import com.kolibru.schoolinfo.ConstClass;

import org.parceler.Parcel;

import io.realm.AttendanceRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by Andrej_Maligin on 28.11.2015.
 */
@RealmClass      // required if using JDK 1.6 (unrelated to Parceler issue)
@Parcel(implementations = {AttendanceRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = {Attendance.class})
public class Attendance extends RealmObject {

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



