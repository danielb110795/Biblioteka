package controller;

import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.CzytelnikDAO;
import dao.EgzemplarzDAO;
import dao.PracownikDAO;
import dao.UzytkownikDAO;
import entity.Czytelnik;
import entity.Egzemplarz;
import entity.Pracownik;
import entity.Uzytkownik;
import lombok.Data;

@RequestScoped
@Named
@Data
@LocalBean
public class ProfilController {
	
	@EJB
	private CzytelnikDAO czytelnikDAO;
	
	@EJB
	private EgzemplarzDAO egzemplarzDAO;
	
	@EJB
	private PracownikDAO pracownikDAO;
	
	@EJB
	private UzytkownikDAO uzytkownikDAO;
	
	private AutoryzacjaController autoController;
	
	private String imie = "";
	private String nazwisko = "";
	private String pesel = "";
	private String email = "";
	private String adres = "";
	private Long kara = (long)0;
	private int aktWypozyczen = 0;
	private int sumWypozyczen = 0;
	private int iloscKsiazek = 0;
	private int zarejestrowaniUzytkownicy = 0;
	

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
	
	
	public String ustawProfilCzytelnika() {
		
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
	
	
	public String ustawProfilPracownika() {
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Uzytkownik uzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
		
		if(uzytkownik == null)
			return "moje_konto";
		
		Collection<Pracownik> pracownicy = pracownikDAO.findAll();
		 
		for (Pracownik element : pracownicy) {
			if ((element.getUzytkownik().getId().equals(uzytkownik.getId()))) {	
				imie = element.getImie();
				nazwisko = element.getNazwisko();
				email = element.getEmail();
				pesel = element.getPesel();
				adres = element.getAdres();
				
				Collection<Egzemplarz> egzemplarze = egzemplarzDAO.findAll();
				iloscKsiazek = egzemplarze.size();
				
				Collection<Uzytkownik> uzytkownicy = uzytkownikDAO.findAll();
				zarejestrowaniUzytkownicy = uzytkownicy.size();
			}
		}
		return imie;	
	}
	

	
		public String ustawProfilAdministratora() {
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Uzytkownik uzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
		
		if(uzytkownik == null)
			return "moje_konto";
		
		Collection<Pracownik> pracownicy = pracownikDAO.findAll();
		 
		for (Pracownik element : pracownicy) {
			if ((element.getUzytkownik().getId().equals(uzytkownik.getId()))) {	
				imie = element.getImie();
				nazwisko = element.getNazwisko();
				email = element.getEmail();
				pesel = element.getPesel();
				adres = element.getAdres();
				
				Collection<Egzemplarz> egzemplarze = egzemplarzDAO.findAll();
				iloscKsiazek = egzemplarze.size();
				
				Collection<Uzytkownik> uzytkownicy = uzytkownikDAO.findAll();
				zarejestrowaniUzytkownicy = uzytkownicy.size();
			}
		}
		return imie;	
	}
	
}
