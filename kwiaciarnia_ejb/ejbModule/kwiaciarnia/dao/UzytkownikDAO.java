package kwiaciarnia.dao;

import java.util.List;
import java.util.Map;

import kwiaciarnia.jpa.Uzytkownik;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class UzytkownikDAO {

	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Uzytkownik uzytkownik) {
		em.persist(uzytkownik);
	}

	public Uzytkownik merge(Uzytkownik uzytkownik) {
		return em.merge(uzytkownik);
	}

	public void remove(Uzytkownik uzytkownik) {
		em.remove(em.merge(uzytkownik));
	}

	public Uzytkownik find(Object id) {
		return em.find(Uzytkownik.class, id);
	}

	public List<Uzytkownik> getFullList() {
		List<Uzytkownik> list = null;

		Query query = em.createQuery("select uz from Uzytkownik uz");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Uzytkownik> getList(Map<String, Object> searchParams) {
		List<Uzytkownik> list = null;

		// 1. Build query string with parameters
		String select = "select uz ";
		String from = "from Uzytkownik uz ";
		String where = "";
		String orderby = "order by uz.id_uzytkownik";

		// search for surname
		String id_uzytkownik = (String) searchParams.get("id_uzytkownik");
		if (id_uzytkownik != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "uz.id_uzytkownik like :id_uzytkownik ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (id_uzytkownik != null) {
			query.setParameter("id_uzytkownik", id_uzytkownik+"%");
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
