package com.example.hong3.viewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hong3.model.Reply;
import com.example.hong3.myapplication.R;

import java.util.ArrayList;

/**
 * Created by OHBABY on 2016-12-17.
 */
public class ReplylistAdapter extends BaseAdapter {

    private ArrayList<Reply> replyList = new ArrayList<>();



    public ReplylistAdapter(ArrayList<Reply> replyList) {
        this.replyList = replyList;
    }

    //update
    public void update(ArrayList<Reply> newList){


        replyList=newList;
        this.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return replyList.size();
    }

    @Override
    public Object getItem(int position) {
        return replyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reply_adapter, parent, false);
        }

        TextView rlpy_writer = (TextView) convertView.findViewById(R.id.rply_writer);
        TextView rply_content = (TextView) convertView.findViewById(R.id.rply_content);

        Reply reply = replyList.get(position);
        rlpy_writer.setText(reply.getM_id());
        rply_content.setText(reply.getContent());

        return convertView;
    }

    public void setReplyList(ArrayList<Reply> replyList) {
        this.replyList = replyList;
    }
}
