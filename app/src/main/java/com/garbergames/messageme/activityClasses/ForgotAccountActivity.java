package com.garbergames.messageme.activityClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.garbergames.messageme.R;
import com.garbergames.messageme.utils.Keys;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.util.List;

public class ForgotAccountActivity extends AppCompatActivity {

    private EditText mEnterPrompt;
    private Button mGetPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_account);


        getSupportActionBar().hide();
        mEnterPrompt = (EditText) findViewById(R.id.edittext_enter_prompts);
        mGetPrompt = (Button) findViewById(R.id.button_get_prompt);

        mGetPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get query for that email
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo(Keys.EMAIL, mEnterPrompt.getText().toString());
                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null) {
                            if (objects.size() > 0) {
                                ParseUser.requestPasswordResetInBackground(objects.get(0).getEmail(), new RequestPasswordResetCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            //email sent
                                            finish();
                                        } else {
                                            //error
                                        }
                                    }
                                });
                            }
                        }
                    }
                });


                //if size is 0 say email not found
                //else send reset to the first spot in the query
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forgot_account, menu);
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
