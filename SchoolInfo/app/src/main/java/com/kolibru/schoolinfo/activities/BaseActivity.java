package com.kolibru.schoolinfo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.utils.AppSetting;
import com.kolibru.schoolinfo.web.WebFunctionService;

public class BaseActivity extends AppCompatActivity {

    protected WebFunctionService webFunctionService;
    protected AppSetting appSetting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webFunctionService=WebFunctionService.getInstance(this);
        appSetting=AppSetting.getInstance(this);
    }

    protected String getLogName(){
        return getLogName("");
    }

    protected String getLogName(String append){
        try {
            return this.getClass().getName()+"_"+append;
        }
        catch (Exception e){
            return "BaseActivity";
        }
    }

    protected BaseActivity getActivity(){
        return this;
    }

    /**
     * Перезагружаем активити
     */
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
