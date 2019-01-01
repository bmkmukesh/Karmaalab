package com.example.hduser.karmaalab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ItemHolder extends RecyclerView.ViewHolder {

    public TextView txtcompanyName, txtManufacturer, txtCategory, txtModel;

    public ItemHolder(View view) {
        super(view);
        txtcompanyName = (TextView) view.findViewById(R.id.txtCompanyName);
        txtManufacturer = (TextView) view.findViewById(R.id.txtManufacturer);
        txtCategory = (TextView) view.findViewById(R.id.txtCategory);
        txtModel = (TextView) view.findViewById(R.id.txtModel);

    }
}