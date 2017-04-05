package com.kolibru.schoolinfo.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kolibru.schoolinfo.ConstClass;
import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.databinding.ItemLessonBinding;
import com.kolibru.schoolinfo.databinding.ItemVisitBinding;
import com.kolibru.schoolinfo.models.Attendance;
import com.kolibru.schoolinfo.models.Schedule;
import com.kolibru.schoolinfo.models.SubjectAttended;

import io.realm.RealmList;

public class VisitAdapter extends BaseAdapter<SubjectAttended, VisitAdapter.RouteHolder> {

    private String type=null;//"attended"-visit
    private Context context;

    public VisitAdapter(Context context, RealmList<SubjectAttended> source) {
        super(source);
        this.context=context;
    }

    public VisitAdapter(Context context, RealmList<SubjectAttended> source,String type) {
        super(source);
        this.context=context;
        this.type=type;
    }

    @Override
    public RouteHolder onCreateViewHolder(View itemView) {
        return new RouteHolder(itemView);
    }

    @Override
    public View onCreateView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visit, parent, false);
    }

    @Override
    public void onBindViewHolder(final RouteHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        SubjectAttended subjectAttended = getItem(position);
        holder.mBinding.setItem(subjectAttended);
        for (Attendance a:subjectAttended.getAttendance()) {
            if(type!=null && !a.getState().equals(type)) {
                continue;
            }
            View view = LayoutInflater.from(holder.mBinding.getRoot().getContext()).inflate(R.layout.item_visit_part, holder.mBinding.listView, false);
            TextView date = (TextView) view.findViewById(R.id.date);
            date.setText(a.getDate());
            TextView state = (TextView) view.findViewById(R.id.state);
            if (a.getState().equals("attended")) {
                state.setText(context.getText(R.string.attended_present));
                state.setTextColor(ConstClass.getColor(state.getContext(), R.color.green));
            } else {
                state.setText(context.getText(R.string.attended_absent));
                state.setTextColor(ConstClass.getColor(state.getContext(), R.color.red));
            }
            holder.mBinding.listView.addView(view);
        }

    }

    class RouteHolder extends BaseAdapter.ViewHolder {
        ItemVisitBinding mBinding;
        View itemView;

        public RouteHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mBinding = DataBindingUtil.bind(itemView);
        }

    }


}
