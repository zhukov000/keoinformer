package com.kolibru.schoolinfo.activities;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;

import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.adapters.LessonAdapter;
import com.kolibru.schoolinfo.adapters.VisitAdapter;
import com.kolibru.schoolinfo.databinding.FragVisitBinding;
import com.kolibru.schoolinfo.models.StudentAttended;
import com.kolibru.schoolinfo.models.StudentTimeTable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.parceler.Parcels;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Visit extends BaseActivity {

    FragVisitBinding fragVisitBinding;
    StudentAttended studentAttended;
    String visit="attended";
    private Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_visit);
        realm=Realm.getDefaultInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragVisitBinding= DataBindingUtil.setContentView(this, R.layout.frag_visit);
        studentAttended= Parcels.unwrap(getIntent().getParcelableExtra(StudentAttended.class.getSimpleName()));
        visit= getIntent().getStringExtra("visit");
        fragVisitBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragVisitBinding.list.setAdapter(new VisitAdapter(getActivity(), studentAttended.getSubjects(),visit));
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();
    }
}
