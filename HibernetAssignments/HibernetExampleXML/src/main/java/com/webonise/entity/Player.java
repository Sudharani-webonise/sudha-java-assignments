package com.webonise.entity;

import java.io.Serializable;
import java.util.Date;

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int playerId;
	private String playerName;
	private String gameName;
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