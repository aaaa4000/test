package com.todotest.prjtodotest.Class;


public class clsTodo {
    public int ID;
     public int UserId;
public     String TodoDescription;
     public String CreateDate;
public      String LastUpdateTime;
     public int Status;
	public clsTodo()
	{
	 super();
	}
   public clsTodo(int ID, int UserId, String TodoDescription, String CreateDate, String LastUpdateTime, int Status)
   {
        this.ID = ID;
        this.UserId = UserId;
        this.TodoDescription = TodoDescription;
        this.CreateDate = CreateDate;
        this.LastUpdateTime = LastUpdateTime;
        this.Status = Status;
   }
}

