package com.kolibru.schoolinfo.models;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.StudentRealmProxy;
import io.realm.SubjectRealmProxy;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Andrej_Maligin on 28.11.2015.
 */
@RealmClass      // required if using JDK 1.6 (unrelated to Parceler issue)
@Parcel(implementations = {StudentRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = {Student.class})
public class Student extends RealmObject {

    @PrimaryKey
    private String student_id;
    private RealmList<Subject> subjects;

    @ParcelProperty("subjects")
    @ParcelPropertyConverter(SubjectParceler.class)
    public void setExams(RealmList<Subject> subjects) {
        this.subjects = subjects;
    }
}



