package com.example.hong3.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    // ArrayAdapter<String> adapter;
    CustomArrayAdapter c_adapter;
    ListView listView;
    TextView t_rank;
    TextView t_title;
    TextView t_score;
    TextView t_name;
    TextView t_mission;

    public static ArrayList<Music> list_item = new ArrayList<Music>();
    ArrayAdapter spinnerAdapter;
    Spinner spinner;
    String url_text = "";
    RequestParams params = new RequestParams();
    String s_loc, up_id, score, a_id, nm,m_loc;
    Music m;
    String m_id;
    String s_id;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        m_id = intent.getExtras().getString("m_id");

        s_id ="5";

        listView = (ListView) findViewById(R.id.MainlistView);
        spinner = (Spinner) findViewById(R.id.list_spinner);

        c_adapter = new CustomArrayAdapter(this, list_item);

        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.optionarray, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // spinner.setAdapter(spinnerAdapter);
        listView.setAdapter(c_adapter);
        c_adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Music m = (Music) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, AudioInfoActivity.class);
                intent.putExtra("m_id", m_id);
                intent.putExtra("a_id", list_item.get(position).a_id);
                startActivity(intent);

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) { //day
                    url_text = "/audio/list_day";
                } else if (position == 1) {  //week
                    url_text = "/audio/list_week";
                } else if (position == 2) {   //month
                    url_text = "/audio/list_mon";
                }
                //Toast.makeText(getApplicationContext(),url_text,Toast.LENGTH_SHORT).show();

                MyClient.get(url_text, params, new JsonHttpResponseHandler() {
                    public void onSuccess(int statusCode, Header[] headers, JSONArray object) {
                        String result = "";
                        list_item.clear();
                        try {

                            for (int i = 0; i < object.length(); i++) {
                                //  s_loc=object.getJSONObject(i).getString("s_loc");
                                up_id = object.getJSONObject(i).getString("m_id");
                                score = object.getJSONObject(i).getString("score");
                                a_id = object.getJSONObject(i).getString("a_id");
                                nm = object.getJSONObject(i).getString("nm");
                                m_loc =  object.getJSONObject(i).getString("m_loc");

                                m = new Music(up_id, score,a_id,m_loc,up_id);
                                list_item.add(m);

                            }
                            c_adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (result.equals("success")) {

                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                        Toast.makeText(getApplicationContext(), "fail list", Toast.LENGTH_SHORT).show();
                    }

                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //todayMission 클릭 시
        t_mission = (TextView) findViewById(R.id.today_mission);
        t_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                intent.putExtra("m_id", m_id);
                intent.putExtra("s_id",s_id);
                startActivity(intent);


            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        // return super.onCreateOptionsMenu(menu);
        return true;
    }
    //http://loveiskey.tistory.com/176
    //http://dev-daddy.com/8
    //http://sumi3360.blogspot.kr/2015/07/android-action-item-icon.html

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        switch (i) {
            case R.id.mypage_menu:
                mypaging();
                return true;
            case R.id.home:
                tohome();
            case R.id.logout:
                logout();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void mypaging() {

        Intent intent = new Intent(MainActivity.this, MyInfoActivity.class);
        m_id = m_id;

        intent.putExtra("m_id", m_id);

        startActivity(intent);
    }

    public void tohome() {

        Intent intent = new Intent(MainActivity.this, MyInfoActivity.class);
         m_id = m_id;

        intent.putExtra("m_id", m_id);

        startActivity(intent);
    }

    public void logout() {

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.hong3.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.hong3.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
class Music{
    String name;
    String score;
    String a_id;
    String m_loc;
    String m_id;
    Bitmap image;

    public Music(String name,String score,String a_id,String m_loc,String m_id){
        this.name=name;
        this.score=score;
        this.a_id = a_id;
        this.m_loc = m_loc;
        this.m_id =m_id;
        //this.image=image;
    }
    public Music(){}

}

class CustomArrayAdapter extends BaseAdapter{

    private LayoutInflater inflater = null;
    private ArrayList<Music> client_list_item = new ArrayList<Music>();//각 item
    private Context mContext = null;
    private int layout=0;
    private Music mm;

    public CustomArrayAdapter(Activity a, ArrayList<Music> d){
        this.client_list_item=d;
        this.inflater=(LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void updateList(ArrayList<Music> newList){

        client_list_item = newList;
        this.notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return client_list_item.size();
    }

    @Override
    public Object getItem(int position) {
        return client_list_item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context tmp=parent.getContext();
        // LayoutInflater lay=(LayoutInflater)tmp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //convertView = inflater.inflate(R.layout.main_list_item,parent,false);
        convertView=this.inflater.inflate(R.layout.main_list_item,parent,false);
        //ImageView musicimage=(ImageView)convertView.findViewById(R.id.item_art);
        TextView name=(TextView)convertView.findViewById(R.id.main_item_title);
        TextView score=(TextView)convertView.findViewById(R.id.main_item_score);
        ImageView uploader_img = (ImageView) convertView.findViewById(R.id.item_art);

        //  musicimage.setImageBitmap(client_list_item.get(position).image);
        name.setText(this.client_list_item.get(position).name);
        score.setText(this.client_list_item.get(position).score+"점");

        String imgUrl ="";
        imgUrl =MyClient.getBasicUrl()+"/image/"+ this.client_list_item.get(position).m_id + "."+getType(this.client_list_item.get(position).m_loc);
        //  imgDown.execute(imgUrl);
        UrlImageViewHelper.setUrlDrawable(uploader_img, imgUrl) ;

        return convertView;
    }

    //img type 알아내는 함수
    public String getType(String img_loc){

        int lastIndex = img_loc.lastIndexOf(".");
        String Type = img_loc.substring(lastIndex+1,img_loc.length());

        return Type;

    }

}