package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.network.jiufen.carparking.carparking.R;

public class HomepageActivity extends AppCompatActivity implements View.OnClickListener {
    private Button chooseAirport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        chooseAirport = (Button) findViewById(R.id.chooseAirport);
        chooseAirport.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(HomepageActivity.this, CitiesListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate our menu from the resources by using the menu inflater.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
