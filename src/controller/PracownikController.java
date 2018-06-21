package controller;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import dao.PracownikDAO;
import dao.UzytkownikDAO;
import entity.Pracownik;
import entity.Uzytkownik;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class PracownikController {
	@EJB
	private PracownikDAO pracownikDAO;
	
	@EJB
	private UzytkownikDAO uzytkownikDAO;
	
	private String imie;
	private String nazwisko;
	private String pesel;
	private String email;
	private String adres;
	private String login;
	private String haslo;
	
	
	public String dodajPracownika()
	{
		Pracownik pracownik = new Pracownik();
		Uzytkownik uzytkownik = new Uzytkownik();
		
		uzytkownik.setLogin(login);
		uzytkownik.setHaslo(haslo);
		uzytkownik.setRola("PRACOWNIK");
		uzytkownik.setZalogowany(true);
		uzytkownik.setAktywowane(true);
		
		pracownik.setImie(imie);
		pracownik.setNazwisko(nazwisko);
		pracownik.setAdres(adres);
		pracownik.setPesel(pesel);
		pracownik.setEmail(email);
		pracownik.setUzytkownik(uzytkownik);
		
		uzytkownikDAO.save(uzytkownik);
		pracownikDAO.save(pracownik);
		
		return "pracownicy";
	}
	
	public List<Pracownik> pokazPracownikow()
	{
		List<Pracownik> pracownicy = new LinkedList<>();
		pracownicy = pracownikDAO.findAll();
		
		return pracownicy;
	}
}
