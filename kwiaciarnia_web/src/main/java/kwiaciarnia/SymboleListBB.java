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

import kwiaciarnia.dao.SymboleDAO;
import kwiaciarnia.jpa.Symbole;

@Named
@RequestScoped
public class SymboleListBB {

	private static final String PAGE_SYMBOLE_EDIT = "/pages/admin/symboleEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String symbol;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	SymboleDAO symboleDAO;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public List<Symbole> getFullList(){
		return symboleDAO.getFullList();
	}

	public List<Symbole> getList(){
		List<Symbole> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (symbol != null && symbol.length() > 0){
			searchParams.put("symbol", symbol);
		}
		
		//2. Get list
		list = symboleDAO.getList(searchParams);
		
		return list;
	}

	public String newSymbole(){
		Symbole symbole = new Symbole();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("symbole", symbole);
		
		return PAGE_SYMBOLE_EDIT;
	}

	public String editSymbole(Symbole symbole){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("symbole", symbole);
		
		return PAGE_SYMBOLE_EDIT;
	}

	public String deleteSymbole(Symbole symbole){
		symboleDAO.remove(symbole);
		return PAGE_STAY_AT_THE_SAME;
	}
	
}
