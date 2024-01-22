package kwiaciarnia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.servlet.http.HttpSession;

import kwiaciarnia.dao.KwiatyDAO;
import kwiaciarnia.jpa.Kwiaty;

@Named
@RequestScoped
public class KwiatyListBB {

	private static final String PAGE_KWIATY_EDIT = "/pages/admin/kwiatyEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String kwiat;
	private String kolory;
	private String img;
	private String sortuj;
	private int idKwiaty;

	@Inject
	ExternalContext extcontext;
	
	@Inject
	FacesContext context;
	
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
	
	public String getKolory() {
		return kolory;
	}

	public void setKolory(String kolory) {
		this.kolory = kolory;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public String getSortuj() {
		return sortuj;
	}

	public void setSortuj(String sortuj) {
		this.sortuj = sortuj;
	}

	public int getIdKwiaty() {
		return idKwiaty;
	}

	public void setIdKwiaty(int idKwiaty) {
		this.idKwiaty = idKwiaty;
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
		
		if (img != null && img.length() > 0){
			searchParams.put("img", img);
		}
		
		if (kolory != null && kolory.length() > 0){
			searchParams.put("kolory", kolory);
		}
		
		if (sortuj != null) {
			searchParams.put("sortuj", sortuj);
		}
		
		searchParams.put("idKwiaty", idKwiaty);
		
		//2. Get list
		list = kwiatyDAO.getList(searchParams);
		
		return list;
	}

	public String newKwiaty(){
		Kwiaty kwiaty = new Kwiaty();
		
		flash.put("kwiaty", kwiaty);
		
		return PAGE_KWIATY_EDIT;
	}

	public String editKwiaty(Kwiaty kwiaty){

		flash.put("kwiaty", kwiaty);
		
		return PAGE_KWIATY_EDIT;
	}

	public String deleteKwiaty(Kwiaty kwiaty){
		kwiatyDAO.remove(kwiaty);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
}
