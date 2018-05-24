package controller;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import dao.KategoriaDAO;
import entity.Kategoria;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class KategoriaController {
	@EJB
	private KategoriaDAO kategoriaDAO;
	
	private String nazwa;
	
	public String saveKategoria() {
		System.out.println("JESTEM TUTAJ!!");
		Kategoria kategoria = new Kategoria();
		
		kategoria.setNazwa(nazwa);
		
		kategoriaDAO.save(kategoria);
		
		return "kategoria";
	}
	public String showKategoria() {
		Kategoria kategoria = kategoriaDAO.findOne((long) 1);
		String nazwaKat = kategoria.getNazwa();
		return nazwaKat;
	}
}
