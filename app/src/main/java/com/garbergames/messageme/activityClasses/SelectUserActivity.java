package com.garbergames.messageme.activityClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.garbergames.messageme.Adapters.UserAdapter;
import com.garbergames.messageme.R;
import com.garbergames.messageme.utils.Keys;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class SelectUserActivity extends AppCompatActivity {

    private ListView mList;
    private Button mFindUsers, mAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        getSupportActionBar().hide();

        final ArrayList<String> names = getIntent().getStringArrayListExtra(Keys.NAME);
        final ArrayList<String> usernames = getIntent().getStringArrayListExtra(Keys.USERNAME);
        final ArrayList<String> email = getIntent().getStringArrayListExtra(Keys.EMAIL);

        mList = (ListView) findViewById(R.id.listview_users);
        mFindUsers = (Button) findViewById(R.id.button_find_user);
       // mAddUser = (Button) findViewById(R.id.button_add_friend);

        mList.setAdapter(new UserAdapter(usernames, names, email, this));

        mFindUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo(Keys.NAME, names.get(position));
                query.whereEqualTo(Keys.USERNAME, usernames.get(position));
                query.whereEqualTo(Keys.EMAIL, email.get(position));
                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        if(e == null) {
                            ParseUser user = objects.get(0);
                            ParseObject conversation = new ParseObject(Keys.CONVERSATION_OBJ);
                            conversation.put(Keys.CREATED_BY, ParseUser.getCurrentUser());
                            conversation.put(Keys.CREATED_FOR, user);
                            conversation.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e == null){
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            //ParseUser.getCurrentUser().put("friend", user);
                            //ParseUser.getCurrentUser().add("friend", user);
                            //ParseUser.getCurrentUser().saveInBackground();
                        }
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_user, menu);
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
