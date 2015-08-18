package com.webonise.mainclass;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.webonise.entity.Medals;
import com.webonise.entity.Player;
import com.webonise.sessionmanager.SessionManager;

public class PlayerMedalsManager {

	final static Logger logger = Logger.getLogger(PlayerMedalsManager.class);

	public static void main(String[] args) {
		try {
			Session session = SessionManager.getSessionFactory().openSession();

			logger.info("session Opned");
			session.beginTransaction();

			Player player = new Player("Sandy", 456123);

			Medals medal1 = new Medals("Medal Name 1",
					"1st price for football", "gold");
			Medals medal2 = new Medals("Medal Name 2",
					"1st price for vallyball", "gold");
			Medals medal3 = new Medals("Medal Name 3",
					"1st price for backetball", "gold");

			Set<Medals> medals = new HashSet<Medals>();
			medals.add(medal1);
			medals.add(medal2);
			medals.add(medal3);

			player.setMedals(medals);

			session.save(player);

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
