package com.network.jiufen.carparking.carparking.fragment;

import android.app.Fragment;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.activity.ContentActivity;
import com.network.jiufen.carparking.carparking.util.DictionaryUtil;
import com.network.jiufen.carparking.carparking.util.MySingleton;
import com.network.jiufen.carparking.carparking.util.SMSender;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.network.jiufen.carparking.carparking.util.HttpUtil.WEB_SERVICE_HOST;

public class RegistrationFragment extends Fragment {
    private String url = WEB_SERVICE_HOST+"/account/save";
    private int time = 60;
    private String verificationCode;
    private EditText account_name;
    private Button validatePhone;
    private EditText newVerificationCode;
    private EditText newPasswordAgain;
    private EditText newPassword;
    private Button registration;
    private String phoneNumber;
    private TextView new_hintmessage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        init(view);
        return view;
    }


    private void init(View view) {
        account_name = view.findViewById(R.id.new_account_name);
        validatePhone = view.findViewById(R.id.validatePhone);
        newVerificationCode =  view.findViewById(R.id.new_verificationCode);
        newPasswordAgain = view.findViewById(R.id.new_password_again);
        newPassword = view.findViewById(R.id.new_password);
        registration= view.findViewById(R.id.registration);
        new_hintmessage = view.findViewById(R.id.new_hintmessage);
        validatePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(account_name.getText().toString().trim())) {
                    if (account_name.getText().toString().trim().length() == 11) {
                        phoneNumber = account_name.getText().toString().trim();
                        verificationCode = (int) (Math.random() * 9000 + 1000) + "";
                        SMSender smSender = new SMSender(phoneNumber, verificationCode, DictionaryUtil.dictionary.get(DictionaryUtil.APP_KEY), DictionaryUtil.dictionary.get(DictionaryUtil.APP_SECRET));
                        Thread thread = new Thread(smSender);
                        thread.start();
                         newVerificationCode.requestFocus();
                        validatePhone.setVisibility(View.GONE);
                        reminderText();
                    } else {
                        Toast.makeText(getActivity(), "请输入完整电话号码", Toast.LENGTH_LONG).show();
                        account_name.requestFocus();
                    }
                } else {
                    Toast.makeText(getActivity(), "请输入您的电话号码", Toast.LENGTH_LONG).show();
                    account_name.requestFocus();
                }
            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(newPassword.getText().toString().trim()))
                {
                    Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_LONG).show();
                    newPassword.requestFocus();
                }
                if(TextUtils.isEmpty(newPasswordAgain.getText().toString().trim()))
                {
                    Toast.makeText(getActivity(), "请再次输入密码", Toast.LENGTH_LONG).show();
                    newPasswordAgain.requestFocus();
                }

                if(!newPassword.getText().toString().trim().equals(newPasswordAgain.getText().toString().trim()))
                {
                    Toast.makeText(getActivity(), "两次密码输入不一致", Toast.LENGTH_LONG).show();
                    newPasswordAgain.requestFocus();
                }
                if (!TextUtils.isEmpty(newVerificationCode.getText().toString().trim())) {
                    if (newVerificationCode.getText().toString().trim().length() == 4) {
                        if (verificationCode.equals(newVerificationCode.getText().toString().trim())) {
                            Toast.makeText(getActivity(), "验证码校验成功", Toast.LENGTH_SHORT).show();
                            saveUserInfo(phoneNumber,newPassword.getText().toString().trim());
                        } else {
                            Toast.makeText(getActivity(), "验证码错误", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "请输入完整验证码", Toast.LENGTH_LONG).show();
                        newVerificationCode.requestFocus();
                    }
                } else {
                    Toast.makeText(getActivity(), "请输入验证码", Toast.LENGTH_LONG).show();
                    newVerificationCode.requestFocus();
                }
            }
        });
    }


    //验证码送成功后提示文字
    private void reminderText() {
        new_hintmessage.setVisibility(View.VISIBLE);
        handlerText.sendEmptyMessageDelayed(1, 1000);
    }

    Handler handlerText = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (time > 0) {
                    new_hintmessage.setText("验证码已发送" + time + "秒");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                } else {
                    new_hintmessage.setText("提示信息");
                    time = 60;
                    new_hintmessage.setVisibility(View.GONE);
                    validatePhone.setVisibility(View.VISIBLE);
                }
            } else {
                newVerificationCode.setText("");
                new_hintmessage.setText("提示信息");
                time = 60;
                new_hintmessage.setVisibility(View.GONE);
                validatePhone.setVisibility(View.VISIBLE);
            }
        }
    };


    //TODO To query before save
    public void saveUserInfo(String phoneNumber, String password)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phoneNumber", phoneNumber);
        map.put("password", password);
        JSONObject params = new JSONObject(map);
        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if(status.equals(DictionaryUtil.SUCCESS))
                            {
                                Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity(), ContentActivity.class);
                                startActivity(intent);
                            }else
                            {
                                Toast.makeText(getActivity(), "账号已存在", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "无法注册", Toast.LENGTH_LONG).show();
                            return;
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "无法注册", Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(objRequest);
    }

}
