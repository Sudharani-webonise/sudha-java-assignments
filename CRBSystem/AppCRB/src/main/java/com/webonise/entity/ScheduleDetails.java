package com.webonise.entity;

public class ScheduleDetails {
	int employeeId ;
	int roomId;
	int floorNumber;
	String scheduleDate ;
	String startTime ;
	String endTime ;
	String comment ;
	
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String toString() {
		return "ScheduleDetails [employeeId=" + employeeId + ", roomId=" + roomId + ", floorNumber=" + floorNumber
				+ ", scheduleDate=" + scheduleDate + ", startTime=" + startTime + ", endTime=" + endTime + ", comment="
				+ comment + "]";
	}
	
}
