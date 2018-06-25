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
import javax.servlet.http.HttpSession;

import dao.AutorDAO;
import dao.BibliotekaDAO;
import dao.EgzemplarzDAO;
import dao.KategoriaDAO;
import dao.KsiazkaDAO;
import dao.WydanieDAO;
import dao.WydawnictwoDAO;
import entity.Autor;
import entity.Biblioteka;
import entity.Egzemplarz;
import entity.Kategoria;
import entity.Ksiazka;
import entity.Wydanie;
import entity.Wydawnictwo;
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
	private int nrWydania;
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
	
	public String saveKategoria(int skad) {
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		errorMessageKsiazka = "";
		errorMessagePodsumowanie = "";
		List<Kategoria> kategorie = kategoriaDAO.findAll();
		for(Kategoria element : kategorie)
		{
			if(element.getNazwa().equals(nazwa))
			{
				errorMessageKategoria = "Ta kategoria ju¿ istnieje";
				return "kategoria";
			}
		}
		
		Kategoria kategoria = new Kategoria();
		kategoria.setNazwa(nazwa);
		kategoriaDAO.save(kategoria);
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
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		errorMessageKsiazka = "";
		errorMessagePodsumowanie = "";
		List<Autor> autorzy = autorDAO.findAll();
		for(Autor element : autorzy)
		{
			if(element.getImie().equals(imie) && element.getNazwisko().equals(nazwisko))
			{
				errorMessageAutor = "Taki autor ju¿ istnieje.";
				return "autor";
			}
		}
		Autor autor = new Autor();
		errorMessageAutor = "";
		autor.setImie(imie);
		autor.setNazwisko(nazwisko);
		autorDAO.save(autor);
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
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		errorMessageKsiazka = "";
		errorMessagePodsumowanie = "";
		List<Wydawnictwo> wydawnictwa = wydawnictwoDAO.findAll();
		for(Wydawnictwo element : wydawnictwa)
		{
			if(element.getNazwa().equals(nazwaWydawnictwa))
			{
				errorMessageWydawnictwo = "To wydawnictwo ju¿ istnieje";
				if(skad == 0)
					return "wydawnictwo";
				else
					return "dodaj_wydanie";
			}
		}
		Wydawnictwo wydawnictwo = new Wydawnictwo();
		wydawnictwo.setNazwa(nazwaWydawnictwa);
		wydawnictwoDAO.save(wydawnictwo);

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
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		errorMessageKsiazka = "";
		errorMessagePodsumowanie = "";
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Wydanie wydanie = new Wydanie();
		wydanie.setRokWydania(rokWydania);
		wydanie.setMiejsceWydania(miejsceWydania);
		wydanie.setNazwa("Wydanie: "+nrWydania);
		wydanie.setWydawnictwo(getWydanictwo(idWydawnictwa));
		session.setAttribute("wydanie",wydanie);
		
		session.setAttribute("podsumowanie", 1);
		return "dodaj_egzemplarz";
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
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		errorMessageKsiazka = "";
		errorMessageKsiazka = "";
		errorMessagePodsumowanie = "";
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
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		errorMessageKsiazka = "";
		errorMessagePodsumowanie = "";
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		List<Kategoria> kategorie = new LinkedList<Kategoria>();
		Kategoria podanaKategoria = getKategoria(idKategoria);
		if(podanaKategoria.getNazwa().equals(""))
		{
			errorMessageKategoria = "Nie poda³eœ kategori";
			if(skad == 0)
				return "dodaj_autora";
			if(skad == 2)
				return "podsumowanie_ksiazki";
			else
				return "dodaj_kategorie";
		}
		if(ksiazka.getKategoria() != null)
		{
			kategorie = ksiazka.getKategoria();
			for(Kategoria element : kategorie)
			{
				if(element.getNazwa().equals(podanaKategoria.getNazwa()))
				{
					errorMessageKategoria = "Podana kategoria zosta³a ju¿ dodana do tej ksi¹¿ki";
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
		else
			return "dodaj_kategorie";
	}
	
	public String dodajAutoraDoKsiazki(int skad)
	{
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		errorMessageKsiazka = "";
		errorMessagePodsumowanie = "";
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		List<Autor> autorzy = new LinkedList<Autor>();
		Autor podanyAutor = getAutor(idAutora);
		if(podanyAutor.getImie().equals("") || podanyAutor.getNazwisko().equals(""))
		{
			errorMessageAutor = "Nie poda³eœ autora";
			if(skad == 0)
				return "dodaj_wydanie";
			if(skad == 2)
				return "podsumowanie_ksiazki";
			else
				return "dodaj_autora";
		}
		if(ksiazka.getAutor() != null)
		{
			autorzy = ksiazka.getAutor();
			for(Autor element : autorzy)
			{
				if(element.getImie().equals(podanyAutor.getImie()) && element.getNazwisko().equals(podanyAutor.getNazwisko()))
				{
					errorMessageAutor = "Podany autor zosta³ ju¿ dodany do tej ksi¹¿ki";
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
		else
			return "dodaj_autora";
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
		List<Ksiazka> ksiazki = new LinkedList<>();
		ksiazki = ksiazkaDAO.findAll();	
		return ksiazki;
	}
	
	public String saveEgzemplarz(int skad) {
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		errorMessageKsiazka = "";
		errorMessagePodsumowanie = "";
		Egzemplarz egzemplarz = new Egzemplarz();
		if(ISBN.equals(""))
		{
			errorMessageEgzemplarz = "Poda³eœ puste pole!";
			if(skad  == 0)
			{
				return "ksiazki";
			}
			else
			{
				return "dodaj_egzemplarz";
			}
		}
		egzemplarz.setStatus("Dostêpna");
		egzemplarz.setISBN(ISBN);
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Wydanie wydanie = (Wydanie) session.getAttribute("wydanie");
		
		egzemplarz.setWydanie(wydanie);
		
		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		List<Egzemplarz> egzemplarze = new LinkedList<>();
		if(ksiazka.getEgzemplarz() != null)
		{
			egzemplarze = ksiazka.getEgzemplarz();
			for(Egzemplarz element : egzemplarze)
			{
				if(element.getISBN().equals(egzemplarz.getISBN()))
				{
					errorMessageEgzemplarz = "Podany egzemplarz zosta³ ju¿ dodany do ksi¹¿ki";
					return "dodaj_egzemplarz";
				}
			}
		}
		List<Egzemplarz> dostepneEgzemplarze = egzemplarzDAO.findAll();
		for(Egzemplarz element : dostepneEgzemplarze)
		{
			if(element.getISBN().equals(ISBN))
			{
				errorMessageEgzemplarz = "Podany egzemplarz istnieje ju¿ w bazie";
				return "dodaj_egzemplarz";
			}
		}
		egzemplarze.add(egzemplarz);
		ksiazka.setEgzemplarz(egzemplarze);
		

		session.setAttribute("ksiazka", ksiazka);
		
		if(skad  == 0)
		{
			return "podsumowanie_ksiazki";
		}
		else
		{
			return "dodaj_egzemplarz";
		}
	}
	public List<Ksiazka> pokazPodsumowanie()
	{
		List<Ksiazka> ksiazki = new LinkedList<>();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		ksiazki.add(ksiazka);
		return ksiazki;
	}
	public String dodajKsiazke()
	{
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		errorMessageKsiazka = "";
		errorMessagePodsumowanie = "";
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
			errorMessagePodsumowanie = "Ksi¹¿ka musi posiadaæ autora !";
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
		ksiazkaDAO.save(ksiazka);
		return "ksiazki";
	}
	
	public String zapiszIDodajWydanie()
	{
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		errorMessageKsiazka = "";
		errorMessagePodsumowanie = "";
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
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		errorMessageKsiazka = "";
		errorMessagePodsumowanie = "";
		kategoriaDAO.remove((long)idKategoria);
		return "kategoria";
	}
	public String usunAutora()
	{
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		errorMessageKsiazka = "";
		errorMessagePodsumowanie = "";
		autorDAO.remove((long)idAutora);
		return "autor";
	}
	public String usunWydawnictwo() throws IOException
	{
		errorMessageKategoria = "";
		errorMessageAutor = "";
		errorMessageWydawnictwo = "";
		errorMessageEgzemplarz = "";
		errorMessageKsiazka = "";
		errorMessagePodsumowanie = "";
		try {
		wydawnictwoDAO.remove((long)idWydawnictwa);
		}catch(Throwable e)
		{
			errorMessageWydawnictwo = "Wydawnictwo zawiera przypisane egzemplarze !";
			return "wydawnictwo";
		}
		
		return "wydawnictwo";
	}
}
