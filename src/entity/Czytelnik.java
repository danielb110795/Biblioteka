package entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import dao.Domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data   
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Czytelnik implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
	private String imie;
	private String nazwisko;
	private String pesel;
	private String email;
	private String adres;
	private Long kara;

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "uzytkownik")
	private Uzytkownik uzytkownik;
	
	@ManyToMany
	@JoinColumn(name = "wypozyczenie_id")
	private List <Wypozyczenie> wypozyczenia;
}