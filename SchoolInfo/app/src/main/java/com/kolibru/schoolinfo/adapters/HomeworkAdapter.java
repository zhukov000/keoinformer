package com.kolibru.schoolinfo.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kolibru.schoolinfo.ConstClass;
import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.databinding.ItemHomeworkBinding;
import com.kolibru.schoolinfo.databinding.ItemVisitBinding;
import com.kolibru.schoolinfo.models.Attendance;
import com.kolibru.schoolinfo.models.Homework;
import com.kolibru.schoolinfo.models.SubjectAttended;
import com.kolibru.schoolinfo.models.SubjectHomework;

import io.realm.RealmList;


public class HomeworkAdapter extends BaseAdapter<SubjectHomework, HomeworkAdapter.RouteHolder> {

    private String type=null;//"attended"-visit
    private Context context;

    public HomeworkAdapter(Context context, RealmList<SubjectHomework> source) {
        super(source);
        this.context=context;
    }

    @Override
    public RouteHolder onCreateViewHolder(View itemView) {
        return new RouteHolder(itemView);
    }

    @Override
    public View onCreateView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homework, parent, false);
    }

    @Override
    public void onBindViewHolder(final RouteHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        SubjectHomework subjectAttended = getItem(position);
        holder.mBinding.setItem(subjectAttended);
        for (Homework a:subjectAttended.getHomework()) {
            if(type!=null && !a.getState().equals(type)) {
                continue;
            }
            View view = LayoutInflater.from(holder.mBinding.getRoot().getContext()).inflate(R.layout.item_homework_part, holder.mBinding.listView, false);
            TextView date = (TextView) view.findViewById(R.id.date);
            date.setText(a.getDate());
            TextView state = (TextView) view.findViewById(R.id.state);
            if (a.getState().equals("done")) {
                state.setText(context.getString(R.string.state_succ));
                state.setTextColor(ConstClass.getColor(state.getContext(), R.color.green));
            } else {
                state.setText(context.getString(R.string.state_fail));
                state.setTextColor(ConstClass.getColor(state.getContext(), R.color.red));
            }
            holder.mBinding.listView.addView(view);
        }

    }

    class RouteHolder extends BaseAdapter.ViewHolder {
        ItemHomeworkBinding mBinding;
        View itemView;

        public RouteHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mBinding = DataBindingUtil.bind(itemView);
        }

    }


}
