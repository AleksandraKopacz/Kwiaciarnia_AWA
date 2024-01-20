package kwiaciarnia.jpa;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the zamowienie database table.
 * 
 */
@Entity
@NamedQuery(name="Zamowienie.findAll", query="SELECT z FROM Zamowienie z")
public class Zamowienie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_zamowienie")
	private int idZamowienie;

	@Lob
	private String szczegoly;
	
	@Id
	@Column(name="id_uzytkownikFK", nullable = true)
	private int idUzytkownik;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="id_uzytkownikFK", insertable = false, updatable = false, nullable = true)
	private Uzytkownik uzytkownik;

	public Zamowienie() {
	}

	public int getIdZamowienie() {
		return this.idZamowienie;
	}

	public void setIdZamowienie(int idZamowienie) {
		this.idZamowienie = idZamowienie;
	}

	public String getSzczegoly() {
		return this.szczegoly;
	}

	public void setSzczegoly(String szczegoly) {
		this.szczegoly = szczegoly;
	}

	public int getIdUzytkownik() {
		return idUzytkownik;
	}

	public void setIdUzytkownik(int idUzytkownik) {
		this.idUzytkownik = idUzytkownik;
	}

	public Uzytkownik getUzytkownik() {
		return this.uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) {
		this.uzytkownik = uzytkownik;
	}

}