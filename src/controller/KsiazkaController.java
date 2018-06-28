package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpSession;

import dao.AutorDAO;
import dao.BibliotekaDAO;
import dao.EgzemplarzDAO;
import dao.KategoriaDAO;
import dao.KsiazkaDAO;
import dao.WydanieDAO;
import dao.WydawnictwoDAO;
import dao.ZamowienieDAO;
import entity.Autor;
import entity.Biblioteka;
import entity.Egzemplarz;
import entity.Kategoria;
import entity.Ksiazka;
import entity.Uzytkownik;
import entity.Wydanie;
import entity.Wydawnictwo;
import entity.Zamowienie;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
@Data
@LocalBean
public class KsiazkaController {
	@EJB
	private KategoriaDAO kategoriaDAO;
	
	@EJB
	private AutorDAO autorDAO;
	
	@EJB
	private WydanieDAO wydanieDAO;
	
	@EJB
	private KsiazkaDAO ksiazkaDAO;
	
	@EJB
	private WydawnictwoDAO wydawnictwoDAO;
	
	@EJB
	private EgzemplarzDAO egzemplarzDAO;
	
	@EJB
	private BibliotekaDAO bibliotekaDAO;
	
	@EJB
	private ZamowienieDAO zamowienieDAO;
	
	@Getter
	@Setter
	private String errorMessageKategoria = "";
	
	@Getter
	@Setter
	private String errorMessageAutor = "";
	
	@Getter
	@Setter
	private String errorMessageWydawnictwo= "";
	
	@Getter
	@Setter
	private String errorMessageEgzemplarz = "";
	
	@Getter
	@Setter
	private String errorMessageKsiazka = "";
	
	@Getter
	@Setter
	private String errorMessagePodsumowanie = "";
	
	@Getter
	@Setter
	private String dodanieWydawnictwaMessage = "";
	
	@Getter
	@Setter
	private String usunWydawnictwaMessage = "";
	
	@Getter
	@Setter
	private String usunAutoraMessage = "";
	
	@Getter
	@Setter
	private String dodajAutoraMessage = "";
	
	@Getter
	@Setter
	private String usunKategoriaMessage = "";
	
	@Getter
	@Setter
	private String dodajKategorieMessage = "";
	
	@Getter
	@Setter
	private String dodajKolejnaKategoriaMessage = "";
	
	@Getter
	@Setter
	private String dodajKolejnegoAutoraMessage = "";
	
	@Getter
	@Setter
	private String dodajKolejnyEgzemplarzMessage = "";
	
	@Getter
	@Setter
	private String usunKategorieZDodawanejKsiazkiMessage = "";
	
	@Getter
	@Setter
	private String usunAutoraZDodawanejKsiazkiMessage = "";
	
	//kategoria
	private String nazwa;
	
	//autor
	private String imie;
	private String nazwisko;
	
	//wydawnictwo
	private String nazwaWydawnictwa;
	
	//wydanie
	private String rokWydania;
	private int idWydawnictwa;
	private String nrWydania;
	private String miejsceWydania;
	
	//ksiazka
	private String tytul;
	private String opis;
	private String stan;
	private String zdjecie;
	private int idAutora;
	private int idKategoria;
	//private List <Autor> autors; 
	//private List <Kategoria> kategorias;
	
	//egzemplarz
	private String ISBN;
	private int idKsiazki;
	private int idWydania;
	//private int liczbaEgzemplarzy;
	private int idPlacowki;
	private Long iloscEgzemplarzy;
	
	private String szukanie;
	
    public void setKategoriaDAO(KategoriaDAO kategoriaDAO) {
    	this.kategoriaDAO = kategoriaDAO;
    }
    
    public void setWydawnictwoDAO(WydawnictwoDAO wydawnictwoDAO) {
    	this.wydawnictwoDAO = wydawnictwoDAO;
    }
    
    public void setAutorDAO(AutorDAO autorDAO) {
    	this.autorDAO = autorDAO;
    }
    
	public String ustawSzukanie()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("szukanie", szukanie);
		return "spis_ksiazek";
	}
	public String ustawKategorie()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Kategoria kategoria = getKategoria(idKategoria);
		session.setAttribute("szukanieKat", kategoria);
		return "spis_ksiazek";
	}
	public String ustawPlacowke()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Biblioteka placowka = bibliotekaDAO.findOne((long)idPlacowki);
		session.setAttribute("szukaniePlac", placowka);
		return "spis_ksiazek";
	}
	public String saveKategoria(int skad) {
		List<Kategoria> kategorie = kategoriaDAO.findAll();
		for(Kategoria element : kategorie)
		{
			if(element.getNazwa().equals(nazwa))
			{
				errorMessageKategoria = "Ta kategoria ju¿ istnieje";
				if(skad == 0)
					return "kategoria";
				else
					return "dodaj_kategorie";
			}
		}
		
		Kategoria kategoria = new Kategoria();
		kategoria.setNazwa(nazwa);
		kategoriaDAO.save(kategoria);
		dodajKategorieMessage = "Dodano kategorie.";
		if(skad == 0)
			return "kategoria";
		else
			return "dodaj_kategorie";
	}
	
	public List<Kategoria> pokazKategorie()
	{
		List<Kategoria> kategorie = new LinkedList<>();
		//kategorie = kategoriaDAO.findAll();//.stream().sorted().map(Kategoria::getNazwa).collect(Collectors.toList());
		kategorie = kategoriaDAO.findAll().stream().sorted((k1, k2)->{
			  return k1.getNazwa().toLowerCase().compareTo(k2.getNazwa().toLowerCase());
			}).collect(Collectors.toList());
		
		return kategorie;
	}
	
	public Kategoria getKategoria(int idKategoria)
	{
		Kategoria kategoria = kategoriaDAO.findOne((long)idKategoria);	
		return kategoria;                
	}
	
	public String saveAutor(int skad) {
		List<Autor> autorzy = autorDAO.findAll();
		for(Autor element : autorzy)
		{
			if(element.getImie().equals(imie) && element.getNazwisko().equals(nazwisko))
			{
				errorMessageAutor = "Taki autor ju¿ istnieje.";
				if(skad == 0)
					return "autor";
				else
					return "dodaj_autora";
			}
		}
		Autor autor = new Autor();
		autor.setImie(imie);
		autor.setNazwisko(nazwisko);
		autorDAO.save(autor);
		dodajAutoraMessage = "Dodano autora.";
		if(skad == 0)
			return "autor";
		else
			return "dodaj_autora";
	}
	
	public List<Autor> pokazAutorow()
	{
		List<Autor> autors = new LinkedList<>();
		autors = autorDAO.findAll().stream().sorted((a1, a2)->{
				int nazwisko;
				nazwisko = a1.getNazwisko().toLowerCase().compareTo(a2.getNazwisko().toLowerCase());
			  if(nazwisko != 0)
				  {
				  	return nazwisko;
				  }
			  else return a1.getImie().toLowerCase().compareTo(a2.getImie().toLowerCase());
			}).collect(Collectors.toList());
		return autors;
	}
	
	public Autor getAutor(int idAutora)
	{
		Autor autor = autorDAO.findOne((long)idAutora);	
		return autor;                
	}
	
	public String saveWydawnictwo(int skad) {
		List<Wydawnictwo> wydawnictwa = wydawnictwoDAO.findAll();
		for(Wydawnictwo element : wydawnictwa)
		{
			if(element.getNazwa().equals(nazwaWydawnictwa))
			{
				errorMessageWydawnictwo = "To wydawnictwo ju¿ istnieje.";
				if(skad == 0)
					return "wydawnictwo";
				else
					return "dodaj_wydanie";
			}
		}
		Wydawnictwo wydawnictwo = new Wydawnictwo();
		wydawnictwo.setNazwa(nazwaWydawnictwa);
		wydawnictwoDAO.save(wydawnictwo);
		
		dodanieWydawnictwaMessage = "Wydawnictwo pomyœlnie dodane.";

		if(skad == 0)
			return "wydawnictwo";
		else
			return "dodaj_wydanie";
	}
	
	
	public Wydawnictwo getWydanictwo(int idWydawnictwa)
	{
		Wydawnictwo wydawnictwo = wydawnictwoDAO.findOne((long)idWydawnictwa);	
		return wydawnictwo;                
	}
	
	public List<Wydawnictwo> pokazWydawnictwa()
	{
		List<Wydawnictwo> wydawnictwa = new LinkedList<>();
		wydawnictwa = wydawnictwoDAO.findAll().stream().sorted((k1, k2)->{
			  return k1.getNazwa().toLowerCase().compareTo(k2.getNazwa().toLowerCase());
			}).collect(Collectors.toList());;	
		return wydawnictwa;
	}
	
	public String saveWydanie() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		Wydanie wydanie = new Wydanie();
		wydanie.setRokWydania(rokWydania);
		wydanie.setMiejsceWydania(miejsceWydania);
		wydanie.setNazwa("Wydanie: "+ nrWydania);
		wydanie.setWydawnictwo(getWydanictwo(idWydawnictwa));
		wydanie.setISBN(ISBN);
		List<Egzemplarz> egzemplarze = new LinkedList<>();
		if(ksiazka.getEgzemplarz() != null && (ksiazka.getEgzemplarz().isEmpty() == false))
				egzemplarze = ksiazka.getEgzemplarz();
		
		Long ilosc = iloscEgzemplarzy;
		for(long i = 0;i < ilosc;i++)
		{
			Egzemplarz egzemplarz = new Egzemplarz();
			egzemplarz.setWydanie(wydanie);
			egzemplarz.setStatus("DOSTEPNY");
			egzemplarz.setNumerEgz(i);
			egzemplarze.add(egzemplarz);
		}
		ksiazka.setEgzemplarz(egzemplarze);
		session.setAttribute("wydanie",wydanie);
		session.setAttribute("ksiazka", ksiazka);
		session.setAttribute("podsumowanie", 1);
		return "podsumowanie_ksiazki";
	}
	public String dodajEgzemplarzDoIstniejacej(String id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Long idK = Long.parseLong(id);
		Ksiazka ksiazka = ksiazkaDAO.findOne(idK);
		session.setAttribute("ksiazka", ksiazka);
		return "dodaj_wydanie";
	}
	public String dodajEgzemplarzDoIstniejacejZam(String id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Long idK = Long.parseLong(id);
		Ksiazka ksiazka = ksiazkaDAO.findOne(idK);
		List<Egzemplarz> egzemplarze = new LinkedList<>();
		if(ksiazka.getEgzemplarz() != null && (ksiazka.getEgzemplarz().isEmpty() == false))
			egzemplarze = ksiazka.getEgzemplarz();
		Long ilosc = (Long) session.getAttribute("iloscEgz");
		Wydanie wydanie = (Wydanie) session.getAttribute("wydanie");
		for(long i = 0;i < ilosc;i++) {
			Egzemplarz egzemplarz = new Egzemplarz();
			egzemplarz.setStatus("DOSTEPNY");
			egzemplarz.setWydanie(wydanie);
			egzemplarz.setNumerEgz(i);
			egzemplarze.add(egzemplarz);
		}
		
		ksiazka.setEgzemplarz(egzemplarze);
		session.setAttribute("ksiazka", ksiazka);
		session.setAttribute("podsumowanie", 1);
		return "podsumowanie_ksiazki";
	}
	public String zakonczZamowienie(String id)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		
		Long idZam = Long.parseLong(id);
		Zamowienie zamowienie = zamowienieDAO.findOne(idZam);
		
		List<Ksiazka> ksiazki = ksiazkaDAO.findAll();
		for(Ksiazka ksiazka : ksiazki)
		{
			if(ksiazka.getTytul().equals(zamowienie.getTytul()) && ksiazka.getBiblioteka().getId() == zamowienie.getBiblioteka().getId())
			{
				Wydanie wydanie = new Wydanie();
				wydanie.setISBN(zamowienie.getISBN());
				wydanie.setMiejsceWydania(zamowienie.getMiejsceWydania());
				wydanie.setRokWydania(zamowienie.getRokWydania());
				wydanie.setWydawnictwo(zamowienie.getWydawnictwo());
				wydanie.setNazwa("Wydanie: "+zamowienie.getNrWydania());

				Long iloscEgz = zamowienie.getIlosc();
				
				session.setAttribute("iloscEgz", iloscEgz);
				session.setAttribute("wydanie", wydanie);
				zamowienie.setStatus("ZAKOÑCZONE");
				zamowienieDAO.save(zamowienie);
				return dodajEgzemplarzDoIstniejacejZam(""+ksiazka.getId()+"");
			}
		}
		Ksiazka ksiazka2 = new Ksiazka();
		ksiazka2.setTytul(zamowienie.getTytul());
		ksiazka2.setBiblioteka(zamowienie.getBiblioteka());
		ksiazka2.setOpis(zamowienie.getOpis());
		ksiazka2.setZdjecie("zdjecie.jpg");
		Wydanie wydanie = new Wydanie();
		wydanie.setISBN(zamowienie.getISBN());
		wydanie.setMiejsceWydania(zamowienie.getMiejsceWydania());
		wydanie.setRokWydania(zamowienie.getRokWydania());
		wydanie.setWydawnictwo(zamowienie.getWydawnictwo());
		wydanie.setNazwa("Wydanie: "+zamowienie.getNrWydania());
		
		List<Egzemplarz> egzemplarze = new LinkedList<>();
		for(long i = 0;i < zamowienie.getIlosc();i++)
		{
			Egzemplarz egzemplarz = new Egzemplarz();
			egzemplarz.setStatus("DOSTEPNY");
			egzemplarz.setWydanie(wydanie);
			egzemplarz.setNumerEgz(i);
			egzemplarze.add(egzemplarz);
		}
		ksiazka2.setEgzemplarz(egzemplarze);
		session.setAttribute("ksiazka", ksiazka2);
		zamowienie.setStatus("ZAKOÑCZONE");
		zamowienieDAO.save(zamowienie);
		session.setAttribute("podsumowanie", 1);
		return "podsumowanie_ksiazki";
	}
	public Wydanie getWydanie(int idWydania)
	{
		Wydanie wydanie = wydanieDAO.findOne((long)idWydania);	
		return wydanie;                
	}
	
	public List<Wydanie> pokazWydania()
	{
		List<Wydanie> wydania = new LinkedList<>();
		wydania = wydanieDAO.findAll();	
		return wydania;
	}
	
	public String dodawanieKsiazki() {
		Ksiazka ksiazka = new Ksiazka();
		List<Ksiazka> ksiazki = ksiazkaDAO.findAll();
		Biblioteka biblioteka = bibliotekaDAO.findOne((long)idPlacowki);
		for(Ksiazka element : ksiazki)
		{
			if(element.getTytul().equals(tytul) && element.getBiblioteka().equals(biblioteka))
			{
				errorMessageKsiazka = "Ksiazka o podanym tytule juz istnieje w tej bibliotece";
				return "ksiazki";
			}
		}
		ksiazka.setTytul(tytul); 
		ksiazka.setOpis(opis);
		ksiazka.setStan("Niezniszczona"); //raczej niepotrzebne
		ksiazka.setZdjecie("zdjecie.jpg");
		
		ksiazka.setBiblioteka(biblioteka);
		
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		session.setAttribute("ksiazka",ksiazka);
		session.setAttribute("podsumowanie", 0);

		return "dodaj_kategorie";
	}
	
	public String dodajKategorieDoKsiazki(int skad)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		List<Kategoria> kategorie = new LinkedList<Kategoria>();
		Kategoria podanaKategoria = getKategoria(idKategoria);
		if(podanaKategoria.getNazwa().equals(""))
		{
			errorMessageKategoria = "Nie poda³eœ kategorii.";
			if(skad == 0)
				return "dodaj_autora";
			if(skad == 2)
				return "podsumowanie_ksiazki";
			else {
				dodajKolejnaKategoriaMessage = "Doda³eœ kategoriê, dodaj kolejn¹ lub przejdz do nastêpnego etapu.";
				return "dodaj_kategorie";
			}
		}
		if(ksiazka.getKategoria() != null)
		{
			kategorie = ksiazka.getKategoria();
			for(Kategoria element : kategorie)
			{
				if(element.getNazwa().equals(podanaKategoria.getNazwa()))
				{
					errorMessageKategoria = "Podana kategoria zosta³a ju¿ dodana do tej ksi¹¿ki. Dodaj inn¹ lub przejdz dalej.";
					return "dodaj_kategorie";
				}
			}
		}
		kategorie.add(podanaKategoria);
		ksiazka.setKategoria(kategorie);
		session.setAttribute("ksiazka",ksiazka);
		if(skad == 0)
			return "dodaj_autora";
		if(skad == 2)
			return "podsumowanie_ksiazki";
		else {
			dodajKolejnaKategoriaMessage = "Doda³eœ kategoriê, dodaj kolejn¹ lub przejdz do nastêpnego etapu.";
			return "dodaj_kategorie";
		}			
	}
	
	public String usunKategorieZKsiazki()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		List<Kategoria> kategorie = new LinkedList<Kategoria>();
		Kategoria podanaKategoria = getKategoria(idKategoria);
		
		if(ksiazka.getKategoria() != null)
		{
			kategorie = ksiazka.getKategoria();		
		}
		
		//int id;
		//id = podanaKategoria.getId();
		usunKategorieZDodawanejKsiazkiMessage = "Usuniêto kategorie z ksia¿ki.";
		kategorie.remove(podanaKategoria);
		ksiazka.setKategoria(kategorie);
		session.setAttribute("ksiazka",ksiazka);
		
		return "dodaj_kategorie";
	}
	
	public String usunAutoraZKsiazki()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		List<Autor> autorzy = new LinkedList<Autor>();
		Autor podanyAutor = getAutor(idAutora);
		
		if(ksiazka.getAutor() != null || ksiazka.getAutor().isEmpty())
		{
			autorzy = ksiazka.getAutor();
		}
		//int id;
		//id = podanaKategoria.getId();
		usunAutoraZDodawanejKsiazkiMessage = "Usuniêto autora z ksi¹¿ki.";
		autorzy.remove(podanyAutor);
		ksiazka.setAutor(autorzy);
		session.setAttribute("ksiazka",ksiazka);
		
		return "dodaj_autora";
	}
	
	public String dodajAutoraDoKsiazki(int skad)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		List<Autor> autorzy = new LinkedList<Autor>();
		Autor podanyAutor = getAutor(idAutora);
		if(podanyAutor.getImie().equals("") || podanyAutor.getNazwisko().equals(""))
		{
			errorMessageAutor = "Nie poda³eœ autora.";
			if(skad == 0)
				return "dodaj_wydanie";
			if(skad == 2)
				return "podsumowanie_ksiazki";
			else {
				dodajKolejnegoAutoraMessage = "Doda³eœ autora, dodaj kolejnego lub przejdz do nastêpnego etapu.";
				return "dodaj_autora";
			}
		}
		if(ksiazka.getAutor() != null)
		{
			autorzy = ksiazka.getAutor();
			for(Autor element : autorzy)
			{
				if(element.getImie().equals(podanyAutor.getImie()) && element.getNazwisko().equals(podanyAutor.getNazwisko()))
				{
					errorMessageAutor = "Podany autor zosta³ ju¿ dodany do tej ksi¹¿ki. Wybierz innego lub przejdz dalej.";
					return "dodaj_autora";
				}
			}
		}
		autorzy.add(podanyAutor);
		ksiazka.setAutor(autorzy);
		session.setAttribute("ksiazka",ksiazka);
		if(skad == 0)
			return "dodaj_wydanie";
		if(skad == 2)
			return "podsumowanie_ksiazki";
		else {
			dodajKolejnegoAutoraMessage = "Doda³eœ autora, dodaj kolejnego lub przejdz do nastêpnego etapu.";
			return "dodaj_autora";
		}
	}
	
	/*public String saveKsiazka() {
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		Ksiazka ksiazka = new Ksiazka();
		ksiazka.setTytul(tytul);
		ksiazka.setOpis(opis);
		ksiazka.setStan("Niezniszczona"); //raczej niepotrzebne
		ksiazka.setZdjecie("zdjecie.jpg");
		
		//zapis autorow
		List<Autor> autorzy = new LinkedList<>();
		autorzy.add(getAutor(idAutora));
		ksiazka.setAutor(autorzy);
		
		//zapis kategorii
		List<Kategoria> kategorie = new LinkedList<>();
		kategorie.add(getKategoria(idKategoria));
		ksiazka.setKategoria(kategorie);
		
		ksiazkaDAO.save(ksiazka);

		return "ksiazki";
	}*/
	
	public Ksiazka getKsiazka(int idKsiazki)
	{
		Ksiazka ksiazka = ksiazkaDAO.findOne((long)idKsiazki);	
		return ksiazka;                
	}
	
	public List<Ksiazka> pokazKsiazki()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		String zapytanie = (String) session.getAttribute("szukanie");
		Kategoria kategoria = (Kategoria) session.getAttribute("szukanieKat");
		Biblioteka placowka = (Biblioteka) session.getAttribute("szukaniePlac");
		session.removeAttribute("szukanie");
		session.removeAttribute("szukanieKat");
		session.removeAttribute("szukaniePlac");
		List<Ksiazka> ksiazki = new LinkedList<>();
		ksiazki = ksiazkaDAO.findAll();	
		if(placowka != null)
		{
			List<Ksiazka> ksiazkiPlac = new LinkedList<>();
			for(Ksiazka element : ksiazki)
			{
				if(element.getBiblioteka().getId() == placowka.getId())
				{
					ksiazkiPlac.add(element);
				}	
			}
			return ksiazkiPlac;
		}
		if(kategoria != null)
		{
			List<Ksiazka> ksiazkiKat = new LinkedList<>();
			for(Ksiazka element : ksiazki)
			{
				List<Kategoria> kategorie = element.getKategoria();
				for(Kategoria elementKat : kategorie)
				{
					if(elementKat.getNazwa() == kategoria.getNazwa())
					{
						ksiazkiKat.add(element);
					}
				}
			}
			return ksiazkiKat;
		}
		if(zapytanie != null)
		{
			List<Ksiazka> ksiazkiFiltr = new LinkedList<>();
			for(Ksiazka element : ksiazki)
			{
				if(element.getTytul().toUpperCase().contains(zapytanie.toUpperCase()))
				{
					ksiazkiFiltr.add(element);
				}
			}
			return ksiazkiFiltr;
		}
		return ksiazki;
	}
	
	
	
	public List<Ksiazka> pokazPodsumowanie()
	{
		List<Ksiazka> ksiazki = new LinkedList<>();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		ksiazki.add(ksiazka);
		return ksiazki;
	}
	
	public List<Kategoria> pokazKategorieKsiazki()
	{
		List<Kategoria> kategorie = pokazPodsumowanie().get(0).getKategoria();
		return kategorie;
	}
	
	public List<Autor> pokazAutoraKsiazki()
	{
		List<Autor> autorzy = pokazPodsumowanie().get(0).getAutor();
		return autorzy;
	}
	
	public String dodajKsiazke()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		Wydanie wydanie = (Wydanie) session.getAttribute("wydanie");
		if(ksiazka.getKategoria() == null || ksiazka.getKategoria().isEmpty())
		{
			errorMessagePodsumowanie = "Ksi¹zka musi posiadaæ kategoriê !";
			return "podsumowanie_ksiazki";
		}
		if(ksiazka.getAutor() == null || ksiazka.getAutor().isEmpty())
		{
			errorMessagePodsumowanie = "Ksi¹¿ka musi posiadaæ autora !";
			return "podsumowanie_ksiazki";
		}
		if(ksiazka.getEgzemplarz() == null || ksiazka.getEgzemplarz().isEmpty())
		{
			errorMessagePodsumowanie = "Ksi¹¿ka musi posiadaæ autora !";
			return "podsumowanie_ksiazki";
		}
		
		ksiazkaDAO.save(ksiazka);
		return "ksiazki";
	}
	
	public String zapiszIDodajWydanie()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		Wydanie wydanie = (Wydanie) session.getAttribute("wydanie");
		if(ksiazka.getKategoria() == null)
		{
			errorMessagePodsumowanie = "Ksi¹zka musi posiadaæ kategoriê !";
			return "podsumowanie_ksiazki";
		}
		if(ksiazka.getAutor() == null)
		{
			errorMessagePodsumowanie = "Ksi¹¿ka musi posiadaæ autora !";
			return "podsumowanie_ksiazki";
		}
		if(ksiazka.getEgzemplarz() == null)
		{
			errorMessagePodsumowanie = "Ksi¹¿ka musi posiadaæ egzemplarz !";
			return "podsumowanie_ksiazki";
		}
		if(ksiazka.getEgzemplarz() != null)
		{
			List<Egzemplarz> egzemplarze = ksiazka.getEgzemplarz();
			int licznik = 0;
			for(Egzemplarz element : egzemplarze)
			{
				if(element.getWydanie() == null)
				{
					errorMessagePodsumowanie = "Pominiêto dodanie wydania Anuluj i zacznij jeszcze raz !";
					return "podsumowanie_ksiazki";
				}
				if(element.getWydanie().getId() == wydanie.getId())
				{
					licznik = 1;
					break;
				}
			}
			if(licznik == 0)
			{
				errorMessagePodsumowanie = "Ksi¹¿ka musi posiadaæ egzemplarz aktualnie dodawanego wydania !";
				return "podsumowanie_ksiazki";
			}
		}
		Long id = (long) -1;
		ksiazkaDAO.save(ksiazka);
		List<Ksiazka> ksiazki = ksiazkaDAO.findAll();//.stream().filter(k -> k.getTytul().equals(ksiazka.getTytul()))
		for(Ksiazka element : ksiazki)
		{
			if(element.getTytul().equals(ksiazka.getTytul()))
			{
				id = element.getId();
			}
		}
		if(id == -1)
			return "ksiazki";
		Ksiazka ksiazkaEdit = ksiazkaDAO.findOne(id);
		session.setAttribute("ksiazka",ksiazkaEdit);
		return "dodaj_wydanie";
	}
	
	public int czyZPodsumowania()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		int skad = (int) session.getAttribute("podsumowanie");
		return skad;
	}
	
	public String pokazSzczegoly(String id)
	{
		Long idK = Long.parseLong(id);
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("idKsiazki", idK);
		return "ksiazka_szczegoly";
	}
	
	public Ksiazka znajdzKsiazke()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Long idK = (Long) session.getAttribute("idKsiazki");
		
		Ksiazka ksiazka = ksiazkaDAO.findOne(idK);
		return ksiazka;
	}

	public String usunKategorie()
	{
		try {
			kategoriaDAO.remove((long)idKategoria);
		}catch(Throwable e)
		{
			errorMessageKategoria = "Kategoria jest przypisana do ksi¹¿ki!";
			return "kategoria";
		}
		usunKategoriaMessage = "Usuniêto kategoriê.";

		return "kategoria";
	}
	
	public String usunAutora()
	{	
		try {
			autorDAO.remove((long)idAutora);
		}catch(Throwable e)
		{
			errorMessageAutor = "Autor jest przypisany do ksi¹¿ki!";
			return "autor";
		}
		usunAutoraMessage = "Usuniêto autora.";
		return "autor";
	}
	
	public String usunWydawnictwo() throws IOException
	{
		try {
			
			wydawnictwoDAO.remove((long)idWydawnictwa);
		}catch(Throwable e)
		{
			errorMessageWydawnictwo = "Wydawnictwo zawiera przypisane egzemplarze!";
			return "wydawnictwo";
		}
		usunWydawnictwaMessage = "Usuniêto wydawnictwo.";
		return "wydawnictwo";
	}
}
