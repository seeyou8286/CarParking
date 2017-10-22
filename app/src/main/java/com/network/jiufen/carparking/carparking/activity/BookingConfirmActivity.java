package com.network.jiufen.carparking.carparking.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;
import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.entity.BookingDetail;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.network.jiufen.carparking.carparking.util.DateUtil.DefautDateFormat;
import static com.network.jiufen.carparking.carparking.util.HttpUtil.WEB_SERVICE_HOST;

public class BookingConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    private String url = WEB_SERVICE_HOST + "/booking/save";

    /**
     * Called when the activity is first created.
     */
    @BindView(R.id.parkingLotName)
    TextView parkingLotName;
//    @BindView(R.id.categoryValue)
//    TextView categoryValue;
    @BindView(R.id.plannedCheckInTime)
    TextView startTimeValue;
    @BindView(R.id.plannedCheckOutTime)
    TextView endTimeValue;
    @BindView(R.id.plateValue)
    TextView plateValue;
    @BindView(R.id.confirmBooking)
    Button confirmBookingButton;
    @BindView(R.id.navigation_button)
    ImageButton navigationButton;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.parkingDays)
    TextView parkingDays;
    @BindView(R.id.bookingStatus)
    TextView bookingStatus;
    @BindView(R.id.totalPrice)
    TextView totalPrice;
    @BindView(R.id.leftPrice)
    TextView leftPrice;
    @BindView(R.id.bookingFee)
    TextView bookingFee;
    private String phoneNumber;
    private Context mContext = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_booking_content);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        BookingDetail bookingDetail = (BookingDetail)intent.getSerializableExtra("bookingDetail");
        mContext = this;


        parkingLotName.setText(bookingDetail.getParkingLotName());
        //TODO
//        categoryValue.setText("室内停车");
        startTimeValue.setText(bookingDetail.getPlanedCheckInTime().toString(DefautDateFormat));
        endTimeValue.setText(bookingDetail.getPlanedCheckOutTime().toString(DefautDateFormat));
        plateValue.setText(bookingDetail.getPlateNumber());
        address.setText(bookingDetail.getParkingLotAddress());
        parkingDays.setText(bookingDetail.getParkingDays().toString());
        bookingStatus.setText(bookingDetail.getBookingStatus().toString());
        totalPrice.setText(bookingDetail.getTotalPrice().toString());
        Integer left = bookingDetail.getTotalPrice() - bookingDetail.getBookingFee();
        leftPrice.setText(left.toString());
        bookingFee.setText(bookingDetail.getBookingFee().toString()  );
        confirmBookingButton.setOnClickListener(this);
        navigationButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.confirmBooking:
            {
                Intent intent = new Intent(BookingConfirmActivity.this, PaymentSuccessActivity.class);
                intent.putExtra("phoneNumber",phoneNumber);
                startActivity(intent);
                break;
            }
            case R.id.navigation_button:
            {
                showPopupWindow(view);
//                Toast.makeText(this, "无法连接网络", Toast.LENGTH_LONG).show();
            }

        }

    }


    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.navigation_choose, null);
        // 设置按钮的点击事件
        Button amap = (Button) contentView.findViewById(R.id.amap);
        Button baidu = (Button) contentView.findViewById(R.id.amap);
        amap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                 intent.setAction(Intent.ACTION_VIEW);
                 intent.addCategory(Intent.CATEGORY_DEFAULT);//将功能Scheme以URI的方式传入data
                 Uri uri = Uri.parse("amapuri://route/plan/?sid=BGVIS1&sname=我的位置&did=BGVIS2&dlon=113.898487&dlat=22.516903&dname=中海阳光玫瑰园&dev=0&t=0");
                 intent.setData(uri);//启动该页面即可
            }
        });

        baidu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {

            }
        });

        final PopupWindow popupWindow = new PopupWindow(contentView,
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL,0,0);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

//        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
//        // 我觉得这里是API的一个bug
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(
//                R.drawable.));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }
}
