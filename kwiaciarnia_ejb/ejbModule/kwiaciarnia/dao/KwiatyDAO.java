package kwiaciarnia.dao;

import java.util.List;
import java.util.Map;

import kwiaciarnia.jpa.Kwiaty;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class KwiatyDAO {

	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Kwiaty kwiaty) {
		em.persist(kwiaty);
	}

	public Kwiaty merge(Kwiaty kwiaty) {
		return em.merge(kwiaty);
	}

	public void remove(Kwiaty kwiaty) {
		em.remove(em.merge(kwiaty));
	}

	public Kwiaty find(Object id) {
		return em.find(Kwiaty.class, id);
	}

	public List<Kwiaty> getFullList() {
		List<Kwiaty> list = null;

		Query query = em.createQuery("select k from Kwiaty k");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Kwiaty> getList(Map<String, Object> searchParams) {
		List<Kwiaty> list = null;

		// 1. Build query string with parameters
		String select = "select k ";
		String from = "from Kwiaty k ";
		String where = "";
		String orderby = "order by k.idKwiaty";

		// search for surname
		String kwiat = (String) searchParams.get("kwiat");
		if (kwiat != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "k.kwiat like :kwiat ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (kwiat != null) {
			query.setParameter("kwiat", kwiat+"%");
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
