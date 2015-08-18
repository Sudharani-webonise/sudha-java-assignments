package com.webonise.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medals_demo")
public class Medals {

	private int id;
	private String medal_name;
	private String description;
	private String medal_type;

	public Medals(String medal_name, String description, String medal_type) {
		super();
		this.medal_name = medal_name;
		this.description = description;
		this.medal_type = medal_type;
	}

	@Id
	@GeneratedValue
	@Column(name = "medal_id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMedal_name() {
		return medal_name;
	}

	public void setMedal_name(String medal_name) {
		this.medal_name = medal_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMedal_type() {
		return medal_type;
	}

	public void setMedal_type(String medal_type) {
		this.medal_type = medal_type;
	}

}
