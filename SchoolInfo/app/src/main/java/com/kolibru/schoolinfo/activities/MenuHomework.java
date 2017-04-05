package com.kolibru.schoolinfo.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.adapters.BaseAdapter;
import com.kolibru.schoolinfo.adapters.MenuExamAdapter;
import com.kolibru.schoolinfo.adapters.MenuHomeworkAdapter;
import com.kolibru.schoolinfo.databinding.ActivityMenuExamBinding;
import com.kolibru.schoolinfo.models.*;
import com.kolibru.schoolinfo.web.WebFunctionService;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.parceler.Parcels;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Класс с информацией о домашнем задании
 */

public class MenuHomework extends BaseActivity {

    ActivityMenuExamBinding fragVisitBinding;
    private int mYear, mMonth, mDay;
    private Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_exam);
        realm=Realm.getDefaultInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragVisitBinding= DataBindingUtil.setContentView(this, R.layout.activity_menu_exam);
        fragVisitBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        // дата, с которой показывать выполнение ДЗ
        String date_from= DateTime.now().minusMonths(1).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
        fragVisitBinding.dateFrom.setText(date_from);
        // дата, по которую показывать выполнение ДЗ
        String date_to= DateTime.now().toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
        fragVisitBinding.dateTo.setText(date_to);

        fragVisitBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragVisitBinding.btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(fragVisitBinding.dateFrom);
            }
        });

        fragVisitBinding.btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(fragVisitBinding.dateTo);
            }
        });
        loadList();
    }


    public void setDate(final TextView filed) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        filed.setText(String.valueOf(year)+'-'+String.valueOf(monthOfYear+1)+'-'+String.valueOf(dayOfMonth));
                        loadList();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    // загрузка результатов ДР
    private void loadList()
    {
        String date_from= DateTime.now().toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
        String date_to= DateTime.now().toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
        if(fragVisitBinding.dateFrom.getText().length()>0)
            date_from=fragVisitBinding.dateFrom.getText().toString();
        if(fragVisitBinding.dateTo.getText().length()>0)
            date_to=fragVisitBinding.dateTo.getText().toString();

        if(WebFunctionService.isInternetOn(getActivity())) {
            webFunctionService.getHomework(date_from, date_to, new Callback<RealmList<StudentHomework>>() {
                @Override
                public void onResponse(Call<RealmList<StudentHomework>> call, final Response<RealmList<StudentHomework>> response) {
                    try {
                        if (response.body() != null) {

                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.copyToRealmOrUpdate(response.body());
                                }
                            });
                            MenuHomeworkAdapter m = new MenuHomeworkAdapter(getActivity(), response.body());
                            m.setOnItemClickListener(new BaseAdapter.OnItemClickListener<StudentHomework>() {
                                @Override
                                public void onItemClick(StudentHomework item, View view) {
                                    Intent intent = new Intent(getActivity(), Homework.class);
                                    Parcelable wrap = Parcels.wrap(StudentHomework.class, item);
                                    intent.putExtra(StudentHomework.class.getSimpleName(), wrap);
                                    startActivity(intent);
                                }
                            });
                            fragVisitBinding.list.setAdapter(m);
                        } else
                            Snackbar.make(fragVisitBinding.getRoot(), getResources().getString(R.string.message_nodata), Snackbar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        if (webFunctionService.getTOKEN() != null)
                            Snackbar.make(fragVisitBinding.getRoot(), getText(R.string.message_parse_error), Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RealmList<StudentHomework>> call, Throwable t) {
                    Snackbar.make(fragVisitBinding.getRoot(), getResources().getString(R.string.message_auth), Snackbar.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(getActivity(), getString(R.string.message_no_internet), Toast.LENGTH_LONG).show();
            try {
                RealmResults<StudentHomework> all = realm
                        .where(StudentHomework.class)
                        .findAll();
                RealmList<StudentHomework> results = new RealmList<>();
                results.addAll(all);
                MenuHomeworkAdapter m = new MenuHomeworkAdapter(getActivity(), results);
                m.setOnItemClickListener(new BaseAdapter.OnItemClickListener<StudentHomework>() {
                    @Override
                    public void onItemClick(StudentHomework item, View view) {
                        Intent intent = new Intent(getActivity(), Homework.class);
                        Parcelable wrap = Parcels.wrap(StudentHomework.class, item);
                        intent.putExtra(StudentHomework.class.getSimpleName(), wrap);
                        startActivity(intent);
                    }
                });
                fragVisitBinding.list.setAdapter(m);
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
