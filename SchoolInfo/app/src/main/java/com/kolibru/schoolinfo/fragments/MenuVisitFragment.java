package com.kolibru.schoolinfo.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.activities.Schedule;
import com.kolibru.schoolinfo.activities.Visit;
import com.kolibru.schoolinfo.adapters.BaseAdapter;
import com.kolibru.schoolinfo.adapters.MenuLessonAdapter;
import com.kolibru.schoolinfo.adapters.MenuVisitAdapter;
import com.kolibru.schoolinfo.adapters.VisitAdapter;
import com.kolibru.schoolinfo.databinding.FragMenuVisitBinding;
import com.kolibru.schoolinfo.databinding.FragScheduleBinding;
import com.kolibru.schoolinfo.databinding.FragVisitBinding;
import com.kolibru.schoolinfo.models.StudentAttended;
import com.kolibru.schoolinfo.models.StudentTimeTable;

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


public class MenuVisitFragment extends BaseFragment {

    private FragMenuVisitBinding itemBinding;
    private Realm realm;
    String visit=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm=Realm.getDefaultInstance();
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_menu_visit, container, false);
        itemBinding = DataBindingUtil.bind(view);
        itemBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        String date_from= DateTime.now().minusMonths(1).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
        itemBinding.dateFrom.setText(date_from);
        String date_to= DateTime.now().toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
        itemBinding.dateTo.setText(date_to);
        itemBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemBinding.btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(itemBinding.dateFrom);
            }
        });

        itemBinding.btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(itemBinding.dateTo);
            }
        });
        itemBinding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    visit="absent";
                }
                else
                    visit=null;
                loadList();
            }
        });
        loadList();
        return view;
    }


    private int mYear, mMonth, mDay;

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

    private void loadList()
    {
        String date_from= DateTime.now().toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
        String date_to= DateTime.now().toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
        if(itemBinding.dateFrom.getText().length()>0)
            date_from=itemBinding.dateFrom.getText().toString();
        if(itemBinding.dateTo.getText().length()>0)
            date_to=itemBinding.dateTo.getText().toString();

        if(webFunctionService.isInternetOn(getActivity())) {
        webFunctionService.getAttendance(date_from,date_to,new Callback<RealmList<StudentAttended>>() {
            @Override
            public void onResponse(Call<RealmList<StudentAttended>> call, final Response<RealmList<StudentAttended>> response) {
                try {
                    if(response.body()!=null) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(response.body());
                            }
                        });
                        MenuVisitAdapter menuVisitAdapter = new MenuVisitAdapter(getActivity(), response.body());
                        menuVisitAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<StudentAttended>() {
                            @Override
                            public void onItemClick(StudentAttended item, View view) {

                                Intent intent = new Intent(getActivity(), Visit.class);
                                Parcelable wrap = Parcels.wrap(StudentAttended.class, item);
                                intent.putExtra(StudentAttended.class.getSimpleName(), wrap);
                                intent.putExtra("visit", visit);
                                startActivity(intent);
                            }
                        });
                        itemBinding.list.setAdapter(menuVisitAdapter);
                    }
                    else
                        if (webFunctionService.getTOKEN() != null)
                        Snackbar.make(itemBinding.getRoot(), getText(R.string.message_nostudent), Snackbar.LENGTH_LONG).show();

                } catch (Exception e) {
                    Snackbar.make(itemBinding.getRoot(), getText(R.string.message_parse_error), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RealmList<StudentAttended>> call, Throwable t) {
                Snackbar.make(itemBinding.getRoot(), getText(R.string.message_auth), Snackbar.LENGTH_LONG).show();
            }
        });
        }
        else {
            Toast.makeText(getActivity(), getString(R.string.message_no_internet), Toast.LENGTH_LONG).show();
            try {
                RealmResults<StudentAttended> all = realm
                        .where(StudentAttended.class)
                        .findAll();
                RealmList<StudentAttended> results = new RealmList<>();
                results.addAll(all);
                MenuVisitAdapter menuVisitAdapter = new MenuVisitAdapter(getActivity(), results);
                menuVisitAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<StudentAttended>() {
                    @Override
                    public void onItemClick(StudentAttended item, View view) {

                        Intent intent = new Intent(getActivity(), Visit.class);
                        Parcelable wrap = Parcels.wrap(StudentAttended.class, item);
                        intent.putExtra(StudentAttended.class.getSimpleName(), wrap);
                        intent.putExtra("visit", visit);
                        startActivity(intent);
                    }
                });
                itemBinding.list.setAdapter(menuVisitAdapter);
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
