package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import dao.Domain;
import lombok.Data;

@Entity
@Data

public class Egzemplarz implements Domain{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long numerEgz; 
	private String status; //wypozyczony, zarezerwowany, dostepny
	
		
	@ManyToOne
	private Wydanie wydanie; 
	
	
	
}
