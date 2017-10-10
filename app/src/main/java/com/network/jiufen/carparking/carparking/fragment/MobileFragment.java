package com.network.jiufen.carparking.carparking.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.activity.ContentActivity;
import com.network.jiufen.carparking.carparking.util.DictionaryUtil;
import com.network.jiufen.carparking.carparking.util.SMSender;
import com.network.jiufen.carparking.carparking.util.SharedPrefsUtil;

import static android.content.Context.MODE_PRIVATE;

public class MobileFragment extends Fragment {
    private Context context;
    private String verificationCode;

    private EditText phone;
    private EditText cord;
    private TextView hintmessage;
    private Button getCord;
    private Button saveCord;
    private String iPhone;
    private String iCord;
    private int time = 60;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mobile_login, container, false);
        init(view);
        return view;
    }


    private void init(View view) {
        phone = (EditText) view.findViewById(R.id.phone);
        cord = (EditText) view.findViewById(R.id.cord);
        hintmessage = (TextView) view.findViewById(R.id.hintmessage);
        getCord = (Button) view.findViewById(R.id.getcord);
        saveCord = (Button) view.findViewById(R.id.savecord);
        getCord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(phone.getText().toString().trim())) {
                    if (phone.getText().toString().trim().length() == 11) {
                        iPhone = phone.getText().toString().trim();
                        verificationCode = (int) (Math.random() * 9000 + 1000) + "";
                        SMSender smSender = new SMSender(iPhone, verificationCode, DictionaryUtil.dictionary.get(DictionaryUtil.APP_KEY), DictionaryUtil.dictionary.get(DictionaryUtil.APP_SECRET));
                        Thread thread = new Thread(smSender);
                        thread.start();
                        cord.requestFocus();
                        getCord.setVisibility(View.GONE);
                        reminderText();
                    } else {
                        Toast.makeText(getActivity(), "请输入完整电话号码", Toast.LENGTH_LONG).show();
                        phone.requestFocus();
                    }
                } else {
                    Toast.makeText(getActivity(), "请输入您的电话号码", Toast.LENGTH_LONG).show();
                    phone.requestFocus();
                }
            }
        });
        saveCord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(cord.getText().toString().trim())) {
                    if (cord.getText().toString().trim().length() == 4) {
                        iCord = cord.getText().toString().trim();
                        if (verificationCode.equals(iCord)) {
                            SharedPrefsUtil.putValue(getActivity().getApplicationContext(),"phone",iPhone);
                            Toast.makeText(getActivity(), "验证码校验成功", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getActivity(), ContentActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "验证码错误", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "请输入完整验证码", Toast.LENGTH_LONG).show();
                        cord.requestFocus();
                    }
                } else {
                    Toast.makeText(getActivity(), "请输入验证码", Toast.LENGTH_LONG).show();
                    cord.requestFocus();
                }
            }
        });
    }


    //验证码送成功后提示文字
    private void reminderText() {
        hintmessage.setVisibility(View.VISIBLE);
        handlerText.sendEmptyMessageDelayed(1, 1000);
    }

    Handler handlerText = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (time > 0) {
                    hintmessage.setText("验证码已发送" + time + "秒");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                } else {
                    hintmessage.setText("提示信息");
                    time = 60;
                    hintmessage.setVisibility(View.GONE);
                    getCord.setVisibility(View.VISIBLE);
                }
            } else {
                cord.setText("");
                hintmessage.setText("提示信息");
                time = 60;
                hintmessage.setVisibility(View.GONE);
                getCord.setVisibility(View.VISIBLE);
            }
        }
    };


}
