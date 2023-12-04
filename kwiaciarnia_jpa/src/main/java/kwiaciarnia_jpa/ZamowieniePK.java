package kwiaciarnia_jpa;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the zamowienie database table.
 * 
 */
@Embeddable
public class ZamowieniePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_zamowienie")
	private int idZamowienie;

	@Column(name="id_uzytkownik", insertable=false, updatable=false)
	private int idUzytkownik;

	public ZamowieniePK() {
	}
	public int getIdZamowienie() {
		return this.idZamowienie;
	}
	public void setIdZamowienie(int idZamowienie) {
		this.idZamowienie = idZamowienie;
	}
	public int getIdUzytkownik() {
		return this.idUzytkownik;
	}
	public void setIdUzytkownik(int idUzytkownik) {
		this.idUzytkownik = idUzytkownik;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ZamowieniePK)) {
			return false;
		}
		ZamowieniePK castOther = (ZamowieniePK)other;
		return 
			(this.idZamowienie == castOther.idZamowienie)
			&& (this.idUzytkownik == castOther.idUzytkownik);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idZamowienie;
		hash = hash * prime + this.idUzytkownik;
		
		return hash;
	}
}