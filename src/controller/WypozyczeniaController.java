package controller;


import java.util.Calendar;
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
import entity.Uzytkownik;
import entity.Wypozyczenie;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
	
	@Getter
	@Setter
	private String karaMessage = "";
	
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
		Long idC = Long.parseLong(id);
		Czytelnik czytelnik = czytelnikDAO.findOne(idC);
		Egzemplarz egzemplarz = (Egzemplarz) session.getAttribute("egzemplarz");
		Wypozyczenie wypozyczenie = new Wypozyczenie();
		egzemplarz.setStatus("WYPO¯YCZONY");
		List<Wypozyczenie> wypozyczenia = new LinkedList<Wypozyczenie>();
		//List<Wypozyczenie> wypozyczeniaCzyt = new LinkedList<Wypozyczenie>();
		if(czytelnik.getWypozyczenia() != null || czytelnik.getWypozyczenia().isEmpty() == false)
		{
			wypozyczenia = czytelnik.getWypozyczenia();
		}
		wypozyczenie.setEgzemplarz(egzemplarz);
		Date date = new Date();
		wypozyczenie.setDataWypozyczenia(date);
		wypozyczenia.add(wypozyczenie);
		czytelnik.setWypozyczenia(wypozyczenia);
		
		egzemplarzDAO.save(egzemplarz);
		wypozyczenieDAO.save(wypozyczenie);
		czytelnikDAO.save(czytelnik);
		return "spis_ksiazek";
	}
	
	public String zwroc(String id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Long idE = Long.parseLong(id);
		Long idC = (long)session.getAttribute("idCzytelnika");
		
		Czytelnik czytelnik = czytelnikDAO.findOne(idC);
		Egzemplarz egzemplarz = egzemplarzDAO.findOne(idE);
		Wypozyczenie wypozyczenie = new Wypozyczenie();
		egzemplarz.setStatus("DOSTEPNY");
		List<Wypozyczenie> wypozyczenia = new LinkedList<Wypozyczenie>();
		if(czytelnik.getWypozyczenia() != null || czytelnik.getWypozyczenia().isEmpty() == false)
		{
			wypozyczenia = czytelnik.getWypozyczenia();
			for(Wypozyczenie element : wypozyczenia)
			{
				if(element.getEgzemplarz().getISBN().equals(egzemplarz.getISBN()) && element.getDataOddania() == null)
				{
					wypozyczenie = element;
					break;
				}
			}
		}
		wypozyczenie.setEgzemplarz(egzemplarz);
		
		Date data = new Date();
		wypozyczenie.setDataOddania(data);
		
		Date dataWyp = wypozyczenie.getDataWypozyczenia();
		
		long roznica = zwrocRozniceSekund(dataWyp); //roznica w sekundach ile minelo czasu od wypozyczenai do oddania
		
		Long karaPoprzednia = czytelnik.getKara();
		
		if (roznica < 60) {
			karaMessage = "Ksi¹¿ke oddano na czas. Nie naliczono kary.";
			czytelnik.setKara(karaPoprzednia);
		} else {
			Long karaNowa = karaPoprzednia + (roznica - (long)60);
			czytelnik.setKara(karaNowa);
			karaMessage = "Naliczono op³atê! Poinformuj i tym czytelnika. Kara za oddan¹ ksi¹¿ke wynosi: " 
					+ (roznica - (long)60) + ". Kara ogólna: " + karaNowa;
		}
		
		
		czytelnikDAO.save(czytelnik);
		wypozyczenieDAO.save(wypozyczenie);
		egzemplarzDAO.save(egzemplarz);
		
		return "wypozyczenia";
	}
	public List<Wypozyczenie> pokazHistorieWypozyczen()
		{
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			Uzytkownik uzytkownik = (Uzytkownik) session.getAttribute("uzytkownik");
			List<Czytelnik> czytelnicy = czytelnikDAO.findAll();
			Czytelnik czytelnik = new Czytelnik();
			for(Czytelnik element : czytelnicy)
			{
				if(element.getUzytkownik().getId() == uzytkownik.getId())
				{
					czytelnik = element;
				}
			}
			
			List<Wypozyczenie> wypozyczenia = new LinkedList<>();
			
			wypozyczenia = czytelnik.getWypozyczenia();
			List<Wypozyczenie> wypozyczeniaAktualne = new LinkedList<>();
	
			for(Wypozyczenie element : wypozyczenia)
			{
				if(element.getDataOddania() != null)
				{
					wypozyczeniaAktualne.add(element);
				}
			}
			
			return wypozyczeniaAktualne;
		}
		public List<Wypozyczenie> pokazWypozyczeniaCzytelnikowi()
		{
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			Uzytkownik uzytkownik = (Uzytkownik) session.getAttribute("uzytkownik");
			List<Czytelnik> czytelnicy = czytelnikDAO.findAll();
			Czytelnik czytelnik = new Czytelnik();
			for(Czytelnik element : czytelnicy)
			{
				if(element.getUzytkownik().getId() == uzytkownik.getId())
				{
					czytelnik = element;
				}
			}
			
			List<Wypozyczenie> wypozyczenia = new LinkedList<>();
			
			wypozyczenia = czytelnik.getWypozyczenia();
			List<Wypozyczenie> wypozyczeniaAktualne = new LinkedList<>();
	
			for(Wypozyczenie element : wypozyczenia)
			{
				if(element.getDataOddania() == null)
				{
					wypozyczeniaAktualne.add(element);
				}
			}
			
			return wypozyczeniaAktualne;
		}
	
	public long zwrocRozniceSekund (Date datWyp) {
		
		//aktualna data i konwersja na Calendar
		Date data = new Date();
		Calendar aktualnaData = Calendar.getInstance();
		aktualnaData.setTime(data);
		
		//konwersja daty wypozyczenia
		Calendar dataWypozyczenia = Calendar.getInstance();
		dataWypozyczenia.setTime(datWyp);
		

		
		long roznicaMilisekund =  aktualnaData.getTimeInMillis() - dataWypozyczenia.getTimeInMillis();
		Calendar resultMilisekund = Calendar.getInstance();
		resultMilisekund.setTimeInMillis(roznicaMilisekund);

		long roznicaSekund = resultMilisekund.getTimeInMillis() / 1000;

		return roznicaSekund;
	}
	
	
	
	
	public String doRenowacji(String id)
	{
		Long idE = Long.parseLong(id);
		Egzemplarz egzemplarz = egzemplarzDAO.findOne(idE);
		
		egzemplarz.setStatus("RENOWACJA");
		
		egzemplarzDAO.save(egzemplarz);
		return "spis_ksiazek";
	}
	public String zRenowacji(String id)
	{
		Long idE = Long.parseLong(id);
		Egzemplarz egzemplarz = egzemplarzDAO.findOne(idE);
		
		egzemplarz.setStatus("DOSTEPNY");
		
		egzemplarzDAO.save(egzemplarz);
		return "spis_ksiazek";
	}
	public String przejdzDoWypozyczen(String id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Long idC = Long.parseLong(id);
		session.setAttribute("idCzytelnika", idC);
		return "wypozyczenia";
	}
	public List<Wypozyczenie> pokazWypozyczenia()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Long idC = (long) session.getAttribute("idCzytelnika");
		Czytelnik czytelnik = czytelnikDAO.findOne(idC);
		List<Wypozyczenie> wypozyczenia = new LinkedList<>();
		
		wypozyczenia = czytelnik.getWypozyczenia();
		List<Wypozyczenie> wypozyczeniaAktualne = new LinkedList<>();

		for(Wypozyczenie element : wypozyczenia)
		{
			if(element.getDataOddania() == null)
			{
				wypozyczeniaAktualne.add(element);
			}
		}
		
		return wypozyczeniaAktualne;
	}
	public Ksiazka pokazKsiazkiWypozyczenia(String id)
	{
		Ksiazka ksiazka = new Ksiazka();
		Long idE = Long.parseLong(id);
		Egzemplarz egzemplarz = egzemplarzDAO.findOne(idE);
		List<Ksiazka> ksiazki = ksiazkaDAO.findAll();
		
		for(Ksiazka elementKsiazka : ksiazki)
		{
			List<Egzemplarz> egzemplarze = elementKsiazka.getEgzemplarz();
			for(Egzemplarz elementEgzemplarz : egzemplarze)
			{
				if(egzemplarz.getId() == elementEgzemplarz.getId())
				{
					ksiazka = elementKsiazka;
					break;
				}
			}
		}
	
		return ksiazka;
	}
}