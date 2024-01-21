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

import kwiaciarnia.dao.UzytkownikDAO;
import kwiaciarnia.jpa.Uzytkownik;

@Named
@RequestScoped
public class ZamowienieListBB {
	
	private static final long serialVersionUID = 1L;

	private static final String PAGE_ZAMOWIENIE_EDIT = "/pages/admin/zamowienieEdit?faces-redirect=true";
	private static final String PAGE_ZAMOWIENIE_NEW = "/pages/admin/zamowienieNew?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String szczegoly;
	private int idZamowienie;
	private int idUzytkownik;
		
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

	public int getIdZamowienie() {
		return idZamowienie;
	}

	public void setIdZamowienie(int idZamowienie) {
		this.idZamowienie = idZamowienie;
	}

	public int getIdUzytkownik() {
		return idUzytkownik;
	}

	public void setIdUzytkownik(int idUzytkownik) {
		this.idUzytkownik = idUzytkownik;
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
		
		searchParams.put("idZamowienie", idZamowienie);
		
		//2. Get list
		list = zamowienieDAO.getList(searchParams);
		
		return list;
	}
	
	public List<Zamowienie> getOrderList() {
		List<Zamowienie> uzytkownikList = null;
		uzytkownikList = (List<Zamowienie>) flash.get("uzytkownikList");
		return uzytkownikList;
	}

	public String newZamowienie(){
		Zamowienie zamowienie = new Zamowienie();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("zamowienie", zamowienie);
		
		return PAGE_ZAMOWIENIE_NEW;
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
