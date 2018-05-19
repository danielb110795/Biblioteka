package entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import dao.Domain;
import lombok.Data;

@Data
@Entity
public class Wypozyczenie implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date data_wypozyczenia;
	private Date data_oddania; 
	
	@ManyToOne
	private Czytelnik czytelnik; 
	
	@ManyToOne
	private Ksiazka ksiazka; 
}