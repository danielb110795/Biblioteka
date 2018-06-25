package email;

import java.util.Collection;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dao.CzytelnikDAO;
import dao.UzytkownikDAO;
import entity.Czytelnik;
import entity.Uzytkownik;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Stateless
@Data
@Named
@LocalBean

public class OdzyskajHasloController {
	
	
	@EJB
	private UzytkownikDAO uzytkownikDAO;
	@EJB
	private CzytelnikDAO czytelnikDAO;
	@Getter
	@Setter
	private String errorMessageEmail = "";
	public String noweHaslo = null;
	
    public OdzyskajHasloController() {
    }
    
	String addressFrom = "paibiblioteka@gmail.com";

	public String generujHaslo () 
	{
        Random rand = new Random();
        char los = (char)(rand.nextInt(26)+97);
        char los2 = (char)(rand.nextInt(26)+97);
        char los3 = (char)(rand.nextInt(26)+97);
        char los4 = (char)(rand.nextInt(26)+65);
        char los5 = (char)(rand.nextInt(26)+65);
        char los6 = (char)(rand.nextInt(26)+65);
        char los7 = (char)(rand.nextInt(10)+48);
        char los8 = (char)(rand.nextInt(10)+48);
        char los9 = (char)(rand.nextInt(10)+48);
        
        String haslo = String.valueOf(los)+ String.valueOf(los4) + String.valueOf(los7) +
        				String.valueOf(los2)+ String.valueOf(los5) + String.valueOf(los8) +
        				String.valueOf(los3)+ String.valueOf(los6) + String.valueOf(los9);
        return haslo;
	}
	
	public void edytujHasloUzytkownika(long idCzytelnika, String noweHaslo)
	{	
		
		Czytelnik czytelnik = czytelnikDAO.findOne(idCzytelnika);
		Uzytkownik uzytkownik = uzytkownikDAO.findOne(czytelnik.getUzytkownik().getId());
		
			uzytkownik.setLogin(uzytkownik.getLogin());	
			
			uzytkownik.setHaslo(noweHaslo);
					
			uzytkownik.setRola("CZYTELNIK");
			uzytkownik.setAktywowane(true);
			uzytkownik.setZalogowany(true);
			czytelnik.setImie(czytelnik.getImie());

			czytelnik.setNazwisko(czytelnik.getNazwisko());
			czytelnik.setEmail(czytelnik.getEmail());
		
			czytelnik.setPesel(czytelnik.getPesel());
			czytelnik.setAdres(czytelnik.getAdres());

			czytelnik.setKara(czytelnik.getKara());
			czytelnik.setUzytkownik(uzytkownik);
		
			uzytkownikDAO.save(uzytkownik);
			czytelnikDAO.save(czytelnik); 
	} 
	
	
	
	public String wyslij(String email){ 
		errorMessageEmail = "Wys³ano e-mail z nowym has³em";
		
		Czytelnik czytelnikDoEdycji = null;
		noweHaslo = generujHaslo();
		
		Collection<Czytelnik> czytelnicy = czytelnikDAO.findAll();
		for(Czytelnik czytelnik : czytelnicy)
		{
			if(czytelnik.getEmail().equals(email))
			{
				czytelnikDoEdycji = czytelnik;
			}
			
			if(!czytelnik.getEmail().equals(email))
			{
				errorMessageEmail = "Nie ma konta za³o¿onego na taki adres e-mail";
				return "";
			}
		}
		if(czytelnikDoEdycji != null) {
			Long id = czytelnikDoEdycji.getId();
			edytujHasloUzytkownika(id, noweHaslo);			
		}
			
		 
		try {
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		
		Session sesja = Session.getDefaultInstance(props, null);
    	sesja.setDebug(true);
    	
    	Message wiadomosc = new MimeMessage(sesja);
		
		wiadomosc.setFrom(new InternetAddress("paibiblioteka@gmail.com"));
		wiadomosc.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		wiadomosc.setContent(wiadomosc, "text/html");
		wiadomosc.setSubject("Nowe has³o do konta bibliotekaPAI");
		wiadomosc.setText("Witaj " + czytelnikDoEdycji.getImie() + " " + czytelnikDoEdycji.getNazwisko() + ",\n\n" +
						"Napisa³eœ do nas w celu uzyskania nowego has³a do konta." + "\n" +
						"Twoje nowe has³o zosta³o wygenerowane pomyœlnie!" + "\n" + 
						"Twój login: " + czytelnikDoEdycji.getUzytkownik().getLogin() + "\n" +
						"Twoje nowe has³o: " + noweHaslo + "\n\n" +
						"Pozdrawiamy, " + "\n" + "Biblioteka ProjektPAI");
	
		Transport transport = sesja.getTransport("smtp");
		transport.connect("smtp.gmail.com", "paibiblioteka", "paibiblioteka1");

		transport.sendMessage(wiadomosc, wiadomosc.getAllRecipients());
		
		
    }catch (Exception ex) {
			Logger.getLogger(OdzyskajHasloController.class.getName()).log(Level.SEVERE, null, ex);
		}
			
		return "";
	}
	
}