package com.example.hong3.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hong3.model.Reply;
import com.example.hong3.model.UpAudio;
import com.example.hong3.viewAdapter.UplistAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jeongyeon on 2016-12-17.
 */

public class mysonglistActivity extends AppCompatActivity {

    CustomArrayAdapter c_adapter;
    ListView my_music_list;
    ArrayList<UpAudio> upList;
    RequestParams params = new RequestParams();
    String m_id;
    String s_loc,s_id,score,s_name,a_id;

    UplistAdapter uplistAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_music_list);

        Intent intent=getIntent();
        m_id=intent.getStringExtra("m_id");

        my_music_list = (ListView) findViewById(R.id.my_music);

        upList = new ArrayList<UpAudio>();
        // c_adapter = new CustomArrayAdapter(this,upList);

       // my_music_list.setAdapter(c_adapter);
       // c_adapter.notifyDataSetChanged();

        String url_text ="/audio/uploadedAudio";
        params.put("m_id",m_id);
        MyClient.get(url_text, params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray object) {
                try {
                    jsontoArr(object);
                    uplistAdapter= new UplistAdapter(upList);
                    my_music_list.setAdapter(uplistAdapter);

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                Toast.makeText(getApplicationContext(), "fail list", Toast.LENGTH_SHORT).show();
            }

        });

/*        my_music_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UpAudio selA = upList.get(position);
                deleteAudio(selA.getA_id());

            }
        });*/


    }

    // 삭제
    public void deleteAudio(String a_id){

        String url_text = "/audio/delete_audio";
        params.put("a_id",a_id);
        MyClient.get(url_text,params,new JsonHttpResponseHandler(){

            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Intent i=new Intent(mysonglistActivity.this, MyinfoViewAcitivty.class);
                i.putExtra("m_id",m_id);
                startActivity(i);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject object) {


            }


        });

    }


    //jsonarray to array
    public void jsontoArr(JSONArray jsonArr) throws JSONException {


        if(jsonArr != null){

            for(int i =0;i<jsonArr.length();i++){

                JSONObject obj = jsonArr.getJSONObject(i);
              //String s_loc, String s_id, String score, String s_name, String a_id
                UpAudio upAudio = new UpAudio(obj.getString("s_loc"),obj.getString("s_id"),obj.getString("score"),obj.getString("s_name"),obj.getString("a_id"));
                upList.add(upAudio);


            }

        }


    }

}



