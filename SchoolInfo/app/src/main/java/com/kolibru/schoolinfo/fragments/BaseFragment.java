package com.kolibru.schoolinfo.fragments;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.kolibru.schoolinfo.utils.AppSetting;
import com.kolibru.schoolinfo.web.WebFunctionService;


/**
 * Created by artoymtkachenko on 05.07.15.
 */

public class BaseFragment extends Fragment {

    protected WebFunctionService webFunctionService=null;
    protected AppSetting appSetting=null;



    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        appSetting=AppSetting.getInstance(getActivity());
        webFunctionService=WebFunctionService.getInstance(getActivity());

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

}
