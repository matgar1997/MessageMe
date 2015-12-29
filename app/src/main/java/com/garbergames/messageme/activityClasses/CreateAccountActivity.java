package com.garbergames.messageme.activityClasses;

import android.app.AlertDialog;
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
import com.parse.SignUpCallback;

import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText mName, mEmail, mUsername, mPassword;
    private Button mCreateAccount;


    //Add in a user lookup by name
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        getSupportActionBar().hide();

        mName = (EditText) findViewById(R.id.edittext_name);
        mUsername = (EditText) findViewById(R.id.edittext_username);
        mEmail = (EditText) findViewById(R.id.edittext_email);
        mPassword = (EditText) findViewById(R.id.edittext_password);
        mCreateAccount = (Button) findViewById(R.id.button_create_account);

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTextValid()) {
                    final String name, username, email, password, question, answer;
                    name = mName.getText().toString();
                    username = mUsername.getText().toString();
                    email = mEmail.getText().toString();
                    password = mPassword.getText().toString();

                    ParseUser user = new ParseUser();
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.put(Keys.NAME, name);


                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                //Account created
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
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

    private boolean isTextValid(){
        if(mName.getText().toString().equals("")){
            return false;
        }
        if(mUsername.getText().toString().equals("")){
            return false;
        }
        if(mEmail.getText().toString().equals("")){
            return false;
        }
        if(mPassword.getText().toString().equals("")){
            return false;
        }

        return true;
    }
}
