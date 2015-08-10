package com.webonise.entity;

public class ConferenceRooms {
	int roomId ;
	int floorNo ;
	String description ;
	int capacity ;
	
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(int floorNo) {
		this.floorNo = floorNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String toString() {
		return "ConferenceRooms [roomId=" + roomId + ", floorNo=" + floorNo + ", description=" + description
				+ ", capacity=" + capacity + "]";
	}

}
