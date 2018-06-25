package entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import dao.Domain;
import lombok.Data;

@Data
@Entity
public class Wypozyczenie implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataWypozyczenia;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataOddania; 
	
	@ManyToOne
	private Egzemplarz egzemplarz; 
}