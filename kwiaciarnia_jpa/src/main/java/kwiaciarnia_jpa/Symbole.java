package kwiaciarnia_jpa;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the symbole database table.
 * 
 */
@Entity
@NamedQuery(name="Symbole.findAll", query="SELECT s FROM Symbole s")
public class Symbole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_symbole")
	private int idSymbole;

	private String symbol;

	//bi-directional many-to-many association to Kwiaty
	@ManyToMany
	@JoinTable(
		name="symbole_kwiaty"
		, joinColumns={
			@JoinColumn(name="id_symbole")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_kwiaty")
			}
		)
	private List<Kwiaty> kwiaties;

	public Symbole() {
	}

	public int getIdSymbole() {
		return this.idSymbole;
	}

	public void setIdSymbole(int idSymbole) {
		this.idSymbole = idSymbole;
	}

	public String getSymbol() {
		return this.symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public List<Kwiaty> getKwiaties() {
		return this.kwiaties;
	}

	public void setKwiaties(List<Kwiaty> kwiaties) {
		this.kwiaties = kwiaties;
	}

}