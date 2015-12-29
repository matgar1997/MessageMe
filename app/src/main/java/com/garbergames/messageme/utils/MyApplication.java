package com.garbergames.messageme.utils;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

/**
 * Created by matga_000 on 12/17/2015.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore, register parseSubclasses, init the database.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, Keys.APPLICTION_ID, Keys.CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
