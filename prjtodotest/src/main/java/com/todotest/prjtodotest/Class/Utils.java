package com.todotest.prjtodotest.Class;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

public  class Utils {

   
   
    private static String Constr;
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String GetConstr(){
        return Constr;
    }
    public static String SetConStr(String IP,String User,String Pass)
    {
        return  Constr = "jdbc:sqlserver://"+IP+";"
        + "database=TODOLIST;"
        + "user="+User+";"
        + "password="+Pass+";"
        + "encrypt=false;"
        + "trustServerCertificate=false;"
        + "loginTimeout=30;";

    }
}
