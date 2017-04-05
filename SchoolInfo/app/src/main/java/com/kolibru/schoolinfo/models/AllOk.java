package com.kolibru.schoolinfo.models;

import org.parceler.Parcel;

import io.realm.AllOkRealmProxy;
import io.realm.RealmObject;

/**
 *
 */
@Parcel(implementations = {AllOkRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = {AllOk.class})
public class AllOk extends RealmObject  {

    private String access_token;
    private String fullname;


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}



