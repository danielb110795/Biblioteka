package controller;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.inject.Named;

import dao.CzytelnikDAO;
import dao.UzytkownikDAO;
import entity.Czytelnik;
import entity.Uzytkownik;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.enterprise.context.RequestScoped;

@RequestScoped
@Named
@Data
@LocalBean

public class RejestracjaController {
	@EJB
	private UzytkownikDAO uzytkownikDAO;
	@EJB
	private CzytelnikDAO czytelnikDAO;
	
	@Getter
	@Setter
	private String errorMessageLogin = "";
	
	@Getter
	@Setter
	private String errorMessageEmail = "";
	
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
		uzytkownik.setAktywowane(false);
		uzytkownik.setZalogowany(true);
		czytelnik.setImie(imie);
		czytelnik.setNazwisko(nazwisko);
		czytelnik.setEmail(email);
		czytelnik.setPesel(pesel);
		czytelnik.setAdres(adres);
		czytelnik.setKara("0");
		czytelnik.setUzytkownik(uzytkownik);
		
		Collection<Czytelnik> czytelnicy = czytelnikDAO.findAll();
		for(Czytelnik element : czytelnicy)
		{
			if(element.getUzytkownik().getLogin().equals(login))
			{
				errorMessageEmail = "";
				errorMessageLogin = "U¿ytkownik o podanym loginie ju¿ istnieje";
				return "nowe_konto";
			}
			if(element.getEmail().equals(email))
			{
				errorMessageLogin = "";
				errorMessageEmail = "U¿ytkownik o podanym adresie e-mail ju¿ istnieje";
				return "nowe_konto";
			}
		}
		
		uzytkownikDAO.save(uzytkownik);
		czytelnikDAO.save(czytelnik); 
		
		return "info";
	}
	
}
