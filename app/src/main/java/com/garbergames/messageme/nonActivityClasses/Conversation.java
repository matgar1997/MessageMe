package com.garbergames.messageme.nonActivityClasses;

import com.parse.ParseUser;

/**
 * Created by matga_000 on 12/26/2015.
 */
public class Conversation {

    private ParseUser mUsernameOne, mUsernameTwo;
    private String mObjectId;

    public Conversation(ParseUser usernameOne, ParseUser usernameTwo, String objectId){
        this.mUsernameOne = usernameOne;
        this.mUsernameTwo = usernameTwo;
        mObjectId = objectId;
    }

    public String getObjectId(){
        return this.mObjectId;
    }

    public ParseUser getPersonOne(){
        return this.mUsernameOne;
    }

    public ParseUser getPersonTwo(){
        return this.mUsernameTwo;
    }
}
