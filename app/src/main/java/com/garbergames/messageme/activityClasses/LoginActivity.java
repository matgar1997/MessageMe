package com.garbergames.messageme.activityClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.garbergames.messageme.R;
import com.garbergames.messageme.utils.Keys;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsername, mPassword;
    private Button mLogin, mCreateAccount, mForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        mUsername = (EditText) findViewById(R.id.edittext_username);
        mPassword = (EditText) findViewById(R.id.edittext_password);
        mLogin = (Button) findViewById(R.id.button_login);
        mCreateAccount = (Button) findViewById(R.id.button_create_account);
        mForgot = (Button) findViewById(R.id.button_forgot);

        mUsername.setText("");
        mPassword.setText("");

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                if((username != "" || username != null) || (password != "" || password != null)){
                    //login the parse user
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(e == null) {
                                mUsername.setText("");
                                mPassword.setText("");
                                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                                intent.putExtra(Keys.USERNAME, user.getUsername());
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);

            }
        });

        mForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotAccountActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
