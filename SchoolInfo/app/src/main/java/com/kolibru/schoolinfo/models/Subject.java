package com.kolibru.schoolinfo.models;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.SubjectRealmProxy;
import io.realm.annotations.RealmClass;

/**
 * Created by Andrej_Maligin on 28.11.2015.
 */
@RealmClass      // required if using JDK 1.6 (unrelated to Parceler issue)
@Parcel(implementations = {SubjectRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = {Subject.class})
public class Subject extends RealmObject {

    private String name;
    private RealmList<Exam> exams;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Exam> getExams() {
        return exams;
    }

    @ParcelProperty("exams")
    @ParcelPropertyConverter(ExamParceler.class)
    public void setExams(RealmList<Exam> exams) {
        this.exams = exams;
    }
}



