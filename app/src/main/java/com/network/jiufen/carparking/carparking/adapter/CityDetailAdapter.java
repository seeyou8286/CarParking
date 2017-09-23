package com.network.jiufen.carparking.carparking.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.domain.CarParkDetail;

import java.util.List;

/**
 * Created by asus on 2017/9/23.
 */

public class CityDetailAdapter extends ArrayAdapter<CarParkDetail>{
    private int resourceId;

    public CityDetailAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<CarParkDetail> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CarParkDetail carParkDetail = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView fruitImage = view.findViewById(R.id.detail_image);
        TextView fruitName = view.findViewById(R.id.detail_name);
        fruitImage.setImageResource(carParkDetail.getImageId());
        fruitName.setText(carParkDetail.getName());
        return view;
    }
}
