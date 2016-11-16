package com.example.hong3.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hong3.model.Audio;
import com.example.hong3.viewAdapter.AudiolistAdapter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by OHBABY on 2016-10-29.
 */
public class UploadActivity extends AppCompatActivity {

    //Cursor cursor;
    String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
    String[] projection = {
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATE_MODIFIED,
            MediaStore.Audio.Media.DURATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_page);



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

                final ArrayList<Audio> audioList = mk_audioList();

                final AudiolistAdapter adapter = new AudiolistAdapter(audioList);
                alertBuilder.setAdapter(adapter, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                TextView output = (TextView) findViewById(R.id.textView10);
                                output.setText(audioList.get(id).getA_name());


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
    }


     private ArrayList<Audio>  mk_audioList() {

         ArrayList<Audio> audioList = new ArrayList<>();
         Cursor cursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI ,
                 projection, selection, null, null);
         cursor.moveToFirst();
             for(int i = 0; i < cursor.getCount(); i ++){
             Audio audio = new Audio();

                 audio.setA_name(cursor.getString(cursor.getColumnIndex("title")));
                 audio.setA_duration( convertSecondsToHMmSs(cursor.getLong(cursor.getColumnIndex("duration"))));
                 audio.setA_date(convertToDate(cursor.getLong(cursor.getColumnIndex("date_modified"))));


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        // return super.onCreateOptionsMenu(menu);
        return true;
    }



}
