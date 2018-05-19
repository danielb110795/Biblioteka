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
import lombok.Data;

@Data
@Entity
public class Czytelnik implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String imie;
	private String nazwisko;
	private String pesel;
	private String adres;
	private Integer kara;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "uzytownik")
	private Uzytkownik uzytkownk;
	
	@ManyToOne  //wiele czytelnikow w jednej bibliotece
	private Biblioteka biblioteka; 
}