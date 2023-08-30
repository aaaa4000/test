package com.todotest.prjtodotest.Class;

public class clsFBToken {
    public String access_token;
    public String token_type;
    public int expires_in;

    public String GetToken(){
        return this.access_token;
    }
}
