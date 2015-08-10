package com.webonise.entity;

public class EmployeeDetails {
	int id;
	String name;
	String type ;
	String city;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String toString() {
		return "EmployeeDetails [id=" + id + ", name=" + name + ", type=" + type + ", city=" + city + "]";
	}
	
	
	
}
