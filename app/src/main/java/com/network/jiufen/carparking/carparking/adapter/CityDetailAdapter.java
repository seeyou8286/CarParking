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
        ImageView imageView = view.findViewById(R.id.detail_image);
        TextView name = view.findViewById(R.id.detail_name);
        TextView points = view.findViewById(R.id.points);
        TextView distance = view.findViewById(R.id.distance);
        TextView monthlySoldOut = view.findViewById(R.id.monthlySoldOut);
        TextView price = view.findViewById(R.id.price);
        imageView.setImageResource(carParkDetail.getImageId());
        name.setText(carParkDetail.getName());
        distance.setText(carParkDetail.getDistance());
        monthlySoldOut.setText(carParkDetail.getDistance());
        points.setText(carParkDetail.getPoints());
        price.setText(carParkDetail.getPrice());
        return view;
    }
}
