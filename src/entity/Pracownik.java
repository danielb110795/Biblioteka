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
public class Pracownik implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String imie;
	private String nazwisko;
	private String pesel;
	private String wynagrodzenie;
	private String email;

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "uzytownik")
	private Uzytkownik uzytkownk;
	
	@ManyToOne  //wiele pracownikow w jednej bibliotece
	private Biblioteka biblioteka; 
}