package com.kolibru.schoolinfo;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Malygin_Andrej on 02.10.16.
 * Chenge by Alexandr Zhukov on 04.04.17
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration configuration = new RealmConfiguration.Builder(getApplicationContext())
                .schemaVersion(6)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

}
