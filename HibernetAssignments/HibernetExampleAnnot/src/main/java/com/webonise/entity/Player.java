package com.webonise.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="players")
public class Player  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "player_id")
	private int playerId;
	
	@Column(name="player_name")
	private String playerName;
	
	@Column(name="game_name")
	private String gameName;
	
	@Column(name="played_date")
	private Date playedDate;

	public Player(int playerId, String playerName, String gameName,
			Date playedDate) {
		this.playerId = playerId;
		this.playerName = playerName;
		this.gameName = gameName;
		this.playedDate = playedDate;
	}

	public Player() {
	}

	public Player(String playerName, String gameName, Date playedDate) {
		this.playerName = playerName;
		this.gameName = gameName;
		this.playedDate = playedDate;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Date getPlayedDate() {
		return playedDate;
	}

	public void setPlayedDate(Date playedDate) {
		this.playedDate = playedDate;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

}