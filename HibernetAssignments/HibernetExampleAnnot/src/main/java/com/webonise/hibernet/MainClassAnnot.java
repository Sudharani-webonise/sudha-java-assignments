package com.webonise.hibernet;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.webonise.entity.Player;
import com.webonise.sessionmanager.SessionManager;

public class MainClassAnnot {
	final static Logger logger = Logger.getLogger(MainClassAnnot.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Session session = SessionManager.getSessionFactory()
				.openSession();
		addPlayer("Yakub", "sing", new Date(), session);
		readPlayers(session, 1);
		updatePlayer(session, 1);
		deletePlayer(session, 1);

	}

	private static void deletePlayer(Session session, int id) {
		try {
			session.beginTransaction();
			Player player = (Player) session.get(Player.class, id);
			session.delete(player);
			session.getTransaction().commit();
			logger.info("REMOVED");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private static void updatePlayer(Session session, int id) {
		try {

			session.beginTransaction();
			Player player = (Player) session.get(Player.class, id);
			player.setGameName("Cricket");
			session.update(player);
			session.getTransaction().commit();
			logger.info("UPDATED");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	private static void addPlayer( String name, String game, Date date,
			Session session) {
		try {
			session.beginTransaction();
			Player player = new Player( name, game, date);
			session.save(player);
			session.getTransaction().commit();
			logger.info("Insterted succesfully");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private static void readPlayers(Session session, int id) {
		Player player = (Player) session.get(Player.class, id);
		System.out.println(player.getPlayerId());
		System.out.println(player.getPlayerName());
		System.out.println(player.getGameName());
		System.out.println(player.getPlayedDate());
	}

}