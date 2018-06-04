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
	private String numerTel;
	private String urlDoMapyGoogle;
	
	private Biblioteka placowka;;
	private Long id;
	
	public String saveBiblioteka()
	{
		Biblioteka biblioteka = new Biblioteka();
		
		biblioteka.setNazwa(nazwa);
		biblioteka.setAdres(adres);
		biblioteka.setNumerTel(numerTel);
		biblioteka.setUrlDoMapyGoogle(urlDoMapyGoogle);
		
		bibliotekaDAO.save(biblioteka);
		
		return "placowki";
	}
	
	
	public List<Biblioteka> pokazPlacowki()
	{
		List<Biblioteka> placowki = new LinkedList<>();
		placowki = bibliotekaDAO.findAll();	
		return placowki;
	}
	
	public String getNumer(String idBiblioteki)
	{
		int id = Integer.parseInt(idBiblioteki);
		placowka = bibliotekaDAO.findOne((long)id);	
		return placowka.getNumerTel();                
	}
	
	public String getUrlDoMapyGoogle(String idBiblioteki)
	{
		int id = Integer.parseInt(idBiblioteki);
		placowka = bibliotekaDAO.findOne((long)id);	
		return placowka.getUrlDoMapyGoogle();                
	}
	
}
