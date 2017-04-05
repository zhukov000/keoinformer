package com.kolibru.schoolinfo.activities;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.adapters.ExamAdapter;
import com.kolibru.schoolinfo.adapters.HomeworkAdapter;
import com.kolibru.schoolinfo.databinding.ActivityExamBinding;
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

public class Exam extends BaseActivity {

    ActivityExamBinding fragVisitBinding;
    StudentExam studentAttended;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragVisitBinding= DataBindingUtil.setContentView(this, R.layout.activity_exam);
        studentAttended= Parcels.unwrap(getIntent().getParcelableExtra(StudentExam.class.getSimpleName()));
        fragVisitBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadList(studentAttended);
    }


    private void loadList(StudentExam item)
    {
        fragVisitBinding.list.setAdapter(new ExamAdapter(getActivity(),item.getSubjects()));
    }


}
