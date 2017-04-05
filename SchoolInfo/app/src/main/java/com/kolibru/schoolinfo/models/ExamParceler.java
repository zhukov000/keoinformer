package com.kolibru.schoolinfo.models;

import android.os.Parcel;

import com.kolibru.schoolinfo.models.Exam;

import org.parceler.Parcels;
import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;

class ExamParceler  extends CollectionParcelConverter<Exam, RealmList<Exam>> {

    @Override
    public Exam itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Exam.class.getClassLoader()));
    }

    @Override
    public void itemToParcel(Exam input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public RealmList<Exam> createCollection() {
        return new RealmList<>();
    }
}
