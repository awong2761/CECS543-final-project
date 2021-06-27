package com.example.project1;

import android.widget.Toast;

import java.util.HashMap;

public class Data {

    // A hashmap data structure for holding usernames and passwords pair
    private static HashMap <String, String> hmCredentials = new HashMap<>();

    public Data(){
        // Adding some items into the hashmap table
        hmCredentials.put("1", "1");
    }

    // This method adds a new username and password to the hashmap
    public void AddCredential(String username, String password){
        hmCredentials.put(username,password);
    }
    // This method checks if username exists in the hashmap
    public Boolean CheckUsername(String username){
        Boolean  retval = true;
        if(!hmCredentials.containsKey(username)){
            retval = false;
        }
        return retval;
    }

    // This method checks a username and password combination is correct!
    public Boolean CheckCredentials(String username, String Password){
        Boolean  retval;
        String user = hmCredentials.get(username);
        if(CheckUsername(username)){
            if(user.equals(Password)){
                retval = true;
            }
            else
                retval = false;
        }
        else
            retval = false;
        return retval;
    }
    public String getPassword(String user){
        return hmCredentials.get(user);
    }
    public void setNewPass(String username, String password){
        hmCredentials.put(username, password);
    }

}
