package kwiaciarnia;

import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import kwiaciarnia.dao.SymboleDAO;
import kwiaciarnia.jpa.Symbole;

@Named
@ViewScoped
public class SymboleEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_SYMBOLE_LIST = "/pages/admin/symboleList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Symbole symbole = new Symbole();
	private Symbole loaded = null;

	@EJB
	SymboleDAO symboleDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Symbole getSymbole() {
		return symbole;
	}

	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (Symbole) flash.get("symbole");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			symbole = loaded;
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

		try {
			if (symbole.getIdSymbole() == 0) {
				// new record
				symboleDAO.create(symbole);
			} else {
				// existing record
				symboleDAO.merge(symbole);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_SYMBOLE_LIST;
	}
}
