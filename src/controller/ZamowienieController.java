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
import dao.ZamowienieDAO;
import entity.Pracownik;
import entity.Uzytkownik;
import entity.Zamowienie;
import lombok.Data;

@RequestScoped
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
	private String ilosc;
	
	
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
		zamowienie.setStatus("Z£O¯ONE");
		
		zamowienieDAO.save(zamowienie);
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
		if(uzytkownik.getRola().equals("PRACOWNIK"))
			return "zamowienia_pracownik.xhtml";
		else
			return "zamowienia_admin.xhtml";
	}
	
}
