package com.kolibru.schoolinfo.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kolibru.schoolinfo.ConstClass;
import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.databinding.ItemExamBinding;
import com.kolibru.schoolinfo.databinding.ItemHomeworkBinding;
import com.kolibru.schoolinfo.models.Attendance;
import com.kolibru.schoolinfo.models.Exam;
import com.kolibru.schoolinfo.models.SubjectExam;
import com.kolibru.schoolinfo.models.SubjectHomework;

import io.realm.RealmList;


public class ExamAdapter extends BaseAdapter<SubjectExam, ExamAdapter.RouteHolder> {

    private String type=null;
    private Context context;

    public ExamAdapter(Context context, RealmList<SubjectExam> source) {
        super(source);
        this.context=context;
    }

    @Override
    public RouteHolder onCreateViewHolder(View itemView) {
        return new RouteHolder(itemView);
    }

    @Override
    public View onCreateView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exam, parent, false);
    }

    @Override
    public void onBindViewHolder(final RouteHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        SubjectExam subjectAttended = getItem(position);
        holder.mBinding.setItem(subjectAttended);
        for (Exam a:subjectAttended.getExams()) {
            if(type!=null && !a.getType().equals(type)) {
                continue;
            }
            View view = LayoutInflater.from(holder.mBinding.getRoot().getContext()).inflate(R.layout.item_exam_part, holder.mBinding.listView, false);
            TextView date = (TextView) view.findViewById(R.id.date);
            date.setText(a.getDate());
            TextView res = (TextView) view.findViewById(R.id.result);
            res.setText(String.valueOf(a.getResult()));
            TextView state = (TextView) view.findViewById(R.id.type);
            if (a.getType().equals("test")) {
                state.setText("Тест");
                state.setTextColor(ConstClass.getColor(state.getContext(), R.color.green));
            } else {
                state.setText("Экзамен");
                state.setTextColor(ConstClass.getColor(state.getContext(), R.color.red));
            }
            holder.mBinding.listView.addView(view);
        }

    }

    class RouteHolder extends BaseAdapter.ViewHolder {
        ItemExamBinding mBinding;
        View itemView;

        public RouteHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mBinding = DataBindingUtil.bind(itemView);
        }

    }


}
