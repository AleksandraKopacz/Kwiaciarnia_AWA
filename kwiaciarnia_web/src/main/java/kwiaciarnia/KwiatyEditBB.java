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

import kwiaciarnia.dao.KwiatyDAO;
import kwiaciarnia.jpa.Kwiaty;

@Named
@ViewScoped
public class KwiatyEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_KWIATY_LIST = "pages/admin/kwiatyList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Kwiaty kwiaty = new Kwiaty();
	private Kwiaty loaded = null;

	@EJB
	KwiatyDAO kwiatyDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Kwiaty getKwiaty() {
		return kwiaty;
	}

	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (Kwiaty) flash.get("kwiaty");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			kwiaty = loaded;
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
			if (kwiaty.getIdKwiaty() == 0) {
				// new record
				kwiatyDAO.create(kwiaty);
			} else {
				// existing record
				kwiatyDAO.merge(kwiaty);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_KWIATY_LIST;
	}
}
