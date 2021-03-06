package entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import dao.Domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pracownik implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String imie;
	private String nazwisko;
	private String pesel;
	private String email;
	private String adres;
	
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "uzytkownik")
	private Uzytkownik uzytkownik;
	
	@ManyToOne
	private Biblioteka biblioteka; 
}