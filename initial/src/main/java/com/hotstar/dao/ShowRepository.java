package com.hotstar.dao;

import javax.persistence.EntityManagerFactory;

import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShowRepository {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public List<String> getTraysContainingShow(String name) {
		EntityManager session = entityManagerFactory.createEntityManager();
		try {
			List<String> trays = session.createNativeQuery("Select name from tray WHERE id in (select ts.tray_id from tray_show ts inner join show s on s.id=ts.show_id where s.name = :sname)")
//					List<String> trays = session.createNativeQuery("Select name from business WHERE name = :sname")
					.setParameter("sname", name).getResultList();

			return trays;
		} catch (Exception e) {
			return null;
		} finally {
			if (session.isOpen())
				session.close();
		}
	}

}