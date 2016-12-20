package com.example.hong3.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by jeongyeon on 2016-11-14.
 */

public class JoinActivity extends Activity {

    Button btnToimg ;
    EditText inputId;
    EditText inputPwd;
    EditText inputName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_page);

        btnToimg = (Button)findViewById(R.id.btnToimg);
        inputId = (EditText)findViewById(R.id.inputId);
        inputPwd = (EditText)findViewById(R.id.inputPwd);
        inputName = (EditText)findViewById(R.id.inputName);

        btnToimg.setOnClickListener(new View.OnClickListener() {

            String id = "";
            String pwd = "";
            String name = "";

            @Override
            public void onClick(View v) {

                id = inputId.getText().toString();
                pwd = inputPwd.getText().toString();
                name = inputName.getText().toString();


                Intent intent = new Intent(JoinActivity.this, imgUpload.class);
                intent.putExtra("id",id);
                intent.putExtra("pwd",pwd);
                intent.putExtra("name",name);
                startActivity(intent);


            }

        });


    }


}
