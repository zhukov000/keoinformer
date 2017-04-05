package com.kolibru.schoolinfo.models;

import android.os.Parcel;

import org.parceler.Parcels;
import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;

class SubjectAttendenceParceler extends CollectionParcelConverter<SubjectAttended, RealmList<SubjectAttended>> {

    @Override
    public SubjectAttended itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(SubjectAttended.class.getClassLoader()));
    }

    @Override
    public void itemToParcel(SubjectAttended input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public RealmList<SubjectAttended> createCollection() {
        return new RealmList<>();
    }
}
