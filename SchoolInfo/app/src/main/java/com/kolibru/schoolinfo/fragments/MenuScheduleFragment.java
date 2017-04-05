package com.kolibru.schoolinfo.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.activities.Homework;
import com.kolibru.schoolinfo.activities.Schedule;
import com.kolibru.schoolinfo.adapters.BaseAdapter;
import com.kolibru.schoolinfo.adapters.LessonAdapter;
import com.kolibru.schoolinfo.adapters.MenuHomeworkAdapter;
import com.kolibru.schoolinfo.adapters.MenuLessonAdapter;
import com.kolibru.schoolinfo.databinding.FragScheduleBinding;
import com.kolibru.schoolinfo.models.StudentHomework;
import com.kolibru.schoolinfo.models.StudentTimeTable;

import org.parceler.Parcels;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenuScheduleFragment extends BaseFragment {

    private FragScheduleBinding itemBinding;
    private Realm realm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm=Realm.getDefaultInstance();
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_schedule, container, false);
        itemBinding = DataBindingUtil.bind(view);
        itemBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemBinding.list.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadInfo();
            }
        });
        reloadInfo();
        return view;
    }


    private void reloadInfo(){
        if(webFunctionService.isInternetOn(getActivity())) {
            webFunctionService.getTmeTable(new Callback<RealmList<StudentTimeTable>>() {
                @Override
                public void onResponse(Call<RealmList<StudentTimeTable>> call, final Response<RealmList<StudentTimeTable>> response) {
                    try {
                        if (response.body().size() > 0) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.copyToRealmOrUpdate(response.body());
                                }
                            });
                            MenuLessonAdapter menuLessonAdapter = new MenuLessonAdapter(getActivity(), response.body());
                            menuLessonAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<StudentTimeTable>() {
                                @Override
                                public void onItemClick(StudentTimeTable item, View view) {

                                    Intent intent = new Intent(getActivity(), Schedule.class);
                                    Parcelable wrap = Parcels.wrap(StudentTimeTable.class, item);
                                    intent.putExtra(StudentTimeTable.class.getSimpleName(), wrap);
                                    startActivity(intent);
                                }
                            });
                            itemBinding.list.setAdapter(menuLessonAdapter);
                        } else
                            Snackbar.make(itemBinding.getRoot(), getText(R.string.message_nostudent), Snackbar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        if (webFunctionService.getTOKEN() != null)
                            Snackbar.make(itemBinding.getRoot(), getText(R.string.message_parse_error), Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RealmList<StudentTimeTable>> call, Throwable t) {
                    Snackbar.make(itemBinding.getRoot(), getText(R.string.message_auth), Snackbar.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(getActivity(), getString(R.string.message_no_internet), Toast.LENGTH_LONG).show();
            try {
                RealmResults<StudentTimeTable> all = realm
                        .where(StudentTimeTable.class)
                        .findAll();
                RealmList<StudentTimeTable> results = new RealmList<>();
                results.addAll(all);
                MenuLessonAdapter menuLessonAdapter = new MenuLessonAdapter(getActivity(), results);
                menuLessonAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<StudentTimeTable>() {
                    @Override
                    public void onItemClick(StudentTimeTable item, View view) {

                        Intent intent = new Intent(getActivity(), Schedule.class);
                        Parcelable wrap = Parcels.wrap(StudentTimeTable.class, item);
                        intent.putExtra(StudentTimeTable.class.getSimpleName(), wrap);
                        startActivity(intent);
                    }
                });
                itemBinding.list.setAdapter(menuLessonAdapter);
            }
            catch (Exception e){

            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();
    }
}
