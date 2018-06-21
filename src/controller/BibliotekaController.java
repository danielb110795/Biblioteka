package controller;


import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.BibliotekaDAO;
import dao.PracownikDAO;
import entity.Biblioteka;
import entity.Pracownik;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Stateless
@Named
@Data
@LocalBean
public class BibliotekaController {
	
	@EJB
	private BibliotekaDAO bibliotekaDAO;
	
	@EJB
	private PracownikDAO pracownikDAO;
	
	@Getter
	@Setter
	private String errorMessage = "";
	
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
		
		return "placowki.xhtml";
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
	
	public String usunPlacowke(String id) 
	{
		Long idPlacowki = Long.parseLong(id);
		try {
		bibliotekaDAO.remove(idPlacowki);
		}catch(Throwable e)
		{
			errorMessage = "Przed usuniêciem usuñ pracwoników z danej biblioteki";
		}
		
		return "placowki";                
	}
	
	public String przypiszPracownika(String id)
	{
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		session.setAttribute("idPlacowki",id);
		
		return "przypisanie_pracownika";
	}
	
	public String przypiszDoBiblioteki(String id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		String idPlacowki = (String) session.getAttribute("idPlacowki");
		
		Long idPracownika = Long.parseLong(id);
		Long idPlac = Long.parseLong(idPlacowki);
		Pracownik pracownik = pracownikDAO.findOne(idPracownika);
		Biblioteka biblioteka = bibliotekaDAO.findOne(idPlac);
		pracownik.setBiblioteka(biblioteka);
		pracownikDAO.save(pracownik);
		
		return "pracownicy";
	}
	
	public List<Pracownik> znajdzPracownikow()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		String idPlacowki = (String) session.getAttribute("idPlacowki");
		Long idPlac = Long.parseLong(idPlacowki);
		List<Pracownik> pracownicy = new LinkedList<>();
		pracownicy = pracownikDAO.findAll();
		
		List<Pracownik> pracownicyPlacowki = new LinkedList<>();
		
		for(Pracownik element : pracownicy)
		{
			if(element.getBiblioteka() != null)
			{
				if(element.getBiblioteka().getId() == idPlac)
					pracownicyPlacowki.add(element);
			}
		}
		
		return pracownicyPlacowki;
	}
	
	public String pokazEdycje(String id)
	{
	
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if(id.equals(""))
			return "strona_glowna.xhtml";
		session.setAttribute("idPlacowki", id);
		
		return "edytuj_placowke.xhtml";
	}
	
	public String pokazSzczegoly(String id)
	{
	
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if(id.equals(""))
			return "strona_glowna.xhtml";
		session.setAttribute("idPlacowki", id);
		
		return "placowka_szczegoly";
	}
	
	public String edytujPlacowke() 
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		String idPlac = (String) session.getAttribute("idPlacowki");
		if(idPlac.equals(""))
			return "nowe_konto.xhtml";
		Long idPlacowki = Long.parseLong(idPlac);
		
		Biblioteka biblioteka = bibliotekaDAO.findOne(idPlacowki);
		
		if(nazwa.equals(""))
			biblioteka.setNazwa(biblioteka.getNazwa());
		else
			biblioteka.setNazwa(nazwa);
		
		if(adres.equals(""))
			biblioteka.setAdres(biblioteka.getAdres());
		else
			biblioteka.setAdres(adres);
		
		
		if(numerTel.equals(""))
			biblioteka.setNumerTel(biblioteka.getNumerTel());
		else
			biblioteka.setNumerTel(numerTel);
		if(urlDoMapyGoogle.equals(""))
			biblioteka.setUrlDoMapyGoogle(biblioteka.getUrlDoMapyGoogle());
		else
			biblioteka.setUrlDoMapyGoogle(urlDoMapyGoogle);
		
		bibliotekaDAO.save(biblioteka);
		 
		
		return "placowki";              
	}
}
