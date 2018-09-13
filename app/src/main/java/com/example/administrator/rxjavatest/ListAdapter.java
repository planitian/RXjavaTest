package com.example.administrator.rxjavatest;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ListAdapter extends BaseAdapter {
    private List<Operate> operates;



    @Override
    public int getCount() {
        return operates.size();
    }

    @Override
    public Object getItem(int position) {
        return operates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Operate operate=operates.get(position);
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        TextView textView = convertView.findViewById(R.id.textView);
        textView.setText(operate.getName());
        operate.change(new Operate.CallBack() {
            @Override
            public void change(String info) {
                Observable.just(info).observeOn(AndroidSchedulers.mainThread()).subscribe(v->{
                    textView.setText(v);
                });
            }
        });
        return null;
    }
}
