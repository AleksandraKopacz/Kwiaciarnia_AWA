package kwiaciarnia;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import kwiaciarnia.dao.UzytkownikDAO;
import kwiaciarnia.jpa.Uzytkownik;

@Named
@ViewScoped
public class UzytkownikEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_UZYTKOWNIK_LIST = "/pages/admin/uzytkownikList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Uzytkownik uzytkownik = new Uzytkownik();
	private Uzytkownik loaded = null;

	@EJB
	UzytkownikDAO uzytkownikDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}

	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (Uzytkownik) flash.get("uzytkownik");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			uzytkownik = loaded;
			// session.removeAttribute("person");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("personList.xhtml");
			// context.responseComplete();
			// }
		}

	}

	public String saveData() {
		// no Person object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}
		
		List<Uzytkownik> list = null;
		Map<String,Object> searchParams = new HashMap<String, Object>();
		searchParams.put("email", uzytkownik.getEmail());		
		list = uzytkownikDAO.getList(searchParams);
		
		try {
			if (uzytkownik.getIdUzytkownik() == 0) {
				// new record
				if(!list.isEmpty()) {
					context.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Użytkownik o takim adresie e-mail już istnieje", null));
					return PAGE_STAY_AT_THE_SAME;
				} else {
					uzytkownikDAO.create(uzytkownik);
					return PAGE_UZYTKOWNIK_LIST;
				}
			} else {
				// existing record
				uzytkownikDAO.merge(uzytkownik);
				return PAGE_UZYTKOWNIK_LIST;
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}
	}
	
	public void registerOnLoad() {
		Uzytkownik uzytkownik = new Uzytkownik();
	}
	
	public String registerUzytkownik() {
		
		List<Uzytkownik> list = null;
		Map<String,Object> searchParams = new HashMap<String, Object>();
		searchParams.put("email", uzytkownik.getEmail());		
		list = uzytkownikDAO.getList(searchParams);
		
		try {
			if(!list.isEmpty()) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Użytkownik o takim adresie e-mail już istnieje", null));
			} else {
				uzytkownikDAO.create(uzytkownik);
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Konto zostało utworzone, możesz teraz zalogować się", null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_STAY_AT_THE_SAME;
		
	}
}
