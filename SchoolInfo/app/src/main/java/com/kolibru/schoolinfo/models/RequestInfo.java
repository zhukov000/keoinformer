package com.kolibru.schoolinfo.models;

import org.parceler.Parcel;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RequestInfoRealmProxy;

/**
 *
 */
@Parcel(implementations = {RequestInfoRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = {Subject.class})
public class RequestInfo extends RealmObject  {

    private String name;
    private String message;
    private String code;
    private int status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}



