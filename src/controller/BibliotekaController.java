package controller;


import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.BibliotekaDAO;
import dao.DzienDAO;
import dao.PracownikDAO;
import entity.Biblioteka;
import entity.Dzien;
import entity.Pracownik;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
@Data
@LocalBean
public class BibliotekaController {
	
	@EJB
	private BibliotekaDAO bibliotekaDAO;
	
	@EJB
	private PracownikDAO pracownikDAO;
	
	@EJB
	private DzienDAO dzienDAO;
	
	@Getter
	@Setter
	private String errorMessage = "";
	
	private String nazwa;
	private String adres;
	private String numerTel;
	private String urlDoMapyGoogle;
	
	private String pnOtwarcie;
	private String pnZamkniecie;
	private String wtOtwarcie;
	private String wtZamkniecie;
	private String srOtwarcie;
	private String srZamkniecie;
	private String czOtwarcie;
	private String czZamkniecie;
	private String ptOtwarcie;
	private String ptZamkniecie;
	private String sobOtwarcie;
	private String sobZamkniecie;
	
	
	private Biblioteka placowka;
	private Long id;
	
	public String saveBiblioteka()
	{
		Biblioteka biblioteka = new Biblioteka();
		
		biblioteka.setNazwa(nazwa);
		biblioteka.setAdres(adres);
		biblioteka.setNumerTel(numerTel); 
		biblioteka.setUrlDoMapyGoogle(urlDoMapyGoogle);
		List<Dzien> dni = new LinkedList<>();
		Dzien poniedzialek = dzienDAO.save(Dzien.builder().nazwa("Poniedzia³ek").czasOtwarcia(pnOtwarcie).czasZamkniecia(pnZamkniecie).build());
		Dzien wtorek = dzienDAO.save(Dzien.builder().nazwa("Wtorek").czasOtwarcia(wtOtwarcie).czasZamkniecia(wtZamkniecie).build());
		Dzien sroda = dzienDAO.save(Dzien.builder().nazwa("Œroda").czasOtwarcia(srOtwarcie).czasZamkniecia(srZamkniecie).build());
		Dzien czwartek = dzienDAO.save(Dzien.builder().nazwa("Czwartek").czasOtwarcia(czOtwarcie).czasZamkniecia(czZamkniecie).build());
		Dzien piatek = dzienDAO.save(Dzien.builder().nazwa("Pi¹tek").czasOtwarcia(ptOtwarcie).czasZamkniecia(ptZamkniecie).build());
		Dzien sobota = dzienDAO.save(Dzien.builder().nazwa("Sobota").czasOtwarcia(sobOtwarcie).czasZamkniecia(sobZamkniecie).build());
		dni.add(0, poniedzialek);
		dni.add(1, wtorek);
		dni.add(2, sroda);
		dni.add(3, czwartek);
		dni.add(4, piatek);
		dni.add(5, sobota);
		
		biblioteka.setDzien(dni);
		
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
			return "blad_usun_placowke"; 
		}
		
		return "placowki";                
	}
	
	public String getUrlPlacowki() 
	{
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
		
		return "placowki";
	}
	
	public String wypiszZBiblioteki(String id)
	{

		Long idPracownika = Long.parseLong(id);
		Pracownik pracownik = pracownikDAO.findOne(idPracownika);
		pracownik.setBiblioteka(null);
		pracownikDAO.save(pracownik);
		
		return "placowki";
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
		
		
		Long idPlacowki = Long.parseLong(id);
		Biblioteka biblioteka = bibliotekaDAO.findOne(idPlacowki);
		List<Dzien> dni = biblioteka.getDzien();

		nazwa = biblioteka.getNazwa();
		adres = biblioteka.getAdres();
		numerTel = biblioteka.getNumerTel();
		urlDoMapyGoogle = biblioteka.getUrlDoMapyGoogle();
		
		Dzien poniedzialek = dni.get(0);
		pnOtwarcie = poniedzialek.getCzasOtwarcia();
		pnZamkniecie = poniedzialek.getCzasZamkniecia();
		
		Dzien wtorek = dni.get(1);
		wtOtwarcie = wtorek.getCzasOtwarcia();
		wtZamkniecie = wtorek.getCzasZamkniecia();
		
		Dzien sroda = dni.get(2);
		srOtwarcie = sroda.getCzasOtwarcia();
		srZamkniecie = sroda.getCzasZamkniecia();
		
		Dzien czwartek = dni.get(3);
		czOtwarcie = czwartek.getCzasOtwarcia();
		czZamkniecie = czwartek.getCzasZamkniecia();
		
		Dzien piatek = dni.get(4);
		ptOtwarcie = piatek.getCzasOtwarcia();
		ptZamkniecie = piatek.getCzasZamkniecia();
		
		Dzien sobota = dni.get(5);
		sobOtwarcie = sobota.getCzasOtwarcia();
		sobZamkniecie = sobota.getCzasZamkniecia();
		
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
		
		List<Dzien> dni = new LinkedList<>();
		Dzien poniedzialek = dzienDAO.save(Dzien.builder().nazwa("Poniedzia³ek").czasOtwarcia(pnOtwarcie).czasZamkniecia(pnZamkniecie).build());
		Dzien wtorek = dzienDAO.save(Dzien.builder().nazwa("Wtorek").czasOtwarcia(wtOtwarcie).czasZamkniecia(wtZamkniecie).build());
		Dzien sroda = dzienDAO.save(Dzien.builder().nazwa("Œroda").czasOtwarcia(srOtwarcie).czasZamkniecia(srZamkniecie).build());
		Dzien czwartek = dzienDAO.save(Dzien.builder().nazwa("Czwartek").czasOtwarcia(czOtwarcie).czasZamkniecia(czZamkniecie).build());
		Dzien piatek = dzienDAO.save(Dzien.builder().nazwa("Pi¹tek").czasOtwarcia(ptOtwarcie).czasZamkniecia(ptZamkniecie).build());
		Dzien sobota = dzienDAO.save(Dzien.builder().nazwa("Sobota").czasOtwarcia(sobOtwarcie).czasZamkniecia(sobZamkniecie).build());
		dni.add(0, poniedzialek);
		dni.add(1, wtorek);
		dni.add(2, sroda);
		dni.add(3, czwartek);
		dni.add(4, piatek);
		dni.add(5, sobota);
		
		biblioteka.setDzien(dni);
			
		
		bibliotekaDAO.save(biblioteka);
		 
		
		return "placowki";              
	}
}
