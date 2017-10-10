package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.network.jiufen.carparking.carparking.R;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView detailImage;
    private Button startBooking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_content);
        detailImage = (ImageView) findViewById(R.id.detail_image);
        detailImage.setClickable(true);
        detailImage.setOnClickListener(this);

        startBooking = (Button) findViewById(R.id.startBooking);
        startBooking.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String parkingLotName = this.getIntent().getStringExtra("parkingLotName");
        Intent intent = null;
        switch (view.getId())
        {
            case R.id.detail_image:
                intent = new Intent(DetailActivity.this,PicViewerActivity.class);
                break;
            case R.id.startBooking:
                intent = new Intent(DetailActivity.this,BookingActivity.class);
                intent.putExtra("parkingLotName",parkingLotName);
                break;
        }
        startActivity(intent);
    }
}
