package com.garbergames.messageme.activityClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.garbergames.messageme.R;
import com.garbergames.messageme.utils.Keys;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private EditText mUsername, mEmail, mName;
    private Button mFindUser;
    //private int type; //1 = name, 2 = username, 3 = email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().hide();

        mUsername = (EditText) findViewById(R.id.edittext_username);
        mEmail = (EditText) findViewById(R.id.edittext_email);
        mName = (EditText) findViewById(R.id.edittext_name);
        mFindUser = (Button) findViewById(R.id.button_find);

        mFindUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                if(mUsername.getText().toString().length() != 0){
                    query.whereEqualTo(Keys.USERNAME, mUsername.getText().toString());
                    //type = 2;
                }
                else if(mName.getText().toString().length() != 0){
                    query.whereEqualTo(Keys.NAME, mName.getText().toString());
                   // type = 1;
                }
                else if(mEmail.getText().toString().length() != 0){
                    query.whereEqualTo(Keys.EMAIL, mEmail.getText().toString());
                   // type = 3;
                }

                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        if(e == null){
                            //create intent
                            Log.d("size",""+objects.size());
                            Intent intent = new Intent(getApplicationContext(), SelectUserActivity.class);
                            //send arraylist of strings over
                            ArrayList<String> name = new ArrayList<String>();
                            ArrayList<String> email = new ArrayList<String>();
                            ArrayList<String> user = new ArrayList<String>();

                                for (int i = 0; i < objects.size(); i++) {
                                    email.add(i, objects.get(i).getEmail());
                                    name.add(i, objects.get(i).getString(Keys.NAME));
                                    user.add(i, objects.get(i).getUsername());
                                }

                            //also send over what type of data
                            intent.putStringArrayListExtra(Keys.NAME, name);
                            intent.putStringArrayListExtra(Keys.USERNAME, user);
                            intent.putStringArrayListExtra(Keys.EMAIL, email);
                            //goto screen selectuseractivity
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
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
