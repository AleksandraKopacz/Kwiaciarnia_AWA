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

import kwiaciarnia.dao.UslugiDAO;
import kwiaciarnia.jpa.Uslugi;

@Named
@RequestScoped
public class UslugiListBB {

	private static final String PAGE_USLUGI_EDIT = "uslugiEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String usluga;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	UslugiDAO uslugiDAO;

	public String getUsluga() {
		return usluga;
	}

	public void setUsluga(String usluga) {
		this.usluga = usluga;
	}

	public List<Uslugi> getFullList(){
		return uslugiDAO.getFullList();
	}

	public List<Uslugi> getList(){
		List<Uslugi> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (usluga != null && usluga.length() > 0){
			searchParams.put("usluga", usluga);
		}
		
		//2. Get list
		list = uslugiDAO.getList(searchParams);
		
		return list;
	}

	public String newUslugi(){
		Uslugi uslugi = new Uslugi();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("uslugi", uslugi);
		
		return PAGE_USLUGI_EDIT;
	}

	public String editUslugi(Uslugi uslugi){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("uslugi", uslugi);
		
		return PAGE_USLUGI_EDIT;
	}

	public String deleteUslugi(Uslugi uslugi){
		uslugiDAO.remove(uslugi);
		return PAGE_STAY_AT_THE_SAME;
	}
	
}
