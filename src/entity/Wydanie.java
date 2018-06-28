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
public class Wydanie implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nazwa; 
	private String miejsceWydania;
	private String rokWydania;
	private String ISBN;
	
	@ManyToOne
	private Wydawnictwo wydawnictwo; 
}