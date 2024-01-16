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

import kwiaciarnia.dao.ZamowienieDAO;
import kwiaciarnia.jpa.Zamowienie;

@Named
@RequestScoped
public class ZamowienieListBB {
	
	private static final long serialVersionUID = 1L;

	private static final String PAGE_ZAMOWIENIE_EDIT = "/pages/admin/zamowienieEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String szczegoly;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	ZamowienieDAO zamowienieDAO;

	public String getSzczegoly() {
		return szczegoly;
	}

	public void setSzczegoly(String szczegoly) {
		this.szczegoly = szczegoly;
	}

	public List<Zamowienie> getFullList(){
		return zamowienieDAO.getFullList();
	}

	public List<Zamowienie> getList(){
		List<Zamowienie> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (szczegoly != null && szczegoly.length() > 0){
			searchParams.put("szczegoly", szczegoly);
		}
		
		//2. Get list
		list = zamowienieDAO.getList(searchParams);
		
		return list;
	}

	public String newZamowienie(){
		Zamowienie zamowienie = new Zamowienie();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("zamowienie", zamowienie);
		
		return PAGE_ZAMOWIENIE_EDIT;
	}

	public String editZamowienie(Zamowienie zamowienie){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("zamowienie", zamowienie);
		
		return PAGE_ZAMOWIENIE_EDIT;
	}

	public String deleteZamowienie(Zamowienie zamowienie){
		zamowienieDAO.remove(zamowienie);
		return PAGE_STAY_AT_THE_SAME;
	}
	
}
