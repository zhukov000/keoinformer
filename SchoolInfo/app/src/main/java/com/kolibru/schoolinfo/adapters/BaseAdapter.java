package com.kolibru.schoolinfo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.kolibru.schoolinfo.R;

import java.util.List;


public abstract class BaseAdapter<T, VH extends BaseAdapter.ViewHolder> extends RecyclerView.Adapter<VH> implements View.OnClickListener {

    List<T> source;
    OnItemClickListener mOnItemClickListener;//храним обработчик нажатий

    public BaseAdapter(List<T> source) {
        this.source = source;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.itemView.setTag(R.id.item_tag, position);
    }

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = onCreateView(parent, viewType);
        itemView.setOnClickListener(this);
        return onCreateViewHolder(itemView);
    }

    public abstract View onCreateView(ViewGroup parent, int viewType);

    public abstract VH onCreateViewHolder(View itemView);

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public int getItemIndex(T item){
        return source.indexOf(item);
    }

    public T getItem(int position) {
        if(position<=source.size())
            return source.get(position);
        else
            return null;
    }

    @Override
    public int getItemCount() {
        return source.size();
    }


    @Override
    public void onClick(View v) {       //когда нажали объект адаптера
        int position = (int) v.getTag(R.id.item_tag);
        if (mOnItemClickListener != null) { //если есть обработчик нажатия - то запускаем его
            mOnItemClickListener.onItemClick(source.get(position), v);
        }
    }

    public interface OnItemClickListener<T> {//интерфейс работы нажатия

        void onItemClick(T item, View view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
