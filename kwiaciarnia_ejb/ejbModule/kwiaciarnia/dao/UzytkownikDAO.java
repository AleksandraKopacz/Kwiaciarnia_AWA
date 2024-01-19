package kwiaciarnia.dao;

import java.util.ArrayList;
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
		String orderby = "order by uz.idUzytkownik";

		// search for surname
		String email = (String) searchParams.get("email");
		if (email != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "uz.email like :email ";
		}
		
		byte rola = (byte) searchParams.get("rola");
		if (rola != 2) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "uz.rola like :rola ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (email != null) {
			query.setParameter("email", email+"%");
		}
		
		if (rola != 2) {
			query.setParameter("rola", rola);
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
	
	public Uzytkownik getUserFromDatabase(Map<String, Object> searchParams) {
		Uzytkownik uzytkownik = null;
		
		String select = "select distinct uz ";
		String from = "from Uzytkownik uz ";
		String where = "where ";
		
		String email = (String) searchParams.get("email");
		String haslo = (String) searchParams.get("haslo");
		where += "uz.email = :email and uz.haslo = :haslo";
		
		Query query = em.createQuery(select + from + where);
		
		query.setParameter("email", email);
		query.setParameter("haslo", haslo);
		
		try {
			uzytkownik = (Uzytkownik) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return uzytkownik;
	}

	public List<String> getUserRolesFromDatabase(Uzytkownik uzytkownik) {
		ArrayList<String> roles = new ArrayList<String>();
		
		int roleCheck = uzytkownik.getRola();
		String whatRole = String.valueOf(roleCheck);
		
		if (whatRole.equals("1")) {
			roles.add("1");
		} else if(whatRole.equals("0")) {
			roles.add("0");
		}
		
		return roles;
	}
	
}
