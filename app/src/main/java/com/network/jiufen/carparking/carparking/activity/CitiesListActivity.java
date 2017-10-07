package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.util.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CitiesListActivity extends AppCompatActivity implements View.OnClickListener {
    private String url = "https://carparkingservice.herokuapp.com/airport/findall";
    private List<String> airportList = new ArrayList<>();

    private ListView listView;

    private ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cities_list);
        listView = (ListView) findViewById(R.id.listViewBasic);

        initGettingAirporList();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, airportList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(airportList.get(position));
                Intent intent = new Intent(CitiesListActivity.this, BriefActivity.class);
                intent.putExtra("airportName",airportList.get(position));
                startActivity(intent);
            }
        });
    }


    public void initGettingAirporList() {
        JsonArrayRequest objRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        arrayAdapter.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                String name = ((JSONObject) response.get(i)).get("name").toString();
                                airportList.add(name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(CitiesListActivity.this, "无法连接网络", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("content-type", "application/json");
                return headers;
            }
        };
        MySingleton.getInstance(CitiesListActivity.this).addToRequestQueue(objRequest);
    }


    @Override
    public void onClick(View view) {

    }
}
