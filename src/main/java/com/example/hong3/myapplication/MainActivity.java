package com.example.hong3.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.MainlistView);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i=item.getItemId();
        switch (i){
            case R.id.mypage_menu:
                mypaging();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void mypaging(){
        Intent intent=new Intent(MainActivity.this,MyInfoActivity.class);
    }
}
