package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.adapter.ParkingLotAdapter;
import com.network.jiufen.carparking.carparking.entity.ParkingLot;
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

public class BriefActivity extends AppCompatActivity{
    private String url = WEB_SERVICE_HOST+"/park/find";
    private List<ParkingLot> parkingLotsList = new ArrayList<>();
    private ParkingLotAdapter parkingLotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brief_list);
        Intent intent = this.getIntent();
        String airportName = intent.getStringExtra("airportName");
        initDetails(airportName);
        parkingLotAdapter = new ParkingLotAdapter(BriefActivity.this,R.layout.brief_info,parkingLotsList);
        ListView listView = (ListView)findViewById(R.id.brief_list);
        listView.setAdapter(parkingLotAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(parkingLotsList.get(position));
                Intent intent = new Intent(BriefActivity.this,DetailActivity.class);
                startActivity(intent);
            }
        });
    }


    public void initDetails(final String airportName)
    {
        Map request = new HashMap();
        request.put("airportName",airportName);
        JSONObject params = new JSONObject(request);
        CustomJsonArrayRequest objRequest = new CustomJsonArrayRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                String name = jsonObject.get("name").toString();
                                String distance = jsonObject.get("distance").toString();
                                String rate = jsonObject.get("rate").toString();
                                Integer dayPrice = jsonObject.getInt("dayPrice");
                                String cityName = jsonObject.get("cityName").toString();
                                ParkingLot parkingLot =
                                        new ParkingLot(name,distance,rate,dayPrice,airportName,cityName);
                                parkingLotsList.add(parkingLot);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        parkingLotAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                  error.printStackTrace();
                Toast.makeText(BriefActivity.this, "无法连接网络", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("content-type", "application/json");
                return headers;
            }
        } ;
        MySingleton.getInstance(BriefActivity.this).addToRequestQueue(objRequest);
    }

}
