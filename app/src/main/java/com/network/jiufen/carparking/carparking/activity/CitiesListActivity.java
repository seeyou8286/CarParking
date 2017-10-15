package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bigkoo.quicksidebar.QuickSideBarTipsView;
import com.bigkoo.quicksidebar.QuickSideBarView;
import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener;
import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.adapter.CityListAdapter;
import com.network.jiufen.carparking.carparking.adapter.OnRecyclerviewItemClickListener;
import com.network.jiufen.carparking.carparking.decoration.DividerDecoration;
import com.network.jiufen.carparking.carparking.entity.City;
import com.network.jiufen.carparking.carparking.util.MySingleton;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.network.jiufen.carparking.carparking.util.HttpUtil.WEB_SERVICE_HOST;

public class CitiesListActivity extends AppCompatActivity implements OnQuickSideBarTouchListener {
    private String url = WEB_SERVICE_HOST + "/airport/findall";
    private List<City> airportList = new ArrayList<>();
    RecyclerView recyclerView;
    HashMap<String, Integer> letters = new HashMap<>();
    QuickSideBarView quickSideBarView;
    QuickSideBarTipsView quickSideBarTipsView;


    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            Intent intent = new Intent(CitiesListActivity.this, BriefActivity.class);
            intent.putExtra("airportName",airportList.get(position).getName());
            startActivity(intent);
        }
    };


    CityListWithHeadersAdapter adapter = new CityListWithHeadersAdapter(onRecyclerviewItemClickListener);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cities_list);

        initGettingAirporList();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        quickSideBarView = (QuickSideBarView) findViewById(R.id.quickSideBarView);
        quickSideBarTipsView = (QuickSideBarTipsView) findViewById(R.id.quickSideBarTipsView);

        //设置监听
        quickSideBarView.setOnQuickSideBarTouchListener(this);


        //设置列表数据和浮动header
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Add the sticky headers decoration


        ArrayList<String> customLetters = new ArrayList<>();

        int position = 0;
        for (City city : airportList) {
            String letter = city.getFirstLetter();
            //如果没有这个key则加入并把位置也加入
            if (!letters.containsKey(letter)) {
                letters.put(letter, position);
                customLetters.add(letter);
            }
            position++;
        }

        //不自定义则默认26个字母
        quickSideBarView.setLetters(customLetters);
        adapter.addAll(airportList);
        recyclerView.setAdapter(adapter);

        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        recyclerView.addItemDecoration(new DividerDecoration(this));

    }


    @Override
    public void onLetterChanged(String letter, int position, float y) {
        quickSideBarTipsView.setText(letter, position, y);
        //有此key则获取位置并滚动到该位置
        if (letters.containsKey(letter)) {
            recyclerView.scrollToPosition(letters.get(letter));
        }
    }

    @Override
    public void onLetterTouching(boolean touching) {
        //可以自己加入动画效果渐显渐隐
        quickSideBarTipsView.setVisibility(touching ? View.VISIBLE : View.INVISIBLE);
    }

    private class CityListWithHeadersAdapter extends CityListAdapter<RecyclerView.ViewHolder>
            implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>,View.OnClickListener {

        private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;

        public CityListWithHeadersAdapter(OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
            this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_item, parent, false);
            view.setOnClickListener(this);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(getItem(position).getName());
            holder.itemView.setTag(position);
        }

        @Override
        public long getHeaderId(int position) {
            return getItem(position).getFirstLetter().charAt(0);
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_header, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(String.valueOf(getItem(position).getFirstLetter()));
            holder.itemView.setBackgroundColor(Color.parseColor("darkgrey"));
        }

        private int getRandomColor() {
            SecureRandom rgen = new SecureRandom();
            return Color.HSVToColor(150, new float[]{
                    rgen.nextInt(359), 1, 1
            });
        }

        @Override
        public void onClick(View v) {
            mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
        }
    }


    public void initGettingAirporList() {
        JsonArrayRequest objRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                String name = ((JSONObject) response.get(i)).get("name").toString();
                                String firstLetter = ((JSONObject) response.get(i)).get("firstLetter").toString();
                                City city = new City(name, firstLetter);
                                airportList.add(city);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.addAll(airportList);
                        adapter.notifyDataSetChanged();
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
}
