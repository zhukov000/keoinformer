package com.kolibru.schoolinfo.models;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.StudentTimeTableRealmProxy;
import io.realm.SubjectRealmProxy;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Andrej_Maligin on 28.11.2015.
 */
@RealmClass      // required if using JDK 1.6 (unrelated to Parceler issue)
@Parcel(implementations = {StudentTimeTableRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = {StudentTimeTable.class})
public class StudentTimeTable extends RealmObject {

    @PrimaryKey
    private String student_id;
    private RealmList<Schedule> timetable;



    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public RealmList<Schedule> getTimetable() {
        return timetable;
    }

    @ParcelProperty("timetable")
    @ParcelPropertyConverter(ScheduleParceler.class)
    public void setTimetable(RealmList<Schedule> timetable) {
        this.timetable = timetable;
    }
}



