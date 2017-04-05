package com.kolibru.schoolinfo.models;

import android.os.Parcel;

import org.parceler.Parcels;
import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;

/**
 * Created by Andrej_Maligin on 05.11.2016.
 */

public class ScheduleParceler extends CollectionParcelConverter<Schedule, RealmList<Schedule>> {

    @Override
    public Schedule itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Schedule.class.getClassLoader()));
    }

    @Override
    public void itemToParcel(Schedule input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public RealmList<Schedule> createCollection() {
        return new RealmList<>();
    }
}
