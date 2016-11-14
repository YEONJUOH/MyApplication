package com.example.hong3.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
       // return super.onCreateOptionsMenu(menu);
        return true;
    }
    //http://loveiskey.tistory.com/176
    //http://dev-daddy.com/8
    //http://sumi3360.blogspot.kr/2015/07/android-action-item-icon.html
}
