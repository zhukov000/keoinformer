package com.kolibru.schoolinfo.models;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.SubjectAttendedRealmProxy;
import io.realm.SubjectRealmProxy;
import io.realm.annotations.RealmClass;

/**
 * Created by Andrej_Maligin on 28.11.2015.
 */
@RealmClass      // required if using JDK 1.6 (unrelated to Parceler issue)
@Parcel(implementations = {SubjectAttendedRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = {SubjectAttended.class})
public class SubjectAttended extends RealmObject {

    private String name;
    private RealmList<Attendance> attendance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Attendance> getAttendance() {
        return attendance;
    }

    @ParcelProperty("attendance")
    @ParcelPropertyConverter(AttendenceParceler.class)
    public void setAttendance(RealmList<Attendance> attendance) {
        this.attendance = attendance;
    }
}



