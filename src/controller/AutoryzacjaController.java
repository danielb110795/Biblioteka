package controller;

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
	private String errorMessage;

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
		nowyUzytkownik.setCzyZalogowany(true);
		uzytkownikDAO.save(nowyUzytkownik);
		return "Dodano";
	}

	@Data
	public class Autoryzacja {
		private String login;
		private String haslo;
	}

	public String zaloguj() {
		//tutaj usunalem czyCzyzalogowany(true). U nas kazdy mia³ false i nie znajdowa³o dobrego uzytkownika
		Uzytkownik uzytkownik = uzytkownikDAO.findByQuery(Uzytkownik.builder().login(autoryzacja.getLogin())
				.haslo(autoryzacja.getHaslo()).build()).iterator().next();
		if (uzytkownik == null) {
			errorMessage = "Bledny login lub haslo";
			return null;
		}

		if (!uzytkownik.isCzyZalogowany()) {
			errorMessage = "User o login " + uzytkownik.getLogin() + " jest nieaktywny";
			return null; // TODO
		}

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		session.setAttribute("uzytkownik", uzytkownik);

		return null; // TODO udalo sie
	}

	public String wyloguj() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.removeAttribute("user");
		return null; // TODO strona logowania
	}

}