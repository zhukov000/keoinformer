package com.kolibru.schoolinfo.models;

import android.os.Parcel;

import org.parceler.Parcels;
import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;

class SubjectExamParceler extends CollectionParcelConverter<SubjectExam, RealmList<SubjectExam>> {

    @Override
    public SubjectExam itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(SubjectExam.class.getClassLoader()));
    }

    @Override
    public void itemToParcel(SubjectExam input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public RealmList<SubjectExam> createCollection() {
        return new RealmList<>();
    }
}
