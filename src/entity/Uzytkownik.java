package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import dao.Domain;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Uzytkownik implements Domain{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String login;
	private String haslo; 
	private String rola;
	private boolean zalogowany; 
	private boolean aktywowane;

}