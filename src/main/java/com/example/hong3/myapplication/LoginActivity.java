package com.example.hong3.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jeongyeon on 2016-11-14.
 */

public class LoginActivity extends AppCompatActivity{
    Button b;
    EditText user_name;
    EditText user_pw;
    String url_text;
    RequestParams params=new RequestParams();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
         b=(Button)findViewById(R.id.login_button);
          user_name=(EditText)findViewById(R.id.userNameText);
          user_pw=(EditText)findViewById(R.id.userPwText);

        b.setOnClickListener(new View.OnClickListener() {
            String user_id="";
            String user_password="";
            @Override
            public void onClick(View v) {
                user_id=user_name.getText().toString();
                user_password=user_pw.getText().toString();
                params.put("m_id",user_id);
                params.put("password",user_password);
                url_text="/member/login";
                MyClient.post(url_text,params,new JsonHttpResponseHandler (){

                    public void onSuccess(int statusCode, Header[] headers,JSONObject object) {
                        String result="";
                        try {
                            result = object.getString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(LoginActivity.this,result,Toast.LENGTH_SHORT).show();
                        if(result.equals("success")) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject object) {
                        Toast.makeText(LoginActivity.this,"fail2",Toast.LENGTH_SHORT).show();
                       // Toast.makeText(LoginActivity.this, responseString, Toast.LENGTH_LONG).show();
                        //super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                });

            }
        });




    }

}
