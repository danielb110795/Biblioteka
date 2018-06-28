package test.dao;

import org.junit.jupiter.api.Test;

import controller.UzytkownikController;
import entity.Uzytkownik;
import org.junit.Assert;

public class TestActiveUser {
	private UzytkownikController uzytkownikController = new UzytkownikController();
	private Uzytkownik uzytkownik = new Uzytkownik(10L, "Login", "Haslo", "CZYTELNIK", true, true, null);
	
	@Test
    public void testActive() { 
		Uzytkownik uzytkownik2 = uzytkownik;
		uzytkownikController.activeUser(uzytkownik2);
		Assert.assertEquals(uzytkownik, uzytkownik2);
    }
}
