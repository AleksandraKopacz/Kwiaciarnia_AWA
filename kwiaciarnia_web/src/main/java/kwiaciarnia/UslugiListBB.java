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

	private static final String PAGE_USLUGI_EDIT = "/pages/admin/uslugiEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private int idUslugi;
	private String usluga;
	private String img;
	private String opis;
	private String typ;
	private String sortuj;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	UslugiDAO uslugiDAO;

	public int getIdUslugi() {
		return idUslugi;
	}

	public void setIdUslugi(int idUslugi) {
		this.idUslugi = idUslugi;
	}

	public String getUsluga() {
		return usluga;
	}

	public void setUsluga(String usluga) {
		this.usluga = usluga;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getSortuj() {
		return sortuj;
	}

	public void setSortuj(String sortuj) {
		this.sortuj = sortuj;
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
		
		if (img != null && img.length() > 0){
			searchParams.put("img", img);
		}
		
		if (opis != null && opis.length() > 0){
			searchParams.put("opis", opis);
		}
		
		if (typ != null){
			searchParams.put("typ", typ);
		}
		
		if (sortuj != null){
			searchParams.put("sortuj", sortuj);
		}
		
		searchParams.put("idUslugi", idUslugi);
		
		//2. Get list
		list = uslugiDAO.getList(searchParams);
		
		return list;
	}

	public String newUslugi(){
		Uslugi uslugi = new Uslugi();

		flash.put("uslugi", uslugi);
		
		return PAGE_USLUGI_EDIT;
	}

	public String editUslugi(Uslugi uslugi){
		flash.put("uslugi", uslugi);
		
		return PAGE_USLUGI_EDIT;
	}

	public String deleteUslugi(Uslugi uslugi){
		uslugiDAO.remove(uslugi);
		return PAGE_STAY_AT_THE_SAME;
	}
	
}
