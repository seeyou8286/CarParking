package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.network.jiufen.carparking.carparking.R;

public class PaymentSuccessActivity extends AppCompatActivity implements View.OnClickListener {
    private Button returnButton;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_success);
        Intent intent = this.getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        returnButton = (Button) findViewById(R.id.returnToHomePage);
        returnButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.returnToHomePage: {
                Intent nextIntent = new Intent(this, HomePageActivity.class);
                nextIntent.putExtra("phoneNumber",phoneNumber);
                startActivity(nextIntent);
            }
        }
    }
}
