package kwiaciarnia_jpa;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the uslugi database table.
 * 
 */
@Entity
@NamedQuery(name="Uslugi.findAll", query="SELECT u FROM Uslugi u")
public class Uslugi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_uslugi")
	private int idUslugi;

	@Column(name="cena_uslugi")
	private double cenaUslugi;

	private String img;

	@Lob
	private String opis;

	private String typ;

	private String usluga;

	public Uslugi() {
	}

	public int getIdUslugi() {
		return this.idUslugi;
	}

	public void setIdUslugi(int idUslugi) {
		this.idUslugi = idUslugi;
	}

	public double getCenaUslugi() {
		return this.cenaUslugi;
	}

	public void setCenaUslugi(double cenaUslugi) {
		this.cenaUslugi = cenaUslugi;
	}

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getUsluga() {
		return this.usluga;
	}

	public void setUsluga(String usluga) {
		this.usluga = usluga;
	}

}