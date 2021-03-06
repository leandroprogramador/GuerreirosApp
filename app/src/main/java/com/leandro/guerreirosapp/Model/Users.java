package com.leandro.guerreirosapp.Model;

import com.leandro.guerreirosapp.Helper.ValidationHelper;

import java.util.ArrayList;

/**
 * Created by leani on 02/11/2017.
 */

public class Users {
    private String idUser;
    private String user;
    private String email;
    private String password;
    private ArrayList<String> permissions = new ArrayList<>();

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    public ArrayList<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(ArrayList<String> permissions) {
        this.permissions = permissions;
    }


}
