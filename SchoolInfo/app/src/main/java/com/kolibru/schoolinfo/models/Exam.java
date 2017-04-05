package com.kolibru.schoolinfo.models;

import com.kolibru.schoolinfo.ConstClass;

import org.parceler.Parcel;

import io.realm.ExamRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Andrej_Maligin on 28.11.2015.
 */
@RealmClass      // required if using JDK 1.6 (unrelated to Parceler issue)
@Parcel(implementations = {ExamRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = {Exam.class})
public class Exam extends RealmObject {

    private String date;
    private String type;
    private int result;

    public String getDate() {
        return ConstClass.GetDateFromWeb(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}



