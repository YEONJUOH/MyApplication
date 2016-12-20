package com.example.hong3.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jeongyeon on 2016-11-16.
 */

public class MyInfoActivity extends AppCompatActivity{

    Button modi,mysong;
    TextView my_id,my_score,my_song_counter,my_name;
    String url_text="";
    RequestParams params=new RequestParams();
    String m_id;
    ImageView  myPageImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_page);

        modi=(Button)findViewById(R.id.button_modi);
        mysong=(Button)findViewById(R.id.button_mysong);

        my_id=(TextView)findViewById(R.id.my_id);
        my_score=(TextView)findViewById(R.id.my_score);
        my_song_counter=(TextView)findViewById(R.id.my_song_count);
        my_name=(TextView)findViewById(R.id.my_name);

        myPageImg = (ImageView) findViewById(R.id.myPageImg);

        Intent intent=getIntent();
        m_id=intent.getStringExtra("m_id");

        params.put("m_id",m_id);
        url_text="/member/memberInfo";

        MyClient.post(url_text,params,new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                String result="";
                String imgUrl="";
                try {
                    my_id.setText("User ID : "+object.getString("m_id"));
                    my_name.setText("Name : "+object.getString("m_name"));
                    my_score.setText("Song : "+object.getString("audio_nm"));
                    String avg="";
                    if(object.getString("score_avg").equals("null")){
                        avg = "0";
                    }else{

                        avg = object.getString("score_avg");
                    }

                    my_song_counter.setText("Score 평균 : "+avg);

                    imgUrl =MyClient.getBasicUrl()+"/image/"+m_id + "."+getType(object.getString("m_loc"));
                    //  imgDown.execute(imgUrl);
                    UrlImageViewHelper.setUrlDrawable(myPageImg, imgUrl) ;


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(result.equals("success")) {

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                Toast.makeText(getApplicationContext(),"m_id"+m_id,Toast.LENGTH_SHORT).show();
            }

        });

        modi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MyInfoActivity.this, MyinfoViewAcitivty.class);
                i.putExtra("m_id",m_id);
                startActivity(i);
            }
        });
        mysong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MyInfoActivity.this, mysonglistActivity.class);
                i.putExtra("m_id",m_id);
                startActivity(i);
            }
        });

    }


    //img type 알아내는 함수
    public String getType(String img_loc){

        int lastIndex = img_loc.lastIndexOf(".");
        String Type = img_loc.substring(lastIndex+1,img_loc.length());

        return Type;
    }

}
