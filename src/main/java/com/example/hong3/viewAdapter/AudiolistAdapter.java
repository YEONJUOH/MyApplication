package com.example.hong3.viewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hong3.model.Audio;
import com.example.hong3.myapplication.R;

import java.util.ArrayList;

/**
 * Created by OHBABY on 2016-11-02.
 */
public class AudiolistAdapter extends BaseAdapter {

   private ArrayList<Audio> audioList = new ArrayList<Audio>();

    public AudiolistAdapter(ArrayList<Audio> audioList){
        this.audioList = audioList;
    }

    @Override
    public int getCount() {
        return audioList.size();
    }

    @Override
    public Object getItem(int position) {
        return audioList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.audio_dialog, parent, false);
        }

        TextView audio_name = (TextView) convertView.findViewById(R.id.audio_name);
        TextView audio_duration =(TextView) convertView.findViewById(R.id.audio_duration);
        TextView audio_date = (TextView) convertView.findViewById(R.id.audio_date);

        Audio audio = audioList.get(position);

        audio_name.setText(audio.getA_name());
        audio_date.setText(audio.getA_date());
        audio_duration.setText(audio.getA_duration());

        return convertView;
    }
}
