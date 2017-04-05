package com.kolibru.schoolinfo.models;

import android.os.Parcel;

import org.parceler.Parcels;
import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;

class AttendenceParceler extends CollectionParcelConverter<Attendance, RealmList<Attendance>> {

    @Override
    public Attendance itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Attendance.class.getClassLoader()));
    }

    @Override
    public void itemToParcel(Attendance input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public RealmList<Attendance> createCollection() {
        return new RealmList<>();
    }
}
