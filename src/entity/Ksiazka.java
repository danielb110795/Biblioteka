package entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import dao.Domain;
import lombok.Data;

@Data
@Entity
public class Ksiazka implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String tytul;
	private String opis;
	private String stan; //oki, do naprawy, do wyrzucenia
	private String zdjecie;
	
	@ManyToMany
	private List <Autor> autor; 
	
	@ManyToMany
	private List <Kategoria> kategoria; //lita kategorii do jakich nale¿y ksi¹¿ka
	
}