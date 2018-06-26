package controller;

import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.CzytelnikDAO;
import entity.Czytelnik;
import entity.Uzytkownik;
import lombok.Data;

@RequestScoped
@Named
@Data
@LocalBean
public class ProfilController {
	
	@EJB
	private CzytelnikDAO czytelnikDAO;
	
	private AutoryzacjaController autoController;
	
	private String imie = "";
	private String nazwisko = "";
	private String pesel = "";
	private String email = "";
	private String adres = "";
	private String kara = "0";
	private int aktWypozyczen = 0;
	private int sumWypozyczen = 0;
	

	/*public String przeanalizujProfil() {
	
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Uzytkownik uzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
		if(uzytkownik == null)
			return "uzytkownik null";
		
		
		Collection<Czytelnik> czytelnicy = czytelnikDAO.findAll();
		if(czytelnicy.isEmpty() == true)
			return "brak czytelnikow";
		for (Czytelnik element : czytelnicy) {
			if ((element.getUzytkownik().getId().equals(uzytkownik.getId())))
			{
				nazwisko = element.getNazwisko();
				pesel = ""+element.getPesel()+"";
				adres = element.getAdres();
				kara = ""+element.getKara()+"";
				if(element.getKara()==null || element.getKara().equals("0"))
					kara = "Brak";
	
				return ""+element.getImie()+"";
			}
		}
		return "nie jestes czytelnikiem";	
	}*/
	
	
	public String getImie() {
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Uzytkownik uzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
		
		if(uzytkownik == null)
			return "moje_konto";
		
		Collection<Czytelnik> czytelnicy = czytelnikDAO.findAll();
		
		for (Czytelnik element : czytelnicy) {
			if ((element.getUzytkownik().getId().equals(uzytkownik.getId()))) {	
				imie = element.getImie();
				nazwisko = element.getNazwisko();
				email = element.getEmail();
				pesel = element.getPesel();
				kara = element.getKara();
				adres = element.getAdres();
				aktWypozyczen = element.getWypozyczenia().size();
				sumWypozyczen = element.getWypozyczenia().size();
			}
		}
		return imie;	
	}
	
	
	/*public String getNazwisko()
	{	
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Uzytkownik uzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
		String nazwiskoCzyt = "";
		Collection<Czytelnik> czytelnicy = czytelnikDAO.findAll();
		
		for (Czytelnik element : czytelnicy) {
			if ((element.getUzytkownik().getId().equals(uzytkownik.getId()))) {	
				nazwiskoCzyt = element.getNazwisko();
			}
		}
		return nazwiskoCzyt;	
	}*/
	
	
	
}
