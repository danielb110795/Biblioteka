package controller;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.CzytelnikDAO;
import dao.UzytkownikDAO;
import entity.Czytelnik;
import entity.Uzytkownik;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
@Data
@LocalBean
public class UzytkownikController {
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
	
	@Getter
	@Setter
	private String informacjaONowymHasle = "";
	
	private String login;
	private String haslo;
	private String email;
	private String pesel;
	private String adres;
	private String imie;
	private String nazwisko;
	
	public String dodajUzytkownika()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Uzytkownik zalogowanyUzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
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
		czytelnik.setKara("0");
		czytelnik.setUzytkownik(uzytkownik);
		
		Collection<Czytelnik> czytelnicy = czytelnikDAO.findAll();
		for(Czytelnik element : czytelnicy)
		{
			if(element.getUzytkownik().getLogin().equals(login))
			{
				errorMessageEmail = "";
				errorMessageLogin = "U¿ytkownik o podanym loginie ju¿ istnieje";
				return "dodaj_uzytkownika";
			}
			if(element.getEmail().equals(email))
			{
				errorMessageLogin = "";
				errorMessageEmail = "U¿ytkownik o podanym adresie e-mail ju¿ istnieje";
				return "dodaj_uzytkownika";
			}
		}
		
		uzytkownikDAO.save(uzytkownik);
		czytelnikDAO.save(czytelnik); 
		
		if(zalogowanyUzytkownik.getRola().equals("PRACOWNIK"))
			return "uzytkownicy_pracownik";
		else
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

		String idCzyt = (String) session.getAttribute("idUzytkownika");
		
		Long idCzytelnika = Long.parseLong(idCzyt);
		
		
		Czytelnik czytelnik = czytelnikDAO.findOne(idCzytelnika);
		Uzytkownik uzytkownik = uzytkownikDAO.findOne(czytelnik.getUzytkownik().getId());
		
		
		
		Collection<Czytelnik> czytelnicy = czytelnikDAO.findAll();
		czytelnicy.remove(czytelnik);
		for(Czytelnik element : czytelnicy)
		{
			if(element.getUzytkownik().getLogin().equals(login))
			{
				errorMessageEmail = "";
				errorMessageLogin = "U¿ytkownik o podanym loginie ju¿ istnieje";
				return "edytuj_uzytkownika";
			}
			if(element.getEmail().equals(email))
			{
				errorMessageLogin = "";
				errorMessageEmail = "U¿ytkownik o podanym adresie e-mail ju¿ istnieje";
				return "edytuj_uzytkownika";
			}
		}
		
		
		if(login.equals(""))
			uzytkownik.setLogin(uzytkownik.getLogin());
		else
			uzytkownik.setLogin(login);
		if(haslo.equals(""))
			uzytkownik.setHaslo(uzytkownik.getHaslo());
		else
			uzytkownik.setHaslo(haslo);
		
		uzytkownik.setRola("CZYTELNIK");
		
		uzytkownik.setAktywowane(true);
		uzytkownik.setZalogowany(true);
		
		if(imie.equals(""))
			czytelnik.setImie(czytelnik.getImie());
		else
			czytelnik.setImie(imie);
		if(nazwisko.equals(""))
			czytelnik.setNazwisko(czytelnik.getNazwisko());
		else
			czytelnik.setNazwisko(nazwisko);
		
		if(email.equals(""))
			czytelnik.setEmail(czytelnik.getEmail());
		else
			czytelnik.setEmail(email);
		
		if(pesel.equals(""))
			czytelnik.setPesel(czytelnik.getPesel());
		else	
			czytelnik.setPesel(pesel);
		if(adres.equals(""))
			czytelnik.setAdres(czytelnik.getAdres());
		else
			czytelnik.setAdres(adres);
		czytelnik.setKara(czytelnik.getKara());
		czytelnik.setUzytkownik(uzytkownik);
		
		
		
		uzytkownikDAO.save(uzytkownik);
		czytelnikDAO.save(czytelnik); 
		
		return "uzytkownicy_pracownik";
	} 
	
	public String pokazEdycje(String id)
	{
	
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		
		Long idCzytelnika = Long.parseLong(id);
		Czytelnik czytelnik = czytelnikDAO.findOne(idCzytelnika);
		Uzytkownik uzytkownik = uzytkownikDAO.findOne(czytelnik.getUzytkownik().getId());
		
		login = uzytkownik.getLogin();
		haslo = uzytkownik.getHaslo();
		email = czytelnik.getEmail();
		imie = czytelnik.getImie();
		nazwisko = czytelnik.getNazwisko();
		pesel = czytelnik.getPesel();
		adres = czytelnik.getAdres();
		
		session.setAttribute("idUzytkownika", id);
		return "edytuj_uzytkownika";
	}
	
	public String aktywujUzytkownika(String id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Uzytkownik zalogowanyUzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
		Long idUzytkownika = Long.parseLong(id);
		Uzytkownik uzytkownik = uzytkownikDAO.findOne(idUzytkownika);
		uzytkownik.setAktywowane(true);
		uzytkownikDAO.save(uzytkownik);
		if(zalogowanyUzytkownik.getRola().equals("PRACOWNIK"))
			return "uzytkownicy_pracownik";
		else
			return "uzytkownicy";
	}
	
	public String deaktywujUzytkownika(String id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Uzytkownik zalogowanyUzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
		Long idUzytkownika = Long.parseLong(id);
		Uzytkownik uzytkownik = uzytkownikDAO.findOne(idUzytkownika);
		uzytkownik.setAktywowane(false);
		uzytkownikDAO.save(uzytkownik);
		if(zalogowanyUzytkownik.getRola().equals("PRACOWNIK"))
			return "uzytkownicy_pracownik";
		else
			return "uzytkownicy";
	}
	
	public String zmianaHasla()
	{	
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		String idCzyt = (String) session.getAttribute("idUzytkownika");
		
		Long idCzytelnika = Long.parseLong(idCzyt);
		
		
		Czytelnik czytelnik = czytelnikDAO.findOne(idCzytelnika);
		Uzytkownik uzytkownik = uzytkownikDAO.findOne(czytelnik.getUzytkownik().getId());
		
			uzytkownik.setLogin(uzytkownik.getLogin());	
			
			uzytkownik.setHaslo(haslo);
					
			uzytkownik.setRola("CZYTELNIK");
			uzytkownik.setAktywowane(true);
			uzytkownik.setZalogowany(true);
			czytelnik.setImie(czytelnik.getImie());

			czytelnik.setNazwisko(czytelnik.getNazwisko());
			czytelnik.setEmail(czytelnik.getEmail());
		
			czytelnik.setPesel(czytelnik.getPesel());
			czytelnik.setAdres(czytelnik.getAdres());

			czytelnik.setKara(czytelnik.getKara());
			czytelnik.setUzytkownik(uzytkownik);
		
			uzytkownikDAO.save(uzytkownik);
			czytelnikDAO.save(czytelnik); 
			
			informacjaONowymHasle = "Haslo zmieniono pomyœlnie";
			
			return "zmiana_hasla";
	} 
	
	public String usunUzytkownika(String id)
	{
		Long idCzytelnika = Long.parseLong(id);
		czytelnikDAO.remove(idCzytelnika);
		return "uzytkownicy_pracownik";
	}
}
