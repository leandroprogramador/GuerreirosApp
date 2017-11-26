package com.leandro.guerreirosapp.Model;

/**
 * Created by leani on 07/11/2017.
 */

public class Session {
    private String userID;
    private String time;

    public Session(String userID, String time) {
        this.userID = userID;
        this.time = time;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
