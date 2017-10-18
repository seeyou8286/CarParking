package com.network.jiufen.carparking.carparking.fragment.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.adapter.BookingDetailAdapter;
import com.network.jiufen.carparking.carparking.entity.BookingDetail;
import com.network.jiufen.carparking.carparking.enumeration.BookingStatusEnum;
import com.network.jiufen.carparking.carparking.fragment.login.MobileFragment;
import com.network.jiufen.carparking.carparking.util.CustomJsonArrayRequest;
import com.network.jiufen.carparking.carparking.util.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.network.jiufen.carparking.carparking.util.HttpUtil.WEB_SERVICE_HOST;

public class BookingFragment extends Fragment {
    private String url = WEB_SERVICE_HOST + "/booking/findmybooking";
    private List<BookingDetail> bookingDetailList = new ArrayList<>();
    private BookingDetailAdapter bookingDetailAdapter;

    List<BookingDetail> bookedDetails = new ArrayList<>();
    List<BookingDetail> checkedInDetails = new ArrayList<>();
    List<BookingDetail> checkedOutDetails = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_booking, container, false);
        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation_booking_status);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.status_booked:
                        bookingDetailList.clear();
                        bookingDetailList.addAll(bookedDetails);
                        bookingDetailAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.status_checked_in:
                        bookingDetailList.clear();
                        bookingDetailList.addAll(checkedInDetails);
                        bookingDetailAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.status_checked_out:
                        bookingDetailList.clear();
                        bookingDetailList.addAll(checkedOutDetails);
                        bookingDetailAdapter.notifyDataSetChanged();
                        return true;
                }
                return false;
            }
        });
        retrieveAllMyBooking();

        bookingDetailAdapter = new BookingDetailAdapter(getActivity(), R.layout.booking_detail_info, bookingDetailList);
        ListView listView = (ListView) view.findViewById(R.id.booking_list);
        listView.setAdapter(bookingDetailAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(BriefActivity.this,ParkingLotDetailActivity.class);
//                intent.putExtra("parkingLotName",parkingLotsList.get(position).getName());
//                startActivity(intent);
//            }
//        });



        return view;
    }

    public void retrieveAllMyBooking() {
        String passedPhoneNumber = getActivity().getIntent().getStringExtra("phoneNumber");
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", passedPhoneNumber);
        JSONObject params = new JSONObject(map);
        CustomJsonArrayRequest objRequest = new CustomJsonArrayRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                String id = jsonObject.get("id").toString();
                                String bookingTime = jsonObject.get("bookingTime").toString();
                                String startTime = jsonObject.get("startTime").toString();
                                String endTime = jsonObject.get("endTime").toString();
                                String parkingLotName = jsonObject.get("parkingLotName").toString();
                                String bookingStatus = jsonObject.get("bookingStatus").toString();

                                BookingDetail bookingDetail = new BookingDetail();
                                bookingDetail.setId(id);
                                bookingDetail.setStartTime(startTime);
                                bookingDetail.setEndTime(endTime);
                                bookingDetail.setParkingLotName(parkingLotName);
                                bookingDetail.setBookingTime(bookingTime);
                                bookingDetail.setBookingStatus(BookingStatusEnum.valueOf(bookingStatus));

                                if(bookingStatus.equals(BookingStatusEnum.Booked.name()))
                                {
                                    bookedDetails.add(bookingDetail);
                                }else if(bookingStatus.equals(BookingStatusEnum.CheckedIn.name()))
                                {
                                    checkedInDetails.add(bookingDetail);
                                }else if(bookingStatus.equals(BookingStatusEnum.CheckedOut.name()))
                                {
                                    checkedOutDetails.add(bookingDetail);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        bookingDetailList.addAll(bookedDetails);
                        bookingDetailAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), "无法连接网络", Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(objRequest);

    }


}
