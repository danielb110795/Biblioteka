package test.dao;

import org.junit.jupiter.api.Test;

import controller.UzytkownikController;
import entity.Uzytkownik;
import org.junit.Assert;

public class TestActiveUser {
	private UzytkownikController uzytkownikController = new UzytkownikController();
	Uzytkownik uzytkownik = new Uzytkownik(10L, "Login", "Haslo", "CZYTELNIK", false, false, null);
	Uzytkownik uzytkownik2 = new Uzytkownik(10L, "Login", "Haslo", "CZYTELNIK", true, true, null);
	
	@Test
    public void testActive() { 		
		uzytkownikController.activeUser(uzytkownik);
		boolean tmp = uzytkownik.isAktywowane();
		Assert.assertTrue(tmp);
	}
	
	@Test
    public void testDeactive() { 		
		uzytkownikController.deactiveUser(uzytkownik2);
		boolean tmp = uzytkownik.isAktywowane();
		Assert.assertFalse(tmp);
	}
}