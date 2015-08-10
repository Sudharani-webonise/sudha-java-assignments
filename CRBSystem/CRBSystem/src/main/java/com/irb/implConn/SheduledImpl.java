package com.irb.implConn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.irb.data.ConferenceRooms;

public class SheduledImpl {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/crb_db";
	private static String user = "root";
	private static String password = "root";
	Connection con;

	public ArrayList checkStatus(String roomnumber) {
		int roomno = Integer.parseInt(roomnumber);
		getConnection();
		ArrayList list = new ArrayList();
		try {
			PreparedStatement ps = con.prepareStatement("select * from conference_rooms where roomId=" + roomno);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ConferenceRooms room = new ConferenceRooms();
				room.setRoomId(rs.getInt(1));
				room.setFloorNo(rs.getInt(2));
				room.setDescription(rs.getString(3));
				room.setCapacity(rs.getInt(4));
				list.add(room);
				System.out.println(room);
			}
			con.close();

		} catch (Exception ex) {
			System.out.print(ex);
		}
		return list;

	}

	private void getConnection() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void insertBookedRoom(String roomId, String empId, String comment, String date, String floorno, String fromTime,
			String toTime) throws SQLException {

		getConnection();
		int rId = Integer.parseInt(roomId);
		int eId = Integer.parseInt(empId);
		int fNo = Integer.parseInt(floorno);

		System.out.println("Inserting records into the table...");
		
		 Statement st = con.createStatement();
		 
	      // note that i'm leaving "date_created" out of this insert statement
	      st.executeUpdate("INSERT INTO schedule_details (empid, roomid, floorNumber,scheduleDate,startTime,endTime,comment) "
	          +"VALUES ("+ eId + "," + rId + "," + fNo + ",'" + date + "','" + fromTime +"', '" + toTime +"','"+comment+"')");
		

		/*
		 * PreparedStatement ps = con.prepareStatement(
		 * "inset into schedule_details (empid, roomid, floorNumber,scheduleDate,startTime,endTime,comment) VALUE ('"
		 * + eId + "','" + rId + "','" + fNo + "','" + date + "','" + fromTime +
		 * "','" + toTime + "' "); ps.executeUpdate();
		 */
	}

}
