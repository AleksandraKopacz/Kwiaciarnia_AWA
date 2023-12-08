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

import kwiaciarnia.dao.KwiatyDAO;
import kwiaciarnia.jpa.Kwiaty;

@Named
@RequestScoped
public class KwiatyListBB {

	private static final String PAGE_KWIATY_EDIT = "kwiatyEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String kwiat;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	KwiatyDAO kwiatyDAO;

	public String getKwiat() {
		return kwiat;
	}

	public void setKwiat(String kwiat) {
		this.kwiat = kwiat;
	}

	public List<Kwiaty> getFullList(){
		return kwiatyDAO.getFullList();
	}

	public List<Kwiaty> getList(){
		List<Kwiaty> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (kwiat != null && kwiat.length() > 0){
			searchParams.put("kwiat", kwiat);
		}
		
		//2. Get list
		list = kwiatyDAO.getList(searchParams);
		
		return list;
	}

	public String newKwiaty(){
		Kwiaty kwiaty = new Kwiaty();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("kwiaty", kwiaty);
		
		return PAGE_KWIATY_EDIT;
	}

	public String editKwiaty(Kwiaty kwiaty){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("kwiaty", kwiaty);
		
		return PAGE_KWIATY_EDIT;
	}

	public String deleteKwiaty(Kwiaty kwiaty){
		kwiatyDAO.remove(kwiaty);
		return PAGE_STAY_AT_THE_SAME;
	}
	
}
