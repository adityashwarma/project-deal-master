package com.example.dealrunner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private final String[] select_business_option = {"Food", "Cinema", "Clothes", "Accessories"};


    public CustomAdapter(Context context) {
        super(context, R.layout.select_business_type_layout);
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return select_business_option.length;
    }

    @Override
    public Object getItem(int i) {
        return select_business_option[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.select_business_type_layout, null);
        TextView select_business_text = view.findViewById(R.id.select_business_type_item_textview);
        select_business_text.setText(select_business_option[i]);
        return view;
    }

    public String getItemValue(int i) {
        return select_business_option[i];
    }
}
