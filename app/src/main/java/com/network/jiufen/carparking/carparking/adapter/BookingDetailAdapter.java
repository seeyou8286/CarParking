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
import com.network.jiufen.carparking.carparking.entity.BookingDetail;
import com.network.jiufen.carparking.carparking.entity.ParkingLot;

import java.util.List;

/**
 * Created by asus on 2017/9/23.
 */

public class BookingDetailAdapter extends ArrayAdapter<BookingDetail> {
    private int resourceId;

    public BookingDetailAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<BookingDetail> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookingDetail bookingDetail = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView parkingLotName = view.findViewById(R.id.parkingLotName);
        TextView bookingTime = view.findViewById(R.id.bookingTime);
        TextView status = view.findViewById(R.id.status);
        TextView bookingId = view.findViewById(R.id.bookingId);
        TextView startTime = view.findViewById(R.id.startTime);
        TextView endTime = view.findViewById(R.id.endTime);

        parkingLotName.setText(bookingDetail.getParkingLotName());
        bookingTime.setText(bookingDetail.getBookingTime());
        status.setText("未进场");
        bookingId.setText(bookingDetail.getId());
        startTime.setText(bookingDetail.getStartTime());
        endTime.setText(bookingDetail.getEndTime());
        return view;
    }
}
