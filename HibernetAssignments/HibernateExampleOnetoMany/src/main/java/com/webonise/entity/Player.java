package com.webonise.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "player_demo")
public class Player {

	private int player_id;

	private String player_name;
	private int phone_number;
	private Set<Medals> medals;

	public Player() {
	}

	public Player(String player_name, int phone_number) {
		this.player_name = player_name;
		this.phone_number = phone_number;
	}

	@Id
	@GeneratedValue
	@Column(name = "player_id")
	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public String getPlayer_name() {
		return player_name;
	}

	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}

	public int getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(int phone_number) {
		this.phone_number = phone_number;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "player_medals", joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "medal_id"))
	public Set<Medals> getMedals() {
		return medals;
	}

	public void setMedals(Set<Medals> medals) {
		this.medals = medals;
	}

}
