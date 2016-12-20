package com.example.hong3.viewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.hong3.model.UpAudio;
import com.example.hong3.myapplication.MyClient;
import com.example.hong3.myapplication.MyinfoViewAcitivty;
import com.example.hong3.myapplication.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by OHBABY on 2016-12-20.
 */
public class UplistAdapter extends BaseAdapter {

    private ArrayList<UpAudio> uplist = new ArrayList<>();

    public UplistAdapter(ArrayList<UpAudio> uplist) {
        this.uplist = uplist;
    }

    @Override
    public int getCount() {
        return uplist.size();
    }

    @Override
    public Object getItem(int position) {
        return uplist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mysong_list, parent, false);
        }

        TextView songNm = (TextView) convertView.findViewById(R.id.songNm);
        TextView score = (TextView) convertView.findViewById(R.id.score);
        UpAudio up = uplist.get(position);
        songNm.setText(up.getS_name());
        score.setText(up.getScore()+"점");

        return convertView;
    }

}
