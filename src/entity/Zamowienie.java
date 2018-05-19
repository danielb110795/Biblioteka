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
public class Zamowienie implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer cena;
	
	@ManyToOne
	private Ksiazka ksiazka; 
	
	@ManyToOne
	private Dostawca dostawca;

}