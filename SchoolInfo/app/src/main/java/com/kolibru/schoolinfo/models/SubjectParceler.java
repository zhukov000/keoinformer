package com.kolibru.schoolinfo.models;

import android.os.Parcel;

import org.parceler.Parcels;
import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;

/**
 * Created by Andrej_Maligin on 05.11.2016.
 */

public class SubjectParceler  extends CollectionParcelConverter<Subject, RealmList<Subject>> {

    @Override
    public Subject itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Subject.class.getClassLoader()));
    }

    @Override
    public void itemToParcel(Subject input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public RealmList<Subject> createCollection() {
        return new RealmList<>();
    }
}
