package controller;

import java.util.NoSuchElementException;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.UzytkownikDAO;
import entity.Uzytkownik;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Stateless
@LocalBean
@Named
public class AutoryzacjaController {

	@Getter
	@Setter
	private Autoryzacja autoryzacja = new Autoryzacja();

	@Getter
	@Setter
	private String errorMessage = "";
	
	@Getter
	@Setter
	private String url = "moje_konto.xhtml";

	@Getter
	@Setter
	private Uzytkownik nowyUzytkownik = new Uzytkownik();

	@EJB
	private UzytkownikDAO uzytkownikDAO;

	public boolean czyZalogowany() {
		return getUzytkownik() != null;
	}

	public Uzytkownik getUzytkownik() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		return (Uzytkownik) session.getAttribute("uzytkownik");
	}

	public String addNewUser() {
		nowyUzytkownik.setZalogowany(true);
		uzytkownikDAO.save(nowyUzytkownik);
		return "Dodano";
	}

	@Data
	public class Autoryzacja {
		private String login;
		private String haslo;
	}

	public String zaloguj() {
		if(autoryzacja.getLogin().isEmpty() || autoryzacja.getHaslo().isEmpty())
		{
			errorMessage = "Wype³nij wszystkie pola";
			return "moje_konto";
		}
		Uzytkownik uzytkownik;
		try {
		uzytkownik = uzytkownikDAO.findByQuery(Uzytkownik.builder().login(autoryzacja.getLogin())
				.haslo(autoryzacja.getHaslo()).rola("").zalogowany(true).aktywowane(true).build()).iterator().next();
		}catch(NoSuchElementException e)
		{
			errorMessage = "Bledny login lub haslo";
			return "moje_konto";
		}
		if (uzytkownik == null) {
			errorMessage = "Bledny login lub haslo";
			return "moje_konto";
		}
		else
		{
			url = "profil_czytelnika.xhtml";
		}

		if (!uzytkownik.isZalogowany()) {
			errorMessage = "User o login " + uzytkownik.getLogin() + " jest nieaktywny";
			return "moje_konto"; // TODO
		}
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		session.setAttribute("uzytkownik", uzytkownik);

		return "profil_czytelnika"; // TODO udalo sie
	}

	public String sprawdzCzyZalogowany()
	{
		String login;
		if(czyZalogowany()==true)
		{
			
			login = getUzytkownik().getLogin();
			return login;	
		}
		login = "Moje konto";
		return login;
	}
	public String getWyloguj()
	{
		return "wyloguj.xhtml";
	}
	public String wyloguj() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.removeAttribute("uzytkownik");
		url = "moje_konto.xhtml";
		errorMessage = "";
		return "moje_konto.xhtml"; 
	}

}