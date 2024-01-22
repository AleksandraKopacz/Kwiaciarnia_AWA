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

		// search for params
		String usluga = (String) searchParams.get("usluga");
		if (usluga != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.usluga like :usluga ";
		}
		
		String img = (String) searchParams.get("img");
		if (img != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.img like :img ";
		}
		
		String opis = (String) searchParams.get("opis");
		if (opis != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.opis like :opis ";
		}
		
		String typ = (String) searchParams.get("typ");
		if (typ != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.typ like :typ ";
		}
		
		int idUslugi = (int) searchParams.get("idUslugi");
		if (idUslugi > 0) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.idUslugi like :idUslugi ";
		}
		
		String sortuj = (String) searchParams.get("sortuj");
		if (sortuj != null) {
			if (sortuj.equals("ASC")) {
				orderby = "order by u.cenaUslugi ASC";
			} else if (sortuj.equals("DESC")) {
				orderby = "order by u.cenaUslugi DESC";
			} else {
				orderby = "order by u.idUslugi";
			}
		}
		

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (usluga != null) {
			query.setParameter("usluga", usluga+"%");
		}
		
		if (img != null) {
			query.setParameter("img", img+"%");
		}
		
		if (opis != null) {
			query.setParameter("opis", "%"+opis+"%");
		}
		
		if (typ != null) {
			query.setParameter("typ", typ+"%");
		}
		
		if (idUslugi > 0) {
			query.setParameter("idUslugi", idUslugi);
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
