package com.example.hong3.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by OHBABY on 2016-11-25.
 */
public class imgUpload extends AppCompatActivity {

    TextView pic_sel;
    Button pic_up_btn;
    final int REQ_CODE_SELECT_IMAGE = 100;
    String imgPath;
    RequestParams params = new RequestParams();

    String id;
    String pwd;
    String name;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img_upload);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        pwd = intent.getExtras().getString("pwd");
        name = intent.getExtras().getString("name");

        pic_sel = (TextView) findViewById(R.id.pic_sel);
        pic_up_btn = (Button) findViewById(R.id.pic_up_btn);

        //회원 가입 textview 클릭 시
        pic_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doTakeAlbumAction();
            }
        });

        //sign-up 버튼
        pic_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File imgFile = new File(imgPath);
                try {
                    params.put("file",imgFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                params.put("m_id",id);
                params.put("password",pwd);
                params.put("m_name",name);

               String url_text="/member/join";
                MyClient.post(url_text,params,new JsonHttpResponseHandler(){

                    public void onSuccess(int statusCode, Header[] headers, JSONObject object) {

                        Intent intent = new Intent(imgUpload.this, MainActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject object) {
                        Toast.makeText(imgUpload.this,"fail",Toast.LENGTH_SHORT).show();

                    }

                });


            }
        });


    }

    public void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
        // startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    imgPath = getImageToUri(data.getData());

                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ImageView image = (ImageView) findViewById(R.id.selectedImg);

                    //배치해놓은 ImageView에 set
                    image.setImageBitmap(image_bitmap);


                    //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String getImageToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
       // String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgPath;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        // return super.onCreateOptionsMenu(menu);
        return true;
    }


}