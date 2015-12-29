package com.garbergames.messageme.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.garbergames.messageme.R;
import com.garbergames.messageme.nonActivityClasses.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matga_000 on 12/18/2015.
 */
public class UserAdapter extends BaseAdapter {

    private ArrayList<String> mUsername;
    private ArrayList<String> mName;
    private ArrayList<String> mEmail;
    Activity mContext;

    public UserAdapter(ArrayList<String> username, ArrayList<String> name, ArrayList<String> email, Activity activity){
        mContext = activity;
        this.mUsername = username;
        this.mName = name;
        this.mEmail = email;
    }

    @Override
    public int getCount() {
        return this.mUsername.size();
    }

    @Override
    public Object getItem(int position) {
        return new User(mName.get(position), mUsername.get(position), mEmail.get(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = mContext.getLayoutInflater().inflate(R.layout.listitem_person, null, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.textview_name);
        TextView username = (TextView) convertView.findViewById(R.id.textview_username);
        TextView email = (TextView) convertView.findViewById(R.id.textview_email);

        name.setText(mName.get(position));
        email.setText(mEmail.get(position));
        username.setText(mUsername.get(position));

        return convertView;
    }
}
