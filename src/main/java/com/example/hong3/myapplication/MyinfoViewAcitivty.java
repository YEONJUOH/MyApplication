package com.example.hong3.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jeongyeon on 2016-12-17.
 */

public class MyinfoViewAcitivty extends AppCompatActivity{


    EditText myinfo_id,myinfo_password,myinfo_name,myinfo_password2;
    RequestParams params=new RequestParams();
    String m_id;
    String url_text="";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo_page);

        myinfo_id=(EditText)findViewById(R.id.editText_ID);
        myinfo_name=(EditText)findViewById(R.id.editText_name);
        myinfo_password=(EditText)findViewById(R.id.editText_password);
        myinfo_password2=(EditText)findViewById(R.id.editText_password2);

        Intent intent=getIntent();
          m_id=intent.getStringExtra("m_id");

        params.put("m_id",m_id);
        url_text="/member/memberInfo";

        MyClient.post(url_text,params,new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                String result="";
                try {
                    myinfo_id.setText(object.getString("m_id"));
                   myinfo_name.setText(object.getString("m_name"));
                  // myinfo_password.setText(object.getString("audio_nm"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(result.equals("success")) {

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
            }

        });
    }


}
