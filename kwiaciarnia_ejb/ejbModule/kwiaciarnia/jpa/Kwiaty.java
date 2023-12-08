package kwiaciarnia.jpa;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the kwiaty database table.
 * 
 */
@Entity
@NamedQuery(name="Kwiaty.findAll", query="SELECT k FROM Kwiaty k")
public class Kwiaty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_kwiaty")
	private int idKwiaty;

	private double cena;

	private String img;

	@Lob
	private String kolory;

	private String kwiat;

	//bi-directional many-to-many association to Symbole
	@ManyToMany(mappedBy="kwiaties")
	private List<Symbole> symboles;

	public Kwiaty() {
	}

	public int getIdKwiaty() {
		return this.idKwiaty;
	}

	public void setIdKwiaty(int idKwiaty) {
		this.idKwiaty = idKwiaty;
	}

	public double getCena() {
		return this.cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getKolory() {
		return this.kolory;
	}

	public void setKolory(String kolory) {
		this.kolory = kolory;
	}

	public String getKwiat() {
		return this.kwiat;
	}

	public void setKwiat(String kwiat) {
		this.kwiat = kwiat;
	}

	public List<Symbole> getSymboles() {
		return this.symboles;
	}

	public void setSymboles(List<Symbole> symboles) {
		this.symboles = symboles;
	}

}