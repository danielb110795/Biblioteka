package controller;

import java.util.LinkedList;
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
	
	private Biblioteka placowka;;
	private Long id;
	
	public String saveBiblioteka()
	{
		Biblioteka biblioteka = new Biblioteka();
		
		biblioteka.setNazwa(nazwa);
		biblioteka.setAdres(adres);
		
		bibliotekaDAO.save(biblioteka);
		
		return "placowki";
	}
	
	
	public List<Biblioteka> pokazPlacowki()
	{
		List<Biblioteka> placowki = new LinkedList<>();
		
		placowki = bibliotekaDAO.findAll();
		
		return placowki;
	}
	
	public String pokazKontakt()
	{
		
		placowka = bibliotekaDAO.findOne(id);
		
		return placowka.getNazwa();
	}
	
}
