package controller;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import dao.CzytelnikDAO;
import dao.UzytkownikDAO;
import entity.Czytelnik;
import entity.Uzytkownik;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class RejestracjaController {
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
	
	
	public String zarejestruj()
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
		czytelnik.setUzytkownk(uzytkownik);
		
		uzytkownikDAO.save(uzytkownik);
		czytelnikDAO.save(czytelnik); 
		
		return "strona_glowna";
	}
	
}
