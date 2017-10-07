package com.network.jiufen.carparking.carparking.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.entity.ParkingLot;

import java.util.List;

/**
 * Created by asus on 2017/9/23.
 */

public class ParkingLotAdapter extends ArrayAdapter<ParkingLot> {
    private int resourceId;

    public ParkingLotAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ParkingLot> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ParkingLot parkingLot = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView imageView = view.findViewById(R.id.brief_image);
        TextView name = view.findViewById(R.id.brief_name);
        TextView points = view.findViewById(R.id.points);
        TextView distance = view.findViewById(R.id.distance);
        TextView monthlySoldOut = view.findViewById(R.id.monthlySoldOut);
        TextView price = view.findViewById(R.id.price);
        //TODO
        imageView.setImageResource(R.drawable.car_park1);
        name.setText(parkingLot.getName());
        distance.setText(parkingLot.getDistance());
        monthlySoldOut.setText(parkingLot.getDistance());
        points.setText(parkingLot.getRate());
        price.setText(parkingLot.getDayPrice().toString());
        return view;
    }
}
