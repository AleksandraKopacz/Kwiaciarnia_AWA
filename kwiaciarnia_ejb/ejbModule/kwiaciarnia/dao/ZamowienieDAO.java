package kwiaciarnia.dao;

import java.util.List;
import java.util.Map;

import kwiaciarnia.jpa.Zamowienie;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class ZamowienieDAO {

	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Zamowienie zamowienie) {
		em.persist(zamowienie);
	}

	public Zamowienie merge(Zamowienie zamowienie) {
		return em.merge(zamowienie);
	}

	public void remove(Zamowienie zamowienie) {
		em.remove(em.merge(zamowienie));
	}

	public Zamowienie find(Object id) {
		return em.find(Zamowienie.class, id);
	}

	public List<Zamowienie> getFullList() {
		List<Zamowienie> list = null;

		Query query = em.createQuery("select z from Zamowienie z");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Zamowienie> getList(Map<String, Object> searchParams) {
		List<Zamowienie> list = null;

		// 1. Build query string with parameters
		String select = "select z ";
		String from = "from Zamowienie z ";
		String where = "";
		String orderby = "order by z.id_zamowienie";

		// search for surname
		String id_zamowienie = (String) searchParams.get("id_zamowienie");
		if (id_zamowienie != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "z.id_zamowienie like :id_zamowienie ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (id_zamowienie != null) {
			query.setParameter("id_zamowienie", id_zamowienie+"%");
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
