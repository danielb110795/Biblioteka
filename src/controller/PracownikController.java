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

import dao.PracownikDAO;
import dao.UzytkownikDAO;
import entity.Pracownik;
import entity.Uzytkownik;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
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
	
	@Getter
	@Setter
	private String errorMessageLogin = "";
	
	@Getter
	@Setter
	private String errorMessageEmail = "";
	
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
		
		Collection<Pracownik> pracownicy = pracownikDAO.findAll();
		for(Pracownik element : pracownicy)
		{
			if(element.getUzytkownik().getLogin().equals(login))
			{
				errorMessageEmail = "";
				errorMessageLogin = "Pracownik o podanym loginie ju¿ istnieje";
				return "dodaj_pracownika";
			}
			if(element.getEmail().equals(email))
			{
				errorMessageLogin = "";
				errorMessageEmail = "Pracownik o podanym adresie e-mail ju¿ istnieje";
				return "dodaj_pracownika";
			}
		}
		
		
		
		//uzytkownikDAO.save(uzytkownik);
		pracownikDAO.save(pracownik);
		
		return "pracownicy";
	}
	
	public List<Pracownik> pokazPracownikow()
	{
		List<Pracownik> pracownicy = new LinkedList<>();
		pracownicy = pracownikDAO.findAll();
		
		return pracownicy;
	}
	
	public String edytujPracownika()
	{
		
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		String idPrac = (String) session.getAttribute("idPracownika");
		
		Long idPracownika = Long.parseLong(idPrac);
		
		
		Pracownik pracownik = pracownikDAO.findOne(idPracownika);
		Uzytkownik uzytkownik = uzytkownikDAO.findOne(pracownik.getUzytkownik().getId());
		
		Collection<Pracownik> pracownicy = pracownikDAO.findAll();
		pracownicy.remove(pracownik);
		for(Pracownik element : pracownicy)
		{
			if(element.getUzytkownik().getLogin().equals(login))
			{
				errorMessageEmail = "";
				errorMessageLogin = "Pracownik o podanym loginie ju¿ istnieje";
				return "edytuj_pracownika";
			}
			if(element.getEmail().equals(email))
			{
				errorMessageLogin = "";
				errorMessageEmail = "Pracownik o podanym adresie e-mail ju¿ istnieje";
				return "edytuj_pracownika";
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
		
		uzytkownik.setRola("PRACOWNIK");
		
		uzytkownik.setAktywowane(true);
		uzytkownik.setZalogowany(true);
		
		if(imie.equals(""))
			pracownik.setImie(pracownik.getImie());
		else
			pracownik.setImie(imie);
		if(nazwisko.equals(""))
			pracownik.setNazwisko(pracownik.getNazwisko());
		else
			pracownik.setNazwisko(nazwisko);
		
		if(email.equals(""))
			pracownik.setEmail(pracownik.getEmail());
		else
			pracownik.setEmail(email);
		
		if(pesel.equals(""))
			pracownik.setPesel(pracownik.getPesel());
		else	
			pracownik.setPesel(pesel);
		if(adres.equals(""))
			pracownik.setAdres(pracownik.getAdres());
		else
			pracownik.setAdres(adres);
		
		pracownik.setBiblioteka(pracownik.getBiblioteka());
		
		pracownik.setUzytkownik(uzytkownik);
		
		uzytkownikDAO.save(uzytkownik);
		pracownikDAO.save(pracownik); 
		
		return "pracownicy";
	}
	
	public String pokazEdycje(String id)
	{
	
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		Long idPracownika = Long.parseLong(id);
		Pracownik pracownik = pracownikDAO.findOne(idPracownika);
		Uzytkownik uzytkownik = uzytkownikDAO.findOne(pracownik.getUzytkownik().getId());
		
		login = uzytkownik.getLogin();
		haslo = uzytkownik.getHaslo();
		email = pracownik.getEmail();
		imie = pracownik.getImie();
		nazwisko = pracownik.getNazwisko();
		pesel = pracownik.getPesel();
		adres = pracownik.getAdres();
		
		session.setAttribute("idPracownika", id);
		return "edytuj_pracownika.xhtml";
	}
	
	public String usunPracownika(String id)
	{
		Long idPracownika = Long.parseLong(id);
		pracownikDAO.remove(idPracownika);
		return "pracownicy";
	}
}
