package com.kolibru.schoolinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kolibru.schoolinfo.activities.BaseActivity;
import com.kolibru.schoolinfo.activities.MainActivity;
import com.kolibru.schoolinfo.models.Exam;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            if (appSetting.haveKey(appSetting.SETTING_TOKEN)) {
                webFunctionService.setTOKEN(appSetting.getString(appSetting.SETTING_TOKEN));
            }
        }
        catch (Exception e){

        }
        Timer t=new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getActivity(), MainActivity.class));
                finish();
            }
        },2000);

    }
}
