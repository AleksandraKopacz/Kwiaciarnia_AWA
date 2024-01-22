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

import kwiaciarnia.dao.UslugiDAO;
import kwiaciarnia.jpa.Uslugi;

@Named
@ViewScoped
public class UslugiEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_USLUGI_LIST = "/pages/admin/uslugiList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Uslugi uslugi = new Uslugi();
	private Uslugi loaded = null;

	@EJB
	UslugiDAO uslugiDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Uslugi getUslugi() {
		return uslugi;
	}

	public void onLoad() throws IOException {

		loaded = (Uslugi) flash.get("uslugi");

		if (loaded != null) {
			uslugi = loaded;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
		}

	}

	public String saveData() {
		// no Person object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (uslugi.getIdUslugi() == 0) {
				// new record
				uslugiDAO.create(uslugi);
			} else {
				// existing record
				uslugiDAO.merge(uslugi);
			}
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Rekord został zapisany", null));
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_USLUGI_LIST;
	}
}
