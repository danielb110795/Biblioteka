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
		Kategoria kategoria = new Kategoria();
		
		kategoria.setNazwa(nazwa);
		
		kategoriaDAO.save(kategoria);
		
		return "kategoria";
	}
}
