package com.todotest.prjtodotest;


//import java.beans.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.todotest.prjtodotest.Class.Utils;
import com.todotest.prjtodotest.Class.clsTodo;

public class SQLConnector {   
 String connString = Utils.GetConstr();
 /*   String connString = "jdbc:sqlserver://"+";"
                        + "database=TODOLIST;"
                        + "user="+";"
                        + "password="+";"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";*/


    public String CreateTodo(int UserId, String TodoDescription, int Status){
        Connection conn = null;
        String result = new String();
        try{
            // Code here.
             conn = DriverManager.getConnection(connString);
              if (conn != null) {                 
                PreparedStatement pstmt = conn.prepareStatement("{call dbo.sp_tbl_Todo_Insert(?,?,?)}");
                pstmt.setInt(1,UserId);  
                pstmt.setString(2,TodoDescription);  
                pstmt.setInt(3, Status);  
                boolean IsError = pstmt.execute();  
                if(IsError)
                {   
                    //customer DB error
                    result = "E 41 : Create Task Fail !";
                }
                else{
                    result = "Create Task Success !";
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            result = "E 48 : " + e.getMessage();
        }
        finally{
            try{
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    conn = null;
                }

            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                result = "E 60 : " + ex.getStackTrace().toString();
            }
        }
        return result;
    }

    public String SetTodoStatus(int Id,int UserId, int Status){
        Connection conn = null;
        String result = new String();
        try{
            // Code here.
             conn = DriverManager.getConnection(connString);
              if (conn != null) {                 
                PreparedStatement pstmt = conn.prepareStatement("{call dbo.sp_tbl_Todo_Update(?,?,?)}");
                pstmt.setInt(1,Id);  
                pstmt.setInt(2,UserId);  
                pstmt.setInt(3, Status);  
                pstmt.executeUpdate();  
                int Count = pstmt.getUpdateCount();
                if(Count < 0)
                {   
                    //customer DB error
                    result = "E 84 : Jobs Done Fail To Update !";
                }
                else{
                    result = "Jobs Done !";
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            result = "E 93 : " + e.getMessage();
        }
        finally{
            try{
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    conn = null;
                }

            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                result = "E 105 : " + ex.getStackTrace().toString();
            }
        }
        return result;
    }

    public List<clsTodo> GetTodo(int UserId) {     
        Connection conn = null;
        List<clsTodo> items = null;
        try{
           
             conn = DriverManager.getConnection(connString);
              if (conn != null) {                 
                PreparedStatement pstmt = conn.prepareStatement("{call dbo.sp_tbl_Todo_List(?)}");               

                ResultSet resultSet = null;
                pstmt.setInt(1, UserId);
                resultSet = pstmt.executeQuery();  
                items = new ArrayList<clsTodo>();
                while (resultSet.next()) {           
                  items.add(new clsTodo(resultSet.getInt("ID"), resultSet.getInt("UserId"), resultSet.getString("TodoDescription"), resultSet.getString("CreateDate"), resultSet.getString("LastUpdateTime"), resultSet.getInt("Status"))); 
                 
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try{
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    conn = null;
                }

            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
        }
        return items;
    }

    public int InsUser(String ExternalId,int ExternalTypeId){       

        Connection conn = null;
        int UserId = 0;
        try{
             conn = DriverManager.getConnection(connString);
              if (conn != null) {                 
                PreparedStatement pstmt = conn.prepareStatement("{call dbo.sp_tbl_User_Insert(?,?)}");
                pstmt.setString(1,ExternalId);  
                pstmt.setInt(2,ExternalTypeId);
                ResultSet resultSet = pstmt.executeQuery();  
               
                while (resultSet.next()) {           
                    UserId = resultSet.getInt("UserId");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try{
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    conn = null;
                }

            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                //result = "E 186 : " + ex.getStackTrace().toString();
            }
        }
        return UserId;
    }

   public String InsToken(int UserId,String AccessToken,String Token, LocalDateTime DateTime){       

        Connection conn = null;
        String result = new String();
        try{
            // Code here.
             conn = DriverManager.getConnection(connString);
              if (conn != null) {     
                Timestamp timestamp = Timestamp.valueOf(DateTime);            
                PreparedStatement pstmt = conn.prepareStatement("{call sp_tbl_UserSession_Insert(?,?,?,?)}");
                pstmt.setInt(1,UserId);  
                pstmt.setString(2,AccessToken);  
                pstmt.setString(3,Token);                  
                pstmt.setTimestamp(4,timestamp);  
                pstmt.execute();  
                int Count = pstmt.getUpdateCount();
                if(Count < 0)
                {   
                    //customer DB error
                    result = "E 221 : Fail to add sessesion !";
                }
                else{
                    result = "success";
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            result = "E 230 : " + e.getMessage();
        }
        finally{
            try{
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    conn = null;
                }

            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                result = "E 243 : " + ex.getStackTrace().toString();
            }
        }
        return result;
    }
    

    public boolean GetToken(int UserId, String BearerToken){       

        Connection conn = null;
        boolean IsExpired = true;
        try{
             conn = DriverManager.getConnection(connString);
              if (conn != null) {       
                ResultSet resultSet = null;
               
                PreparedStatement pstmt = conn.prepareStatement("{call dbo.sp_tbl_UserSession_Get(?)}");
                pstmt.setInt(1,UserId);    
                resultSet = pstmt.executeQuery(); 
                
                    LocalDateTime CurrentDatetime = LocalDateTime.now();
                    Timestamp tsCurrentDatetime = Timestamp.valueOf(CurrentDatetime);
                    Timestamp tsExpiredDate;
                    int size = 0;
                    while (resultSet.next()) {                                   
                     
                        tsExpiredDate = resultSet.getTimestamp("Exipred");
                        if(tsExpiredDate.after(tsCurrentDatetime) && BearerToken.equals(resultSet.getString("Token")))
                        {
                            IsExpired = false;
                        }
                    }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            //result = "E 174 : " + e.getMessage();
        }
        finally{
            try{
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    conn = null;
                }

            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                //result = "E 186 : " + ex.getStackTrace().toString();
            }
        }
        return IsExpired;
    }
}