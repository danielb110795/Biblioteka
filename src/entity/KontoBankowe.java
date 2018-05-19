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
public class KontoBankowe implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String numer_konta;
	private String nazwa_banku;
	private String adres;
	
	@ManyToOne
	private Biblioteka biblioteka; 
}