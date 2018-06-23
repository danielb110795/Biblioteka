package controller;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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

@Stateless
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
		
		List<Kategoria> kategorie = kategoriaDAO.findAll();
		for(Kategoria element : kategorie)
		{
			if(element.getNazwa().equals(nazwa))
			{
				errorMessageKategoria = "Ju¿ istnieje!!!";
				return "ksiazki";
			}
		}
		
		Kategoria kategoria = new Kategoria();
		kategoria.setNazwa(nazwa);
		kategoriaDAO.save(kategoria);
		if(skad == 0)
			return "ksiazki";
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
				errorMessageAutor = "Ju¿ istnieje";
				return "ksiazki";
			}
		}
		Autor autor = new Autor();
		errorMessageAutor = "";
		autor.setImie(imie);
		autor.setNazwisko(nazwisko);
		autorDAO.save(autor);
		if(skad == 0)
			return "ksiazki";
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
	
	public String saveWydawnictwo() {
		List<Wydawnictwo> wydawnictwa = wydawnictwoDAO.findAll();
		for(Wydawnictwo element : wydawnictwa)
		{
			if(element.getNazwa().equals(nazwaWydawnictwa))
			{
				errorMessageWydawnictwo = "To wydawnictwo ju¿ istnieje";
				return "ksiazki";
			}
		}
		Wydawnictwo wydawnictwo = new Wydawnictwo();
		wydawnictwo.setNazwa(nazwaWydawnictwa);
		wydawnictwoDAO.save(wydawnictwo);

		return "ksiazki";
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
		Wydanie wydanie = new Wydanie();
		wydanie.setRokWydania(rokWydania);
		wydanie.setMiejsceWydania(miejsceWydania);
		wydanie.setNazwa("Wydanie: "+nrWydania);
		wydanie.setWydawnictwo(getWydanictwo(idWydawnictwa));
		session.setAttribute("wydanie",wydanie);
		//wydanieDAO.save(wydanie);

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
		Ksiazka ksiazka = new Ksiazka();
		ksiazka.setTytul(tytul);
		ksiazka.setOpis(opis);
		ksiazka.setStan("Niezniszczona"); //raczej niepotrzebne
		ksiazka.setZdjecie("zdjecie.jpg");
		Biblioteka biblioteka = bibliotekaDAO.findOne((long)idPlacowki);
		ksiazka.setBiblioteka(biblioteka);
		
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		session.setAttribute("ksiazka",ksiazka);

		return "dodaj_kategorie";
	}
	
	public String dodajKategorieDoKsiazki(int skad)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		List<Kategoria> kategorie = new LinkedList<Kategoria>();
		if(ksiazka.getKategoria() != null)
			kategorie = ksiazka.getKategoria();
		kategorie.add(getKategoria(idKategoria));
		ksiazka.setKategoria(kategorie);
		session.setAttribute("ksiazka",ksiazka);
		if(skad == 0)
			return "dodaj_autora";
		else
			return "dodaj_kategorie";
	}
	
	public String dodajAutoraDoKsiazki(int skad)
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Ksiazka ksiazka = (Ksiazka) session.getAttribute("ksiazka");
		List<Autor> autorzy = new LinkedList<Autor>();
		if(ksiazka.getAutor() != null)
			autorzy = ksiazka.getAutor();
		autorzy.add(getAutor(idAutora));
		ksiazka.setAutor(autorzy);
		session.setAttribute("ksiazka",ksiazka);
		if(skad == 0)
			return "dodaj_wydanie";
		else
			return "dodaj_autora";
	}
	
	public String saveKsiazka() {
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
	}
	
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
		Egzemplarz egzemplarz = new Egzemplarz();
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
		}
		egzemplarze.add(egzemplarz);
		ksiazka.setEgzemplarz(egzemplarze);
		

		//session.setAttribute("ksiazka", ksiazka);
		
		if(skad  == 0)
		{
			ksiazkaDAO.save(ksiazka);
			return "ksiazki";
		}
		else
		{
			session.setAttribute("ksiazka", ksiazka);
			return "dodaj_egzemplarz";
		}
	}
}
