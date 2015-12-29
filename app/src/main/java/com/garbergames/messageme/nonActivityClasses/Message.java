package com.garbergames.messageme.nonActivityClasses;

import com.parse.ParseUser;

/**
 * Created by matga_000 on 12/26/2015.
 */
public class Message {

    private String mMessage;
    private ParseUser mFromUser;

    public Message(ParseUser fromUser, String message){
        this.mFromUser = fromUser;
        this.mMessage = message;
    }

    public String getName(){
        return this.mFromUser.getUsername();
    }

    public String getMessage(){
        return this.mMessage;
    }
}
