package controller;

import java.util.List;

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
	
	
	public List<Biblioteka> znajdzWszystkieBiblioteki() {
		return bibliotekaDAO.findAll();
	}
	
	/*public String wyswietlBiblioteki() {
		Collection <Biblioteka> biblioteki = bibliotekaDAO.findAll();
		
		String nazwa;
		
		if(biblioteki.isEmpty()) {
			nazwa="Brak bibiotek";
		}

		
		
		return nazwaKat;
	}*/
}
