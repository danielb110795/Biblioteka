package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import dao.Domain;
import lombok.Data;

@Data
@Entity
public class Ksiazka implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String tytul;
	private String wydawca;
	private String ISBN;
	private String rok_wydania;
	private String stan; //oki, do naprawy, do wyrzucenia
	
	@ManyToOne
	private Autor autor; 
}