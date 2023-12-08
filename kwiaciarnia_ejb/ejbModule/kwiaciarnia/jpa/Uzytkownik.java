package kwiaciarnia.jpa;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the uzytkownik database table.
 * 
 */
@Entity
@NamedQuery(name="Uzytkownik.findAll", query="SELECT u FROM Uzytkownik u")
public class Uzytkownik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_uzytkownik")
	private int idUzytkownik;

	private String email;

	private String haslo;

	private byte rola;

	//bi-directional many-to-one association to Zamowienie
	@OneToMany(mappedBy="uzytkownik")
	private List<Zamowienie> zamowienies;

	public Uzytkownik() {
	}

	public int getIdUzytkownik() {
		return this.idUzytkownik;
	}

	public void setIdUzytkownik(int idUzytkownik) {
		this.idUzytkownik = idUzytkownik;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHaslo() {
		return this.haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public byte getRola() {
		return this.rola;
	}

	public void setRola(byte rola) {
		this.rola = rola;
	}

	public List<Zamowienie> getZamowienies() {
		return this.zamowienies;
	}

	public void setZamowienies(List<Zamowienie> zamowienies) {
		this.zamowienies = zamowienies;
	}

	public Zamowienie addZamowieny(Zamowienie zamowieny) {
		getZamowienies().add(zamowieny);
		zamowieny.setUzytkownik(this);

		return zamowieny;
	}

	public Zamowienie removeZamowieny(Zamowienie zamowieny) {
		getZamowienies().remove(zamowieny);
		zamowieny.setUzytkownik(null);

		return zamowieny;
	}

}