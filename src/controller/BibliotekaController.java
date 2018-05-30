package controller;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import dao.BibliotekaDAO;
import entity.Biblioteka;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class BibliotekaController {
	
	@EJB
	private BibliotekaDAO bibliotekaDAO;
	
	private String nazwa;
	private String adres;
	
	
	public String saveBiblioteka()
	{
		Biblioteka biblioteka = new Biblioteka();
		
		biblioteka.setNazwa(nazwa);
		biblioteka.setAdres(adres);
		
		bibliotekaDAO.save(biblioteka);
		
		return "strona_glowna";
	}
	
}
