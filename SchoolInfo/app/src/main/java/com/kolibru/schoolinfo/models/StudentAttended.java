package com.kolibru.schoolinfo.models;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.StudentAttendedRealmProxy;
import io.realm.StudentRealmProxy;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Andrej_Maligin on 28.11.2015.
 */
@RealmClass      // required if using JDK 1.6 (unrelated to Parceler issue)
@Parcel(implementations = {StudentAttendedRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = {StudentAttended.class})
public class StudentAttended extends RealmObject {

    @PrimaryKey
    private String student_id;
    private RealmList<SubjectAttended> subjects;


    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public RealmList<SubjectAttended> getSubjects() {
        return subjects;
    }

    @ParcelProperty("subjects")
    @ParcelPropertyConverter(SubjectAttendenceParceler.class)
    public void setSubjects(RealmList<SubjectAttended> subjects) {
        this.subjects = subjects;
    }
}



