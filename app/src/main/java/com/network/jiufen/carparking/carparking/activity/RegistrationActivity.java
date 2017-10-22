package com.network.jiufen.carparking.carparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.util.DictionaryUtil;
import com.network.jiufen.carparking.carparking.util.MySingleton;
import com.network.jiufen.carparking.carparking.util.SMSender;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.network.jiufen.carparking.carparking.util.HttpUtil.WEB_SERVICE_HOST;

public class RegistrationActivity extends AppCompatActivity {
    private String url = WEB_SERVICE_HOST + "/account/save";
    private int time = 60;
//    private String verificationCode;

    @BindView(R.id.account_name)
    EditText accountNameText;
    @BindView(R.id.regist)
    Button registButton;
    @BindView(R.id.getcord)
    Button getcordText;
    @BindView(R.id.new_password_again)
    EditText newPasswordAgainText;
    @BindView(R.id.new_password)
    EditText newPasswordText;
    @BindView(R.id.cord)
    EditText verificationCode;
    @BindView(R.id.hintmessage)
    TextView hintmessage;

    private String phoneNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);


//        init();
    }


//    private void init() {
//        getcordText.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                String accountName = accountNameText.getText().toString().trim();
//
//                if (!TextUtils.isEmpty(accountName)) {
//                    if (accountName.length() == 11) {
//                        verificationCode = (int) (Math.random() * 9000 + 1000) + "";
//                        SMSender smSender = new SMSender(accountName, verificationCode, DictionaryUtil.dictionary.get(DictionaryUtil.APP_KEY), DictionaryUtil.dictionary.get(DictionaryUtil.APP_SECRET));
//                        Thread thread = new Thread(smSender);
//                        thread.start();
//                        newVerificationCode.requestFocus();
//                        validatePhone.setVisibility(View.GONE);
//                        reminderText();
//                    } else {
//                        Toast.makeText(RegistrationActivity.this, "请输入完整电话号码", Toast.LENGTH_LONG).show();
//                        accountNameText.requestFocus();
//                    }
//                } else {
//                    Toast.makeText(RegistrationActivity.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
//                    accountNameText.requestFocus();
//                }
//            }
//        });
//        registButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String newPassword = newPasswordText.getText().toString().trim();
//                String newPasswordAgain = newPasswordAgainText.getText().toString().trim();
//
//                if (TextUtils.isEmpty(newPassword)) {
//                    Toast.makeText(RegistrationActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
//                    newPasswordText.requestFocus();
//                }
//                if (TextUtils.isEmpty(newPasswordAgain)) {
//                    Toast.makeText(RegistrationActivity.this, "请再次输入密码", Toast.LENGTH_LONG).show();
//                    newPasswordAgainText.requestFocus();
//                }
//
//                if (!newPassword.equals(newPasswordAgain)) {
//                    Toast.makeText(RegistrationActivity.this, "两次密码输入不一致", Toast.LENGTH_LONG).show();
//                    newPasswordAgainText.requestFocus();
//                }
//                if (!TextUtils.isEmpty(newVerificationCode.getText().toString().trim())) {
//                    if (newVerificationCode.getText().toString().trim().length() == 4) {
//                        if (verificationCode.equals(newVerificationCode.getText().toString().trim())) {
//                            Toast.makeText(RegistrationActivity.this, "验证码校验成功", Toast.LENGTH_SHORT).show();
//                            saveUserInfo(phoneNumber, newPassword.getText().toString().trim());
//                        } else {
//                            Toast.makeText(RegistrationActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(RegistrationActivity.this, "请输入完整验证码", Toast.LENGTH_LONG).show();
//                        newVerificationCode.requestFocus();
//                    }
//                } else {
//                    Toast.makeText(RegistrationActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();
//                    newVerificationCode.requestFocus();
//                }
//            }
//        });
//    }
//
//
//    //验证码送成功后提示文字
//    private void reminderText() {
//        new_hintmessage.setVisibility(View.VISIBLE);
//        handlerText.sendEmptyMessageDelayed(1, 1000);
//    }
//
//    Handler handlerText = new Handler() {
//        public void handleMessage(Message msg) {
//            if (msg.what == 1) {
//                if (time > 0) {
//                    new_hintmessage.setText("验证码已发送" + time + "秒");
//                    time--;
//                    handlerText.sendEmptyMessageDelayed(1, 1000);
//                } else {
//                    new_hintmessage.setText("提示信息");
//                    time = 60;
//                    new_hintmessage.setVisibility(View.GONE);
//                    validatePhone.setVisibility(View.VISIBLE);
//                }
//            } else {
//                newVerificationCode.setText("");
//                new_hintmessage.setText("提示信息");
//                time = 60;
//                new_hintmessage.setVisibility(View.GONE);
//                validatePhone.setVisibility(View.VISIBLE);
//            }
//        }
//    };


    //TODO To query before save
    public void saveUserInfo(String phoneNumber, String password) {
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
                            if (status.equals(DictionaryUtil.SUCCESS)) {
                                Toast.makeText(RegistrationActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegistrationActivity.this, HomePageActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegistrationActivity.this, "账号已存在", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(RegistrationActivity.this, "无法注册", Toast.LENGTH_LONG).show();
                            return;
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrationActivity.this, "无法注册", Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(objRequest);
    }

}
