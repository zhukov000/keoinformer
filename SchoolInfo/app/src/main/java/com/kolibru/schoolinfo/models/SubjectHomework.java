package com.kolibru.schoolinfo.models;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.SubjectHomeworkRealmProxy;
import io.realm.annotations.RealmClass;

/**
 *
 */
@RealmClass      // required if using JDK 1.6 (unrelated to Parceler issue)
@Parcel(implementations = {SubjectHomeworkRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = {SubjectHomework.class})
public class SubjectHomework extends RealmObject {

    private String name;
    private RealmList<Homework> homework;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Homework> getHomework() {
        return homework;
    }

    @ParcelProperty("homework")
    @ParcelPropertyConverter(HomeworkParceler.class)
    public void setAttendance(RealmList<Homework> homework) {
        this.homework = homework;
    }
}



