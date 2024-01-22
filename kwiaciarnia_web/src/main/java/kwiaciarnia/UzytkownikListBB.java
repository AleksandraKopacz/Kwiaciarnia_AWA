package kwiaciarnia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import kwiaciarnia.dao.UzytkownikDAO;
import kwiaciarnia.jpa.Uzytkownik;

@Named
@RequestScoped
public class UzytkownikListBB {

	private static final String PAGE_UZYTKOWNIK_EDIT = "/pages/admin/uzytkownikEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String email;
	private byte rola;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	UzytkownikDAO uzytkownikDAO;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte getRola() {
		return rola;
	}

	public void setRola(byte rola) {
		this.rola = rola;
	}

	public List<Uzytkownik> getFullList(){
		return uzytkownikDAO.getFullList();
	}

	public List<Uzytkownik> getList(){
		List<Uzytkownik> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (email != null && email.length() > 0){
			searchParams.put("email", email);
		}
		
		searchParams.put("rola", rola);
		
		//2. Get list
		list = uzytkownikDAO.getList(searchParams);
		
		return list;
	}

	public String newUzytkownik(){
		Uzytkownik uzytkownik = new Uzytkownik();

		flash.put("uzytkownik", uzytkownik);
		
		return PAGE_UZYTKOWNIK_EDIT;
	}

	public String editUzytkownik(Uzytkownik uzytkownik){

		flash.put("uzytkownik", uzytkownik);
		
		return PAGE_UZYTKOWNIK_EDIT;
	}

	public String deleteUzytkownik(Uzytkownik uzytkownik){
		uzytkownikDAO.remove(uzytkownik);
		return PAGE_STAY_AT_THE_SAME;
	}
	
}
