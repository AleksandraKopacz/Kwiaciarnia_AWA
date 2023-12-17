package kwiaciarnia.dao;

import java.util.List;
import java.util.Map;

import kwiaciarnia.jpa.Uslugi;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class UslugiDAO {

	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Uslugi uslugi) {
		em.persist(uslugi);
	}

	public Uslugi merge(Uslugi uslugi) {
		return em.merge(uslugi);
	}

	public void remove(Uslugi uslugi) {
		em.remove(em.merge(uslugi));
	}

	public Uslugi find(Object id) {
		return em.find(Uslugi.class, id);
	}

	public List<Uslugi> getFullList() {
		List<Uslugi> list = null;

		Query query = em.createQuery("select u from Uslugi u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Uslugi> getList(Map<String, Object> searchParams) {
		List<Uslugi> list = null;

		// 1. Build query string with parameters
		String select = "select u ";
		String from = "from Uslugi u ";
		String where = "";
		String orderby = "order by u.idUslugi";

		// search for surname
		String usluga = (String) searchParams.get("usluga");
		if (usluga != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.usluga like :usluga ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (usluga != null) {
			query.setParameter("usluga", usluga+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
