package com.kolibru.schoolinfo.models;

import android.os.Parcel;

import org.parceler.Parcels;
import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;

class SubjectHomeworkParceler extends CollectionParcelConverter<SubjectHomework, RealmList<SubjectHomework>> {

    @Override
    public SubjectHomework itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(SubjectHomework.class.getClassLoader()));
    }

    @Override
    public void itemToParcel(SubjectHomework input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public RealmList<SubjectHomework> createCollection() {
        return new RealmList<>();
    }
}
