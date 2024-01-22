package kwiaciarnia.dao;

import java.util.List;
import java.util.Map;

import kwiaciarnia.jpa.Symbole;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class SymboleDAO {
	
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Symbole symbole) {
		em.persist(symbole);
	}

	public Symbole merge(Symbole symbole) {
		return em.merge(symbole);
	}

	public void remove(Symbole symbole) {
		em.remove(em.merge(symbole));
	}

	public Symbole find(Object id) {
		return em.find(Symbole.class, id);
	}

	public List<Symbole> getFullList() {
		List<Symbole> list = null;

		Query query = em.createQuery("select s from Symbole s");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Symbole> getList(Map<String, Object> searchParams) {
		List<Symbole> list = null;

		// 1. Build query string with parameters
		String select = "select s ";
		String from = "from Symbole s ";
		String where = "";
		String orderby = "order by s.idSymbole";

		// search for params
		String symbol = (String) searchParams.get("symbol");
		if (symbol != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "s.symbol like :symbol ";
		}
		
		int idSymbole = (int) searchParams.get("idSymbole");
		if (idSymbole > 0) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "s.idSymbole like :idSymbole ";
		}
		

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (symbol != null) {
			query.setParameter("symbol", symbol+"%");
		}
		
		if (idSymbole > 0) {
			query.setParameter("idSymbole", idSymbole);
		}


		// 4. Execute query and retrieve list of objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
}
