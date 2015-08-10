package com.webonise.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.webonise.databaseconc.DBconnection;

public class SheduledImpl {
	DBconnection dBconnection = new DBconnection();
	Connection con;
	int fno;

	public int getFloorNumber(String roomnumber) {
		con = dBconnection.getConection();
		int roomno = Integer.parseInt(roomnumber);
		try {
			PreparedStatement ps = con
					.prepareStatement("select * from conference_rooms where roomId="
							+ roomno);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				fno = rs.getInt(2);
			}
			// System.out.println("floor number :" + fno);
			con.close();

		} catch (Exception ex) {
			System.out.print(ex);
		}
		return fno;

	}

	public void insertBookedRoom(String roomId, String empId, String comment,
			String date, String floorno, String fromTime, String toTime)
			throws SQLException {
		con = dBconnection.getConection();
		int rId = Integer.parseInt(roomId);
		int eId = Integer.parseInt(empId);
		int fNo = Integer.parseInt(floorno);

		System.out.println("Inserting records into the table...");

		Statement st = con.createStatement();

		st.executeUpdate("INSERT INTO schedule_details (empid, roomid, floorNumber,scheduleDate,startTime,endTime,comment) "
				+ "VALUES ("
				+ eId
				+ ","
				+ rId
				+ ","
				+ fNo
				+ ",'"
				+ date
				+ "','" + fromTime + "', '" + toTime + "','" + comment + "')");

	}

}
