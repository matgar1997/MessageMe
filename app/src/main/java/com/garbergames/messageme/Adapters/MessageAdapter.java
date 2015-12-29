package com.garbergames.messageme.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.garbergames.messageme.R;
import com.garbergames.messageme.nonActivityClasses.Message;

import java.util.ArrayList;

/**
 * Created by matga_000 on 12/18/2015.
 */
public class MessageAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<Message> mMessageList;

    public MessageAdapter(Activity activity, ArrayList<Message> messages){
        this.mMessageList = messages;
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        //return the size of that newly created arraylist
        return mMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        // return that newly created object
        return this.mMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        //ignore this method, it never gets called
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.listitem_meesage, null, false);
        }

        TextView fromUsername = (TextView) convertView.findViewById(R.id.textview_sent_from);
        TextView message = (TextView) convertView.findViewById(R.id.textview_message);

        fromUsername.setText(mMessageList.get(position).getName());
        message.setText(mMessageList.get(position).getMessage());

        return convertView;
    }
}
