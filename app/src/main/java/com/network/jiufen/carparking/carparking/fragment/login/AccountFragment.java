package com.network.jiufen.carparking.carparking.fragment.login;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.network.jiufen.carparking.carparking.R;
import com.network.jiufen.carparking.carparking.activity.HomePageActivity;
import com.network.jiufen.carparking.carparking.util.MySingleton;
import com.network.jiufen.carparking.carparking.util.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.network.jiufen.carparking.carparking.util.HttpUtil.WEB_SERVICE_HOST;

public class AccountFragment extends Fragment {
    private String url = WEB_SERVICE_HOST+"/account/find";
    private EditText accountName;
    private EditText password;
    private Button validate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_account, container, false);
        accountName = view.findViewById(R.id.account_name);
        password = view.findViewById(R.id.password);
        validate = view.findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneNumber = accountName.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getActivity(), "请输入用户名", Toast.LENGTH_LONG).show();
                    accountName.requestFocus();
                } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_LONG).show();
                    password.requestFocus();
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("phoneNumber", phoneNumber);
                    JSONObject params = new JSONObject(map);
                    JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    String realPassword;
                                    try {
                                        realPassword = response.getString("password");
                                    } catch (JSONException e) {
                                        Toast.makeText(getActivity(), "账号不存在", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    if(realPassword.equals(password.getText().toString().trim()))
                                    {
                                        SharedPrefsUtil.putValue(getActivity().getApplicationContext(),"phone",phoneNumber);
                                        Intent intent = new Intent(getActivity(), HomePageActivity.class);
                                        intent.putExtra("phoneNumber",phoneNumber);
                                        getActivity().startActivity(intent);
                                        Toast.makeText(getActivity(), "密码正确", Toast.LENGTH_LONG).show();
                                        startActivity(intent);
                                    }else
                                    {
                                        Toast.makeText(getActivity(), "密码不正确", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "无法连接网络", Toast.LENGTH_LONG).show();
                        }
                    });
                    MySingleton.getInstance(getActivity()).addToRequestQueue(objRequest);
                }
            }
        });
        return view;
    }
}
