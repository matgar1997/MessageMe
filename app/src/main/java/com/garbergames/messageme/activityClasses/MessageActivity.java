package com.garbergames.messageme.activityClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.garbergames.messageme.Adapters.MessageAdapter;
import com.garbergames.messageme.R;
import com.garbergames.messageme.nonActivityClasses.Message;
import com.garbergames.messageme.utils.Keys;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private EditText mEnterMessage;
    private Button mSend, mRefresh;
    private ListView mList;

    private String currentConvoId;
    private ParseObject currentConvoPointer;

    private ArrayList<Message> mMessageList;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        currentConvoId = getIntent().getStringExtra(Keys.ID);

        mEnterMessage = (EditText) findViewById(R.id.edittext_message);
        mSend = (Button) findViewById(R.id.button_send);
        mRefresh = (Button) findViewById(R.id.button_refresh);
        mList = (ListView) findViewById(R.id.listview_messages);
        mMessageList = new ArrayList<Message>();

        final MessageAdapter messageAdapter = new MessageAdapter(this, mMessageList);
        mList.setAdapter(messageAdapter);

        ParseQuery<ParseObject> convoQuery = new ParseQuery("Conversation");
        convoQuery.include(Keys.CREATED_BY);
        convoQuery.include(Keys.CREATED_FOR);
        convoQuery.whereEqualTo(Keys.OBJECT_ID, currentConvoId);
        convoQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    currentConvoPointer = objects.get(0);
                }
            }
        });
        getSupportActionBar().hide();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(Keys.MESSAGE);
        query.orderByDescending(Keys.CREATED_AT);
        query.include(Keys.CONVERSATION);
        query.include(Keys.FROM_USER);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.d("done entered", "true");
                if (e == null) {
                    Log.d("e is null entered", "true");
                    Log.d("current convo is: ", "" + currentConvoId);
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("ids are: ", objects.get(i).getObjectId().toString());
                        if (objects.get(i).getParseObject(Keys.CONVERSATION).getObjectId().equals(currentConvoId)) {
                            Log.d("equals current id", "true");
                            ParseUser user = objects.get(i).getParseUser(Keys.FROM_USER);
                            String message = objects.get(i).getString(Keys.MESSAGE_POINTER);
                            mMessageList.add(new Message(user, message));
                            Log.d("number of messages", "" + mMessageList.size());
                        }

                        //notify data changed
                        messageAdapter.notifyDataSetChanged(); //This line causes an error
                    }
                }
            }
        });


        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = mEnterMessage.getText().toString();
                if (messageText.length() > 0) {
                    ParseUser user = ParseUser.getCurrentUser();
                    ParseObject message = new ParseObject(Keys.MESSAGE);
                    message.put(Keys.FROM_USER, user);
                    message.put(Keys.MESSAGE_POINTER, messageText);
                    message.put(Keys.CONVERSATION, currentConvoPointer);
                    message.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d("sent: ", "Yes");
                                mEnterMessage.setText("");
                            } else {
                                Log.d("send failed: ", "" + e.getMessage());
                            }
                        }
                    });
                }
            }
        });

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageList.clear();
                ParseQuery<ParseObject> refreshQuery = ParseQuery.getQuery(Keys.MESSAGE);
                refreshQuery.orderByDescending(Keys.CREATED_AT);
                refreshQuery.include(Keys.CONVERSATION);
                refreshQuery.include(Keys.FROM_USER);
                refreshQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        Log.d("done entered", "true");
                        if (e == null) {
                            Log.d("e is null entered", "true");
                            Log.d("current convo is: ", "" + currentConvoId);
                            for (int i = 0; i < objects.size(); i++) {
                                Log.d("ids are: ", objects.get(i).getObjectId().toString());
                                if (objects.get(i).getParseObject(Keys.CONVERSATION).getObjectId().equals(currentConvoId)) {
                                    Log.d("equals current id", "true");
                                    ParseUser user = objects.get(i).getParseUser(Keys.FROM_USER);
                                    String message = objects.get(i).getString(Keys.MESSAGE_POINTER);
                                    mMessageList.add(new Message(user, message));
                                    Log.d("number of messages", "" + mMessageList.size());
                                }

                                //notify data changed
                                messageAdapter.notifyDataSetChanged(); //This line causes an error
                            }
                        }
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message, menu);
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

    public void refresh(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Keys.MESSAGE);
        query.include(Keys.CONVERSATION);
        query.include(Keys.FROM_USER);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.d("done entered", "true");
                if (e == null) {
                    Log.d("e is null entered", "true");
                    Log.d("current convo is: ", "" + currentConvoId);
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("ids are: ", objects.get(i).getObjectId().toString());
                        if (objects.get(i).getParseObject(Keys.CONVERSATION).getObjectId().equals(currentConvoId)) {
                            Log.d("equals current id", "true");
                            ParseUser user = objects.get(i).getParseUser(Keys.FROM_USER);
                            String message = objects.get(i).getString(Keys.MESSAGE_POINTER);
                            mMessageList.add(new Message(user, message));
                            Log.d("number of messages", "" + mMessageList.size());
                        }

                        //notify data changed
                        messageAdapter.notifyDataSetChanged(); //This line causes an error
                    }
                }
            }
        });
    }
}
