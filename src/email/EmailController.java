package email;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class EmailController {
	
    public EmailController() {
    }
    
	String addressFrom = "paibiblioteka@gmail.com";

	public String wyslij(String imie, String nazwisko, String email, String temat, String tekst){

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
		wiadomosc.setRecipient(Message.RecipientType.TO, new InternetAddress("paibiblioteka@gmail.com"));
		wiadomosc.setContent(wiadomosc, "text/html");
		wiadomosc.setSubject(temat);
		wiadomosc.setText("Pan " + imie + " " + nazwisko + ",\n\n" +
						 "wys³a³ wiadomoœæ o treœci: " + tekst+ "\n\n" +
						 "Odpowiedzieæ na adres: " + email);
	
		Transport transport = sesja.getTransport("smtp");
		transport.connect("smtp.gmail.com", "paibiblioteka", "paibiblioteka1");

		transport.sendMessage(wiadomosc, wiadomosc.getAllRecipients());
		
		
    }catch (Exception ex) {
			Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
		}
			
		return "kontakt";
	}
	
}