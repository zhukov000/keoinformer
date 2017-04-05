package com.kolibru.schoolinfo.models;

import android.os.Parcel;

import org.parceler.Parcels;
import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;

class HomeworkParceler extends CollectionParcelConverter<Homework, RealmList<Homework>> {

    @Override
    public Homework itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Homework.class.getClassLoader()));
    }

    @Override
    public void itemToParcel(Homework input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public RealmList<Homework> createCollection() {
        return new RealmList<>();
    }
}
