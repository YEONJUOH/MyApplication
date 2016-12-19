package com.example.hong3.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hong3.model.Reply;
import com.example.hong3.viewAdapter.ReplylistAdapter;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by OHBABY on 2016-12-10.
 */
public class AudioInfoActivity extends AppCompatActivity {

    String a_id;
    //id는 uploader
    String id;
    //m_id는 지금 사용자
    String m_id;
    Bitmap bmImg;
    ImageView imageView;
    TextView m_idView;
    TextView a_commentView;

    RequestParams params_a = new RequestParams();
    RequestParams params_m = new RequestParams();
    RequestParams params_rply = new RequestParams();
    RequestParams param_score = new RequestParams();


    MediaPlayer mediaPlayer = new MediaPlayer();

    Button playBtn;
    Button stopBtn;
    Button regiesterRplyBtn;

    EditText writeRply;

    ArrayList<Reply> replyList = new ArrayList<Reply>();
    ReplylistAdapter replylistAdapter;
    ListView replyLayout;

    Spinner score_spin;
    int selected_score;
    //layout
    LinearLayout score_layout;

    Button score_btn;
    TextView score_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audioinfo_page);

        //추가 될 text
        score_txt = new TextView(this);

       /* Intent intent = getIntent();
        a_id = intent.getExtras().getString("a_id");
        id = intent.getExtras().getString("m_id");*/

        a_id = "26";
        m_id = "uptest";

        imageView = (ImageView) findViewById(R.id.mPic_audioinfo);
        m_idView = (TextView) findViewById(R.id.m_idVeiw);
        a_commentView = (TextView) findViewById(R.id.a_commentView);

        replyLayout = (ListView) findViewById(R.id.replyLayout);
        writeRply = (EditText) findViewById(R.id.writeRply);

        score_layout= (LinearLayout) findViewById(R.id.score_layout);

        //정보 가져와서 뿌려주는 부분;
        audioInfo(a_id);

        //spinner

        score_spin = (Spinner)findViewById(R.id.score_spinner);
        score_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                      selected_score =  scoreToInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        score_btn = (Button) findViewById(R.id.score_btn);
        score_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               inputScore();
                //score_layout.removeAllViews();
            }
        });

        playBtn = (Button) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mediaPlayer.start();


            }
        });


        stopBtn = (Button) findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                mediaPlayer.stop();
            }
        });

        regiesterRplyBtn = (Button) findViewById(R.id.registerRplyBtn);
        regiesterRplyBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String content = writeRply.getText().toString();

                  createRply(content);
            }
        });


    }




    public void audioInfo(final String a_id){

        String url_text = "/audio/audio_info";
        params_a.put("a_id",a_id);
        MyClient.get(url_text,params_a,new JsonHttpResponseHandler(){

            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {


                String uploader="" ;
                try {

                    uploader = object.getString("m_id");
                    memberInfo(uploader);

                    a_commentView.setText(object.getString("a_comment"));
                    Uri uri = Uri.parse(MyClient.getBasicUrl()+"/audio/"+a_id+"."+getType(object.getString("a_loc")));

                    mediaPlayer.setDataSource(String.valueOf(uri));
                    mediaPlayer.prepare();

                    makeRplyList();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject object) {
                Toast.makeText(AudioInfoActivity.this,"fail",Toast.LENGTH_SHORT).show();

            }


        });

    }

    //member 정보 기재
     public void memberInfo(String uploader){

         id = uploader;
         String url_text = "/member/memberInfo";
         params_m.put("m_id",id);
         MyClient.post(url_text,params_m,new JsonHttpResponseHandler(){

             public void onSuccess(int statusCode, Header[] headers, JSONObject object) {


                 String imgUrl="" ;
                 try {

                     imgUrl =MyClient.getBasicUrl()+"/image/"+ id + "."+getType(object.getString("m_loc"));
                     //  imgDown.execute(imgUrl);
                     UrlImageViewHelper.setUrlDrawable(imageView, imgUrl) ;
                     m_idView.setText(id);
                     // Toast.makeText(AudioInfoActivity.this,mid,Toast.LENGTH_SHORT).show();

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }


             }

             @Override
             public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject object) {
                 Toast.makeText(AudioInfoActivity.this,"fail",Toast.LENGTH_SHORT).show();

             }


         });



     }

    //jsonarraytest
    public void makeRplyList(){

        String url_text = "/reply/replyList";
        //params_a.put("a_id",a_id);
        MyClient.get(url_text,params_a,new JsonHttpResponseHandler(){

            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {

                try {
                    jsontoArr(jsonArray);
                    replylistAdapter = new ReplylistAdapter(replyList);
                    replyLayout.setAdapter(replylistAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject object) {
                Toast.makeText(AudioInfoActivity.this,"fail",Toast.LENGTH_SHORT).show();

            }


        });

    }

    //score
    public void inputScore(){


        String url_text = "/audio/score_audio";
        param_score.put("m_id",m_id);
        param_score.put("a_id",a_id);
        param_score.put("score",selected_score);
        MyClient.post(url_text,param_score,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {

                score_layout.removeAllViews();

                score_txt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                score_txt.setText(selected_score+"점을 주었습니다");
                score_txt.setPadding(0,15,0,15);
                score_layout.addView(score_txt);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject object) {


            }


        });


    }

    //댓글 추가
    public void createRply(final String content){

        String url_text = "/reply/createRply";
        params_rply.put("a_id",a_id);
        params_rply.put("m_id",m_id);
        params_rply.put("content",content);

        MyClient.post(url_text,params_rply,new JsonHttpResponseHandler(){

            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {


                remakeRplyList();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject object) {
                Toast.makeText(AudioInfoActivity.this,"fail",Toast.LENGTH_SHORT).show();

            }


        });


    };




    // 추가시
    public void remakeRplyList(){

        String url_text = "/reply/replyList";
        params_a.put("a_id",a_id);
        MyClient.get(url_text,params_a,new JsonHttpResponseHandler(){

            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {

                try {

                    replyList.clear();
                    jsontoArr(jsonArray);
                    replylistAdapter.update(replyList);
                    writeRply.setText("");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject object) {
                Toast.makeText(AudioInfoActivity.this,"fail",Toast.LENGTH_SHORT).show();

            }


        });

    }

    //jsonarray to array
    public void jsontoArr(JSONArray jsonArr) throws JSONException {


            if(jsonArr != null){

                for(int i =0;i<jsonArr.length();i++){

                    JSONObject obj = jsonArr.getJSONObject(i);
                    Reply reply = new Reply(obj.getString("m_id"),obj.getString("content"));
                    replyList.add(reply);


                }

            }


    }

    //img type 알아내는 함수
    public String getType(String img_loc){

        int lastIndex = img_loc.lastIndexOf(".");
        String Type = img_loc.substring(lastIndex+1,img_loc.length());

        return Type;

    }

    //점수 int로
    public int scoreToInt(String score_str){

        String score = score_str.substring(0,1);
        return Integer.parseInt(score);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        // return super.onCreateOptionsMenu(menu);
        return true;
    }

}

