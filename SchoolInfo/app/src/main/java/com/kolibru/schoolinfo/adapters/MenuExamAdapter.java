package com.kolibru.schoolinfo.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.activities.BaseActivity;
import com.kolibru.schoolinfo.activities.MenuExam;
import com.kolibru.schoolinfo.databinding.ItemExamBinding;
import com.kolibru.schoolinfo.databinding.ItemMenuExamBinding;
import com.kolibru.schoolinfo.models.Exam;
import com.kolibru.schoolinfo.models.Person;
import com.kolibru.schoolinfo.models.StudentExam;
import com.kolibru.schoolinfo.models.SubjectExam;

import io.realm.Realm;
import io.realm.RealmList;


public class MenuExamAdapter extends BaseAdapter<StudentExam, MenuExamAdapter.RouteHolder> {

    private String type=null;
    private Context context;

    public MenuExamAdapter(Context context, RealmList<StudentExam> source) {
        super(source);
        this.context=context;
    }

    @Override
    public RouteHolder onCreateViewHolder(View itemView) {
        return new RouteHolder(itemView);
    }

    @Override
    public View onCreateView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_exam, parent, false);
    }

    @Override
    public void onBindViewHolder(final RouteHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        StudentExam item = getItem(position);
        holder.mBinding.setItem(item);
        try {
            Realm realm = Realm.getDefaultInstance();
            Person res = realm
                    .where(Person.class)
                    .equalTo("id", item.getStudent_id())
                    .findFirst();
            holder.mBinding.title.setText(res.getFullname());
            realm.close();
        }
        catch (Exception e){

        }

    }

    class RouteHolder extends BaseAdapter.ViewHolder {
        ItemMenuExamBinding mBinding;
        View itemView;

        public RouteHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mBinding = DataBindingUtil.bind(itemView);
        }

    }


}
