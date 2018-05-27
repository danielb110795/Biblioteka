package controller;

import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.CzytelnikDAO;
import entity.Czytelnik;
import entity.Uzytkownik;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class ProfilController {
	
	@EJB
	private CzytelnikDAO czytelnikDAO;
	
	private AutoryzacjaController autoController;
	
	private String nrKarty = "";
	private String nazwisko = "";
	private String pesel = "";
	private String adres = "";
	private String kara = "";
	private Long aktWypozyczen = (long) 0;
	private Long sumWypozyczen = (long) 0;
	

	public String przeanalizujProfil() {
	
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Uzytkownik uzytkownik =  (Uzytkownik) session.getAttribute("uzytkownik");
		if(uzytkownik == null)
			return "uzytkownik null";
		
		
		Collection<Czytelnik> czytelnicy = czytelnikDAO.findAll();
		if(czytelnicy.isEmpty() == true)
			return "brak czytelnikow";
		for (Czytelnik element : czytelnicy) {
			if ((element.getUzytkownk().getId().equals(uzytkownik.getId())))
			{
				nazwisko = element.getNazwisko();
				pesel = ""+element.getPesel()+"";
				adres = element.getAdres();
				kara = ""+element.getKara()+"";
				if(element.getKara()==null)
					kara = "Brak";
				nrKarty = ""+element.getId()+""+element.getPesel()+"";
				aktWypozyczen = (long) 0;
				sumWypozyczen = (long) 0;
				return ""+element.getImie()+"";
			}
		}
		return "nie jestes czytelnikiem";
		
	}
	
}
