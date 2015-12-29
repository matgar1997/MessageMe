package com.garbergames.messageme.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.garbergames.messageme.R;
import com.garbergames.messageme.nonActivityClasses.Conversation;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by matga_000 on 12/25/2015.
 */
public class ConversationAdapter extends BaseAdapter {

    private ArrayList<Conversation> mConversationList;
    private Activity mActivity;

    public ConversationAdapter(Activity activity, ArrayList<Conversation> conversations){
        this.mActivity = activity;
        this.mConversationList = conversations;
    }

    @Override
    public int getCount() {
        return mConversationList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mConversationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.listitem_conversation, null, false);
        }

        TextView personOneLabel = (TextView) convertView.findViewById(R.id.textview_username_one);
        TextView personTwoLabel = (TextView) convertView.findViewById(R.id.textview_username_two);

        personOneLabel.setText(mConversationList.get(position).getPersonOne().getUsername());
        personTwoLabel.setText(mConversationList.get(position).getPersonTwo().getUsername());

        return convertView;
    }
}
