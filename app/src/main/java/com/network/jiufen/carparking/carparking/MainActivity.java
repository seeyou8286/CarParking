package com.network.jiufen.carparking.carparking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.mob.tools.utils.ResHelper.getStringRes;

public class MainActivity extends Activity implements View.OnClickListener {
    private EditText phone;
    private EditText cord;
    private TextView now;
    private Button getCord;
    private Button saveCord;
    private String iPhone;
    private String iCord;
    private int time = 60;
    private boolean flag = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }

    private void init() {
        phone = (EditText) findViewById(R.id.phone);
        cord = (EditText) findViewById(R.id.cord);
        now = (TextView) findViewById(R.id.now);
        getCord = (Button) findViewById(R.id.getcord);
        saveCord = (Button) findViewById(R.id.savecord);
        getCord.setOnClickListener(this);
        saveCord.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcord:
                if (!TextUtils.isEmpty(phone.getText().toString().trim())) {
                    if (phone.getText().toString().trim().length() == 11) {
                        iPhone = phone.getText().toString().trim();
                        SMSSDK.getVerificationCode("86", iPhone);
                        cord.requestFocus();
                        getCord.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(MainActivity.this, "请输入完整电话号码", Toast.LENGTH_LONG).show();
                        phone.requestFocus();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
                    phone.requestFocus();
                }
                break;
            case R.id.savecord:
                if (!TextUtils.isEmpty(cord.getText().toString().trim())) {
                    if (cord.getText().toString().trim().length() == 4) {
                        iCord = cord.getText().toString().trim();
                        SMSSDK.submitVerificationCode("86", iPhone, iCord);
                        flag = false;
                    } else {
                        Toast.makeText(MainActivity.this, "请输入完整验证码", Toast.LENGTH_LONG).show();
                        cord.requestFocus();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();
                    cord.requestFocus();
                }
                break;
            default:
                break;
        }
    }

    //验证码送成功后提示文字
    private void reminderText() {
        now.setVisibility(View.VISIBLE);
        handlerText.sendEmptyMessageDelayed(1, 1000);
    }

    Handler handlerText = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (time > 0) {
                    now.setText("验证码已发送" + time + "秒");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                } else {
                    now.setText("提示信息");
                    time = 60;
                    now.setVisibility(View.GONE);
                    getCord.setVisibility(View.VISIBLE);
                }
            } else {
                cord.setText("");
                now.setText("提示信息");
                time = 60;
                now.setVisibility(View.GONE);
                getCord.setVisibility(View.VISIBLE);
            }
        }
    };



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(MainActivity.this, ContentActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "验证码校验成功", Toast.LENGTH_SHORT).show();
            handlerText.sendEmptyMessage(2);
            return;


            // TODO Auto-generated method stub
//            super.handleMessage(msg);
//            int event = msg.arg1;
//            int result = msg.arg2;
//            Object data = msg.obj;
//            Log.e("event", "event=" + event);
//            if (result == SMSSDK.RESULT_COMPLETE) {
//            //短信注册成功后，返回MainActivity,然后提示新好友
//                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过
//                    Toast.makeText(getApplicationContext(), "验证码校验成功", Toast.LENGTH_SHORT).show();
//                    handlerText.sendEmptyMessage(2);
//                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//服务器验证码发送成功
//                    reminderText();
//                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
//                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表
//                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                if (flag) {
//                    getCord.setVisibility(View.VISIBLE);
//                    Toast.makeText(MainActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
//                    phone.requestFocus();
//                } else {
//                    ((Throwable) data).printStackTrace();
//                    int resId = getStringRes(MainActivity.this, "smssdk_network_error");
//                    Toast.makeText(MainActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
//                    cord.selectAll();
//                    if (resId > 0) {
//                        Toast.makeText(MainActivity.this, resId, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }



        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
