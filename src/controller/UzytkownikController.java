package controller;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.CzytelnikDAO;
import dao.UzytkownikDAO;
import entity.Czytelnik;
import entity.Uzytkownik;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class UzytkownikController {
	@EJB
	private UzytkownikDAO uzytkownikDAO;
	
	@EJB
	private CzytelnikDAO czytelnikDAO;
	
	private String login;
	private String haslo;
	private String email;
	private String pesel;
	private String adres;
	private String imie;
	private String nazwisko;
	
	public String dodajUzytkownika()
	{
		Czytelnik czytelnik = new Czytelnik();
		Uzytkownik uzytkownik = new Uzytkownik();
		
		uzytkownik.setLogin(login);
		uzytkownik.setHaslo(haslo);
		uzytkownik.setRola("CZYTELNIK");
		uzytkownik.setAktywowane(true);
		uzytkownik.setZalogowany(true);
		czytelnik.setImie(imie);
		czytelnik.setNazwisko(nazwisko);
		czytelnik.setEmail(email);
		czytelnik.setPesel(pesel);
		czytelnik.setAdres(adres);
		czytelnik.setKara(0);
		czytelnik.setUzytkownik(uzytkownik);
		
		uzytkownikDAO.save(uzytkownik);
		czytelnikDAO.save(czytelnik); 
		
		return "uzytkownicy";
	}
	
	public List<Czytelnik> pokazUzytkownikow()
	{
		List<Czytelnik> czytelnicy = new LinkedList<>();
		czytelnicy = czytelnikDAO.findAll();
		
		return czytelnicy;
	}
	
	public String edytujUzytkownika()
	{
		
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		String idCzyt = (String) session.getAttribute("id");
		Long idCzytelnika = Long.parseLong(idCzyt);
		
		Czytelnik czytelnik = czytelnikDAO.findOne(idCzytelnika);
		Uzytkownik uzytkownik = uzytkownikDAO.findOne(czytelnik.getUzytkownik().getId());
		
		//if(!login.equals(""))
			uzytkownik.setLogin(login);
		//if(!haslo.equals(""))
			uzytkownik.setHaslo(haslo);

		uzytkownik.setRola("CZYTELNIK");
		uzytkownik.setAktywowane(true);
		uzytkownik.setZalogowany(true);
		
		czytelnik.setImie(imie);
		czytelnik.setNazwisko(nazwisko);
		czytelnik.setEmail(email);
		czytelnik.setPesel(pesel);
		czytelnik.setAdres(adres);
		czytelnik.setKara(0);
		czytelnik.setUzytkownik(uzytkownik);
		
		uzytkownikDAO.save(uzytkownik);
		czytelnikDAO.save(czytelnik); 
		
		return "uzytkownicy_pracownik";
	}
	public String pokazEdycje(String id)
	{
	
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		session.setAttribute("id", id);
		return "edytuj_uzytkownika.xhtml";
	}
	
	
}
