package com.kolibru.schoolinfo.models;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.SmsCodeRealmProxy;

/**
 *
 */
@Parcel(implementations = {SmsCodeRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = {SmsCode.class})
public class SmsCode extends RealmObject  {

    private String sms_code;
    private String fullname;



    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSms_code() {
        return sms_code;
    }

    public void setSms_code(String sms_code) {
        this.sms_code = sms_code;
    }
}



