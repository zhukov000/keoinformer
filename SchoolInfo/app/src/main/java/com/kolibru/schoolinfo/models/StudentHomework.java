package com.kolibru.schoolinfo.models;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.StudentAttendedRealmProxy;
import io.realm.StudentHomeworkRealmProxy;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Andrej_Maligin on 28.11.2015.
 */
@RealmClass      // required if using JDK 1.6 (unrelated to Parceler issue)
@Parcel(implementations = {StudentHomeworkRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = {StudentHomework.class})
public class StudentHomework extends RealmObject {

    @PrimaryKey
    private String student_id;
    private RealmList<SubjectHomework> subjects;


    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public RealmList<SubjectHomework> getSubjects() {
        return subjects;
    }

    @ParcelProperty("subjects")
    @ParcelPropertyConverter(SubjectHomeworkParceler.class)
    public void setSubjects(RealmList<SubjectHomework> subjects) {
        this.subjects = subjects;
    }
}



