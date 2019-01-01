package com.example.hduser.karmaalab;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

    private List<Asset> AssetList;

    public ItemAdapter(List<Asset> AssetList) {
        this.AssetList = AssetList;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Asset item = AssetList.get(position);
        holder.txtcompanyName.setText(item.getCompanyName());
        holder.txtManufacturer.setText(item.getManufacturer());
        holder.txtCategory.setText(item.getCategory());
        holder.txtModel.setText(item.getModel());
    }

    @Override
    public int getItemCount() {
        return AssetList.size();
    }
}
