package com.kolibru.schoolinfo.activities;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.adapters.ExamAdapter;
import com.kolibru.schoolinfo.adapters.HomeworkAdapter;
import com.kolibru.schoolinfo.adapters.VisitAdapter;
import com.kolibru.schoolinfo.databinding.ActivityHomeworkBinding;
import com.kolibru.schoolinfo.databinding.FragHomeworkBinding;
import com.kolibru.schoolinfo.databinding.FragVisitBinding;
import com.kolibru.schoolinfo.models.StudentAttended;
import com.kolibru.schoolinfo.models.StudentExam;
import com.kolibru.schoolinfo.models.StudentHomework;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.parceler.Parcels;

import java.util.Calendar;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Homework extends BaseActivity {

    ActivityHomeworkBinding fragVisitBinding;
    StudentHomework studentAttended;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragVisitBinding= DataBindingUtil.setContentView(this, R.layout.activity_homework);
        studentAttended= Parcels.unwrap(getIntent().getParcelableExtra(StudentHomework.class.getSimpleName()));
        fragVisitBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadList(studentAttended);
    }


    private void loadList(StudentHomework item)
    {
        fragVisitBinding.list.setAdapter(new HomeworkAdapter(getActivity(),item.getSubjects()));
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
