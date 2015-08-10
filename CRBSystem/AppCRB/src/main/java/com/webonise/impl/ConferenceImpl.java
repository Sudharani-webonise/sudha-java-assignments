package com.webonise.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.webonise.databaseconc.DBconnection;
import com.webonise.entity.ConferenceRooms;

public class ConferenceImpl {

	DBconnection dBconnection = new DBconnection();
	Connection con;

	public ArrayList<ConferenceRooms> getData() {
		ArrayList<ConferenceRooms> list = new ArrayList<ConferenceRooms>();
		try {
			con = dBconnection.getConection();
			PreparedStatement ps = con
					.prepareStatement("select * from conference_rooms");
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
}
