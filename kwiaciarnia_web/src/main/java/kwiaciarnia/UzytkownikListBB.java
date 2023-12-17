package kwiaciarnia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.servlet.http.HttpSession;

import kwiaciarnia.dao.UzytkownikDAO;
import kwiaciarnia.jpa.Uzytkownik;

@Named
@RequestScoped
public class UzytkownikListBB {

	private static final String PAGE_UZYTKOWNIK_EDIT = "uzytkownikEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String uzyt;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	UzytkownikDAO uzytkownikDAO;

	public String getUzyt() {
		return uzyt;
	}

	public void setUzyt(String uzyt) {
		this.uzyt = uzyt;
	}

	public List<Uzytkownik> getFullList(){
		return uzytkownikDAO.getFullList();
	}

	public List<Uzytkownik> getList(){
		List<Uzytkownik> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (uzyt != null && uzyt.length() > 0){
			searchParams.put("uzyt", uzyt);
		}
		
		//2. Get list
		list = uzytkownikDAO.getList(searchParams);
		
		return list;
	}

	public String newUzytkownik(){
		Uzytkownik uzytkownik = new Uzytkownik();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("uzytkownik", uzytkownik);
		
		return PAGE_UZYTKOWNIK_EDIT;
	}

	public String editUzytkownik(Uzytkownik uzytkownik){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("uzytkownik", uzytkownik);
		
		return PAGE_UZYTKOWNIK_EDIT;
	}

	public String deleteUzytkownik(Uzytkownik uzytkownik){
		uzytkownikDAO.remove(uzytkownik);
		return PAGE_STAY_AT_THE_SAME;
	}
	
}
