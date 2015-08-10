package com.irb.implConn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.irb.data.ConferenceRooms;

public class ConferenceImpl {
	
	private static String driver="com.mysql.jdbc.Driver";
	private static String url="jdbc:mysql://localhost:3306/crb_db";
	private static String user="root";
	private static String password="root";
	
	 public ArrayList getData() {
		
	  ArrayList list=new ArrayList();  
	   try{  
	    Class.forName(driver);  
	    Connection con=DriverManager.getConnection(  
	    url,user,password);  
	              
	    PreparedStatement ps=con.prepareStatement("select * from conference_rooms");  
	    ResultSet rs=ps.executeQuery();  
	    while(rs.next()){  
	     ConferenceRooms room=new ConferenceRooms();  
	     room.setRoomId(rs.getInt(1));  
	     room.setFloorNo(rs.getInt(2));
	     room.setDescription(rs.getString(3));
	     room.setCapacity(rs.getInt(4));
	     list.add(room);  
	     System.out.println(room);
	    }  
	    con.close();  
	              
	   }catch(Exception ex){
		   System.out.print(ex);
		}
	return list;  
	   
	  
	 }
}
