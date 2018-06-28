package controller;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.PracownikDAO;
import dao.WydawnictwoDAO;
import dao.ZamowienieDAO;
import entity.Pracownik;
import entity.Uzytkownik;
import entity.Wydawnictwo;
import entity.Zamowienie;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
@Data
@LocalBean
public class ZamowienieController {
	@EJB
	private ZamowienieDAO zamowienieDAO;
	
	@EJB
	PracownikDAO pracownikDAO;
	
	@EJB
	WydawnictwoDAO wydawnictwoDAO;
	
	@Getter
	@Setter
	private String akceptujZamowienieMessage = "";
	
	@Getter
	@Setter
	private String zakonczZamowienieMessage = "";
	
	@Getter
	@Setter
	private String odrzucZamowienieMessage = "";
	
	@Getter
	@Setter
	private String usunZamowienieMessage = "";
	
	@Getter
	@Setter
	private String zlozZamowienieMessage = "";
	
	private String tytul;
	private String wydanie;
	private String opis;
	private String ilosc;
	private String miejsceWydania;
	private String ISBN;
	private String rokWydania;
	private Long idWydawnictwa;
	private String nrWydania;
	
	public String zlozZamowienie()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Uzytkownik uzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
		Pracownik tenPracownik = new Pracownik();
		List<Pracownik> pracownicy = new LinkedList<>();
		pracownicy = pracownikDAO.findAll();
		for (Pracownik pracownik : pracownicy) {
			if (pracownik.getUzytkownik().getId() == uzytkownik.getId())
			{
				tenPracownik = pracownik;
				break;
			}
		}
		
		Zamowienie zamowienie = new Zamowienie();
		zamowienie.setTytul(tytul);
		zamowienie.setISBN(ISBN);
		zamowienie.setMiejsceWydania(miejsceWydania);
		Long ilosc2 = Long.parseLong(ilosc);
		zamowienie.setIlosc(ilosc2);
		zamowienie.setOpis(opis);
		Long nrWydania2 = Long.parseLong(nrWydania);
		zamowienie.setNrWydania(nrWydania2);
		zamowienie.setRokWydania(rokWydania);
		Wydawnictwo wydawnictwo = wydawnictwoDAO.findOne(idWydawnictwa);
		zamowienie.setWydawnictwo(wydawnictwo);
		zamowienie.setWydawnictwo(wydawnictwo);
		zamowienie.setBiblioteka(tenPracownik.getBiblioteka());
		zamowienie.setStatus("Z£O¯ONE");
		
		zamowienieDAO.save(zamowienie);
		
		zlozZamowienieMessage = "Z³o¿ono zamówienie.";
		
		return "zamowienia_pracownik";
	}
	
	public List<Zamowienie> pokazZamowienia()
	{
		List<Zamowienie> zamowienia = new LinkedList<>();
		zamowienia = zamowienieDAO.findAll();
		
		return zamowienia;
	}
	
	public String usun(String id)
	{
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Uzytkownik uzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
		Long idZam = Long.parseLong(id);
		
		usunZamowienieMessage = "Usunieto zamówienie.";
		
		zamowienieDAO.remove(idZam);
		if(uzytkownik.getRola().equals("PRACOWNIK"))
			return "zamowienia_pracownik.xhtml";
		else
			return "zamowienia_admin.xhtml";
	}
	public String zakoncz(String id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Uzytkownik uzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
		Long idZam = Long.parseLong(id);
		Zamowienie zamowienie = zamowienieDAO.findOne(idZam);
		zamowienie.setStatus("ZAKOÑCZONE");
		zamowienieDAO.save(zamowienie);
		
		usunZamowienieMessage = "Zakoñczono zamówienie.";
		
		if(uzytkownik.getRola().equals("PRACOWNIK"))
			return "zamowienia_pracownik.xhtml";
		else
			return "zamowienia_admin.xhtml";
	}
	public String akceptuj(String id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Uzytkownik uzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
		Long idZam = Long.parseLong(id);
		Zamowienie zamowienie = zamowienieDAO.findOne(idZam);
		zamowienie.setStatus("ZAAKCEPTOWANE");
		zamowienieDAO.save(zamowienie);
		
		akceptujZamowienieMessage = "Akceptowano zamówienie. Tytu³: " + zamowienie.getTytul() +".";
		
		if(uzytkownik.getRola().equals("PRACOWNIK"))
			return "zamowienia_pracownik.xhtml";
		else
			return "zamowienia_admin.xhtml";
	}
	public String odrzuc(String id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Uzytkownik uzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
		Long idZam = Long.parseLong(id);
		Zamowienie zamowienie = zamowienieDAO.findOne(idZam);
		zamowienie.setStatus("ODRZUCONE");
		zamowienieDAO.save(zamowienie);
		
		odrzucZamowienieMessage = "Odrzucono zamówienie. Tytu³: " + zamowienie.getTytul() +".";
		
		if(uzytkownik.getRola().equals("PRACOWNIK"))
			return "zamowienia_pracownik.xhtml";
		else
			return "zamowienia_admin.xhtml";
	}
	
}
