package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import dao.Domain;
import lombok.Data;

@Data
@Entity 
public class Kategoria implements Domain {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nazwa;
}