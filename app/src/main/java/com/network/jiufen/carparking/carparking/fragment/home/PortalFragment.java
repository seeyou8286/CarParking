package com.network.jiufen.carparking.carparking.fragment.home;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.activity.CitiesListActivity;

public class PortalFragment extends Fragment {
    private Button chooseAirport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_portal, container, false);
        chooseAirport = view.findViewById(R.id.chooseAirport);
        chooseAirport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CitiesListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


}
