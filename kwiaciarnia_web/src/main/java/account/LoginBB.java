package account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kwiaciarnia.dao.UzytkownikDAO;
import kwiaciarnia.jpa.Uzytkownik;
import kwiaciarnia.jpa.Zamowienie;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Named
@RequestScoped
public class LoginBB {
	private static final String PAGE_LOGIN = "/login";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static String NEXT_PAGE = null;

	private String login;
	private String pass;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Inject
	UzytkownikDAO uzytkownikDAO;
	
	@Inject
	Flash flash;

	public String doLogin() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		// 1. verify login and password - get Uzytkownik from "database"
		Map<String, Object> searchParams = new HashMap<String, Object>();

		searchParams.put("email", login);
		searchParams.put("haslo", pass);

		Uzytkownik uzytkownik = uzytkownikDAO.getUserFromDatabase(searchParams);

		// 2. if bad login or password - stay with error info
		if (uzytkownik == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niepoprawny e-mail lub hasło", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		// 3. if logged in: get Uzytkownik roles, save in RemoteClient and store it in
		// session

		RemoteClient<Uzytkownik> client = new RemoteClient<Uzytkownik>(); // create new RemoteClient
		client.setDetails(uzytkownik);

		List<String> roles = uzytkownikDAO.getUserRolesFromDatabase(uzytkownik); // get Uzytkownik roles

		if (roles != null) { // save roles in RemoteClient
			for (String role : roles) {
				if (role.equals("1")) {
					client.getRoles().add(role);
					NEXT_PAGE = "/pages/admin/kwiatyList";
					break;
				}
				if (role.equals("0")) {
					client.getRoles().add(role);
					NEXT_PAGE = "/pages/user/zamowienie";
					break;
				}
			}
		}

		// store RemoteClient with request info in session (needed for SecurityFilter)
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		client.store(request);

		// and enter the system (now SecurityFilter will pass the request)
		return NEXT_PAGE;
	}

	public String doLogout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		// Invalidate session
		// - all objects within session will be destroyed
		// - new session will be created (with new ID)
		session.invalidate();
		return PAGE_LOGIN;
	}

}
