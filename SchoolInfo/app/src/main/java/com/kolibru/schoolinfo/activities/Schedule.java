package com.kolibru.schoolinfo.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.adapters.LessonAdapter;
import com.kolibru.schoolinfo.databinding.FragScheduleBinding;
import com.kolibru.schoolinfo.models.StudentTimeTable;

import org.parceler.Parcels;

public class Schedule extends BaseActivity {

    private StudentTimeTable studentTimeTable;

    FragScheduleBinding fragScheduleBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_schedule);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragScheduleBinding= DataBindingUtil.setContentView(this, R.layout.frag_schedule);
        studentTimeTable= Parcels.unwrap(getIntent().getParcelableExtra(StudentTimeTable.class.getSimpleName()));
        fragScheduleBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragScheduleBinding.list.setAdapter(new LessonAdapter(getActivity(),studentTimeTable.getTimetable()));

    }
}
