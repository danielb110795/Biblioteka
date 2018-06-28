package test.dao;

import org.junit.jupiter.api.Test;

import controller.AutoryzacjaController;
import entity.Uzytkownik;
import org.junit.Assert;

public class TestKtoZalogowany {
	private AutoryzacjaController autoryzacjaController = new AutoryzacjaController();
	Uzytkownik czytelnik = new Uzytkownik(1L, "Login", "Haslo", "CZYTELNIK", true, true, null);
	Uzytkownik pracownik = new Uzytkownik(1L, "Login", "Haslo", "PRACOWNIK", true, true, null);
	Uzytkownik administrator = new Uzytkownik(1L, "Login", "Haslo", "ADMINISTRATOR", true, true, null);
	Uzytkownik niezalogowany = null;
	
	@Test
    public void testSprawdzCzyZalogowayCzytelnik() { 		
		String kto = autoryzacjaController.ktoZalogowany(czytelnik);
		Boolean tmp = false;
		if(kto.equals("CZYTELNIK"))
			tmp = true;
		Assert.assertTrue(tmp);
	}
	
	@Test
    public void testSprawdzCzyZalogowayPracownik() { 		
		String kto = autoryzacjaController.ktoZalogowany(pracownik);
		Boolean tmp = false;
		if(kto.equals("PRACOWNIK"))
			tmp = true;
		Assert.assertTrue(tmp);
	}
	
	@Test
    public void testSprawdzCzyZalogowayAdministrator() { 		
		String kto = autoryzacjaController.ktoZalogowany(administrator);
		Boolean tmp = false;
		if(kto.equals("ADMINISTRATOR"))
			tmp = true;
		Assert.assertTrue(tmp);
	}
	
	@Test
    public void testSprawdzCzyNiezalogowany() { 		
		String kto = autoryzacjaController.ktoZalogowany(niezalogowany);
		Boolean tmp = false;
		if(kto.equals("NIEZALOGOWANY"))
			tmp = true;
		Assert.assertTrue(tmp);
	}
}