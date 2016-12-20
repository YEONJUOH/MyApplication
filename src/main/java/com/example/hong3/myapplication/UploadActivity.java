package com.example.hong3.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hong3.model.Audio;
import com.example.hong3.viewAdapter.AudiolistAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

import static java.lang.System.*;

/**
 * Created by OHBABY on 2016-10-29.
 */
public class UploadActivity extends AppCompatActivity {

    //Cursor cursor;
    String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
    String[] projection = {
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATE_MODIFIED,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA

    };


   ArrayList<Audio> audioList = new ArrayList<Audio>();
   //선택된 오디오의 index
   int selectedAudio;
   EditText upload_comment;

     String m_id;
     String s_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_page);


        Intent intent = getIntent();
        m_id = intent.getExtras().getString("m_id");
        s_id = intent.getExtras().getString("s_id");


         upload_comment = (EditText)findViewById(R.id.upload_comment);
    /*    cursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI ,
                projection, selection, null, null);*/


        Button upload_btn = (Button) findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(new Button.OnClickListener(){


            @Override
            public void onClick(View view){

               /* ArrayList<Audio> audioList = mk_audioList();
               TextView output = (TextView) findViewById(R.id.output);
                *//*if(cursor == null) {
                    output.setText("바보");
                }*//*

                output.setText(audioList.get(0).getA_duration()+"노" +audioList.get(0).getA_date());*/

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
                        UploadActivity.this);

                alertBuilder.setIcon(R.drawable.microphone);
                alertBuilder.setTitle("항목중에 하나를 선택하세요.");

                audioList = mk_audioList();

                final AudiolistAdapter adapter = new AudiolistAdapter(audioList);
                alertBuilder.setAdapter(adapter, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                TextView output = (TextView) findViewById(R.id.textView10);
                                output.setText(audioList.get(id).getA_name());
                                selectedAudio = id;



                            };
                }

                );

                // 버튼 생성
                alertBuilder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });


             alertBuilder.show();

            }
        });

    //확인 버튼 눌렀을 때
        Button upload_check = (Button) findViewById(R.id.upload_check);
        upload_check.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadAudio(audioList.get(selectedAudio).getPath());
                Intent intent = new Intent(UploadActivity.this, AudioInfoActivity.class);

            }
        });


    }


     private ArrayList<Audio>  mk_audioList() {

         ArrayList<Audio> audioList = new ArrayList<>();
      /*   Cursor cursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI ,
                 projection, selection, null, null);*/
         ContentResolver resolver = this.getContentResolver();
         Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI ,
                 projection, selection, null,null);


         cursor.moveToFirst();
             for(int i = 0; i < cursor.getCount(); i ++){
             Audio audio = new Audio();

                 audio.setA_name(cursor.getString(cursor.getColumnIndex("title")));
                 audio.setA_duration( convertSecondsToHMmSs(cursor.getLong(cursor.getColumnIndex("duration"))));
                 audio.setA_date(convertToDate(cursor.getLong(cursor.getColumnIndex("date_modified"))));
                 //3 is data
                 audio.setPath(cursor.getString(3));


                 audioList.add(audio);

                cursor.moveToNext();
         }
         cursor.close();

             return audioList;
     }


    private String convertSecondsToHMmSs(Long millis) {

        //long millis = Long.parseLong(ms) ;

        String hm = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));

        return hm;

    }

    private String convertToDate(long tm) {

        String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (tm*1000));
        return date;



    }

    private void uploadAudio(String path){

        RequestParams params = new RequestParams();

        try {
            params.put("file",new File(path));


        } catch (FileNotFoundException e) {
            Toast.makeText(UploadActivity.this,"not found",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        //나중에 변경
        params.put("m_id",m_id);
        params.put("s_id",5);
        //선택된 오디오의 제목
        String a_name = audioList.get(selectedAudio).getA_name();
        params.put("a_name",a_name);
        //오디오 코멘트
        String a_comment = upload_comment.getText().toString();
        params.put("a_comment",a_comment);



        String url_text="/audio/upload";
        MyClient.post(url_text,params,new JsonHttpResponseHandler(){

            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {

                Intent intent = new Intent(UploadActivity.this, AudioInfoActivity.class);
                String a_id="";
                try {

                     a_id = object.getString("a_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra("a_id",a_id);
                //나중에 변경
                intent.putExtra("m_id",m_id);
                startActivity(intent);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject object) {
                Toast.makeText(UploadActivity.this,"fail",Toast.LENGTH_SHORT).show();

            }

        });


    }

    public byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int read = 0;
        byte[] buffer = new byte[1024];
        while (read != -1) {
            read = in.read(buffer);
            if (read != -1)
                out.write(buffer,0,read);
        }
        out.close();
        return out.toByteArray();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        // return super.onCreateOptionsMenu(menu);
        return true;
    }



}
