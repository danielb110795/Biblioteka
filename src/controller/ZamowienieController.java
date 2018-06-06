package controller;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.PracownikDAO;
import dao.ZamowienieDAO;
import entity.Pracownik;
import entity.Uzytkownik;
import entity.Zamowienie;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class ZamowienieController {
	@EJB
	private ZamowienieDAO zamowienieDAO;
	
	@EJB
	PracownikDAO pracownikDAO;
	
	private String tytul;
	private String wydanie;
	private String opis;
	private Integer ilosc;
	
	
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
		zamowienie.setWydanie(wydanie);
		zamowienie.setIlosc(ilosc);
		zamowienie.setOpis(opis);
		zamowienie.setBiblioteka(tenPracownik.getBiblioteka());
		zamowienie.setStatus("ZAAKCEPTOWANE");
		
		zamowienieDAO.save(zamowienie);
		return "zamowienia_pracownik";
	}
	
	public List<Zamowienie> pokazZamowienia()
	{
		List<Zamowienie> zamowienia = new LinkedList<>();
		zamowienia = zamowienieDAO.findAll();
		
		return zamowienia;
	}
	
	public Boolean sprawdzStatusAkcept(Long id)
	{
		Zamowienie zamowienie = zamowienieDAO.findOne(id);
		if(zamowienie.getStatus().equals("ZAAKCEPTOWANE"))
			return true;
		else
			return false;
	}
	
	public Boolean sprawdzStatusOdrzucone(Long id)
	{
		Zamowienie zamowienie = zamowienieDAO.findOne(id);
		if(zamowienie.getStatus().equals("ODRZUCONE"))
			return true;
		else
			return false;
	}
	
	public Boolean sprawdzStatusZlozone(Long id)
	{
		Zamowienie zamowienie = zamowienieDAO.findOne(id);
		if(zamowienie.getStatus().equals("Z£O¯ONE"))
			return true;
		else
			return false;
	}
	
	public Boolean sprawdzStatusZak(Long id)
	{
		Zamowienie zamowienie = zamowienieDAO.findOne(id);
		if(zamowienie.getStatus().equals("ZAKOÑCZONE"))
			return true;
		else
			return false;
	}
	
	public String usun(Long id)
	{
		zamowienieDAO.remove(id);
		return "zamowienia_pracownik.xhtml";
	}
	public String zakoncz(Long id)
	{
		Zamowienie zamowienie = zamowienieDAO.findOne(id);
		zamowienie.setStatus("ZAKOÑCZONE");
		zamowienieDAO.save(zamowienie);
		return "zamowienia_pracownik.xhtml";
	}
	public String akceptuj(Long id)
	{
		Zamowienie zamowienie = zamowienieDAO.findOne(id);
		zamowienie.setStatus("ZAAKCEPTOWANE");
		zamowienieDAO.save(zamowienie);
		return "zamowienia_pracownik.xhtml";
	}
	public String odrzuc(Long id)
	{
		Zamowienie zamowienie = zamowienieDAO.findOne(id);
		zamowienie.setStatus("ODRZUCONE");
		zamowienieDAO.save(zamowienie);
		return "zamowienia_pracownik.xhtml";
	}
}
