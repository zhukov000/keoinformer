package com.kolibru.schoolinfo.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.databinding.ItemLessonBinding;
import com.kolibru.schoolinfo.models.Schedule;

import io.realm.RealmList;


public class LessonAdapter extends BaseAdapter<Schedule, LessonAdapter.RouteHolder> {

    public LessonAdapter(Context context, RealmList<Schedule> source) {
        super(source);
    }

    @Override
    public RouteHolder onCreateViewHolder(View itemView) {
        return new RouteHolder(itemView);
    }

    @Override
    public View onCreateView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
    }

    @Override
    public void onBindViewHolder(final RouteHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Schedule order = getItem(position);
        holder.mBinding.setItem(order);

    }

    class RouteHolder extends BaseAdapter.ViewHolder {
        ItemLessonBinding mBinding;
        View itemView;

        public RouteHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mBinding = DataBindingUtil.bind(itemView);
        }

    }


}
