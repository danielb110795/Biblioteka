package controller;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.AutorDAO;
import dao.BibliotekaDAO;
import dao.CzytelnikDAO;
import dao.EgzemplarzDAO;
import dao.KategoriaDAO;
import dao.KsiazkaDAO;
import dao.WydanieDAO;
import dao.WydawnictwoDAO;
import dao.WypozyczenieDAO;
import entity.Czytelnik;
import entity.Egzemplarz;
import entity.Ksiazka;
import entity.Wypozyczenie;
import lombok.Data;

@RequestScoped
@Named
@Data
@LocalBean
public class WypozyczeniaController {
	@EJB
	private KategoriaDAO kategoriaDAO;
	
	@EJB
	private AutorDAO autorDAO;
	
	@EJB
	private WydanieDAO wydanieDAO;
	
	@EJB
	private KsiazkaDAO ksiazkaDAO;
	
	@EJB
	private CzytelnikDAO czytelnikDAO;
	
	@EJB
	private WydawnictwoDAO wydawnictwoDAO;
	
	@EJB
	private EgzemplarzDAO egzemplarzDAO;
	
	@EJB
	private BibliotekaDAO bibliotekaDAO;
	
	@EJB
	private WypozyczenieDAO wypozyczenieDAO;
	
	public String pokazUzytkownikow(String id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Long idE = Long.parseLong(id);
		Egzemplarz egzemplarz = egzemplarzDAO.findOne(idE);
		session.setAttribute("egzemplarz", egzemplarz);
		
		return "wypozyczenie_uzytkownicy";
	}
	public String wypozycz(String id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Long idU = Long.parseLong(id);
		Czytelnik czytelnik = czytelnikDAO.findOne(idU);
		Egzemplarz egzemplarz = (Egzemplarz) session.getAttribute("egzemplarz");
		Wypozyczenie wypozyczenie = new Wypozyczenie();
		egzemplarz.setStatus("WYPO¯YCZONY");
		List<Wypozyczenie> wypozyczenia = new LinkedList<Wypozyczenie>();
		if(czytelnik.getWypozyczenia() != null || czytelnik.getWypozyczenia().isEmpty() == false)
		{
			wypozyczenia = wypozyczenieDAO.findAll();
		}
		wypozyczenie.setEgzemplarz(egzemplarz);
		Date date = new Date();
		wypozyczenie.setDataWypozyczenia(date);
		wypozyczenia.add(wypozyczenie);
		czytelnik.setWypozyczenia(wypozyczenia);
		return "spis_ksiazek";
	}
	public String pokazTMP()
	{
		return "ksiazki";
	}
}