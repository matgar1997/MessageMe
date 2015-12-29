package com.garbergames.messageme.activityClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.garbergames.messageme.Adapters.ConversationAdapter;
import com.garbergames.messageme.R;
import com.garbergames.messageme.nonActivityClasses.Conversation;
import com.garbergames.messageme.utils.Keys;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SelectChatActivity extends AppCompatActivity {

    private TextView mTitle;
    private ListView mList;
    private ArrayList<Conversation> mConversations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chat);

        getSupportActionBar().hide();

        this.mList = (ListView) findViewById(R.id.listview_users);
        this.mTitle = (TextView) findViewById(R.id.textview_conversation);

        mConversations = new ArrayList<Conversation>();
        final ConversationAdapter conversationAdapter = new ConversationAdapter(this, mConversations);
        mList.setAdapter(conversationAdapter);

        ParseQuery<ParseObject> query = new ParseQuery(Keys.CONVERSATION_OBJ);
        query.include(Keys.CREATED_BY);
        query.include(Keys.CREATED_FOR);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.d("Amount of convos", "" + objects.size()); //Pass gets all ParseObjects "Conversation"
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        ParseUser personOne = objects.get(i).getParseUser(Keys.CREATED_BY);
                        ParseUser personTwo = objects.get(i).getParseUser(Keys.CREATED_FOR);
                        String id = objects.get(i).getObjectId();

                        if (personOne.getObjectId().equals(ParseUser.getCurrentUser().getObjectId()) ||
                                personTwo.getObjectId().equals(ParseUser.getCurrentUser().getObjectId()))
                            mConversations.add(new Conversation(personOne, personTwo, id));
                    }
                    Log.d("Amount of convos", "" + mConversations.size()); //Pass only adds where user is involved
                    conversationAdapter.notifyDataSetChanged(); // this soltion works
                    if(mConversations.size() == 0){
                        mTitle.setText("Start Conversation w/ People Search");
                    } else {
                        mTitle.setText("Join a conversation");
                    }
                }
            }
        });

       mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
               intent.putExtra("id", mConversations.get(position).getObjectId());
               startActivity(intent);
           }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
