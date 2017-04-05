package com.kolibru.schoolinfo.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.activities.MenuExam;
import com.kolibru.schoolinfo.activities.MenuHomework;
import com.kolibru.schoolinfo.databinding.FragMenuProgressBinding;
import com.kolibru.schoolinfo.databinding.FragProgressBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuProgressFragment extends BaseFragment {

    private FragMenuProgressBinding itemBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_menu_progress, container, false);
        itemBinding = DataBindingUtil.bind(view);
        itemBinding.homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MenuHomework.class));
            }
        });

        itemBinding.result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MenuExam.class));
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {menu.clear();
        inflater.inflate(R.menu.main_empty, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
