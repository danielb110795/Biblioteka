package entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@JoinColumn(name = "autor_id")
	private List <Autor> autor; 
	
	@ManyToMany
	@JoinColumn(name = "kategoria_id")
	private List <Kategoria> kategoria; 
	
	@ManyToMany
	@JoinColumn(name = "egzemplarz_id")
	private List <Egzemplarz> egzemplarz; 
	
}