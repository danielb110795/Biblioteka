package controller;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import dao.AutorDAO;
import dao.EgzemplarzDAO;
import dao.KategoriaDAO;
import dao.KsiazkaDAO;
import dao.WydanieDAO;
import dao.WydawnictwoDAO;
import entity.Autor;
import entity.Egzemplarz;
import entity.Kategoria;
import entity.Ksiazka;
import entity.Wydanie;
import entity.Wydawnictwo;
import lombok.Data;

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
	
	
	public String saveKategoria() {
		Kategoria kategoria = new Kategoria();
		kategoria.setNazwa(nazwa);
		kategoriaDAO.save(kategoria);
		
		return "ksiazki";
	}
	
	public List<Kategoria> pokazKategorie()
	{
		List<Kategoria> kategorie = new LinkedList<>();
		kategorie = kategoriaDAO.findAll();	
		return kategorie;
	}
	
	public Kategoria getKategoria(int idKategoria)
	{
		Kategoria kategoria = kategoriaDAO.findOne((long)idKategoria);	
		return kategoria;                
	}
	
	public String saveAutor() {
		Autor autor = new Autor();
		autor.setImie(imie);
		autor.setNazwisko(nazwisko);
		autorDAO.save(autor);

		return "ksiazki";
	}
	
	public List<Autor> pokazAutorow()
	{
		List<Autor> autors = new LinkedList<>();
		autors = autorDAO.findAll();	
		return autors;
	}
	
	public Autor getAutor(int idAutora)
	{
		Autor autor = autorDAO.findOne((long)idAutora);	
		return autor;                
	}
	
	public String saveWydawnictwo() {
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
		wydawnictwa = wydawnictwoDAO.findAll();	
		return wydawnictwa;
	}
	
	public String saveWydanie() {
		Wydanie wydanie = new Wydanie();
		wydanie.setRokWydania(rokWydania);
		wydanie.setWydawnictwo(getWydanictwo(idWydawnictwa));
		wydanieDAO.save(wydanie);

		return "ksiazki";
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
	
	public String saveKsiazka() {
		Ksiazka ksiazka = new Ksiazka();
		ksiazka.setTytul(tytul);
		ksiazka.setOpis(opis);
		ksiazka.setStan("Niezniszczona");
		ksiazka.setZdjecie(zdjecie);
		
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
	
	public String saveEgzemplarz() {
		Egzemplarz egzemplarz = new Egzemplarz();
		egzemplarz.setStatus("Dost�pna");
		egzemplarz.setISBN(ISBN);
		egzemplarz.setKsiazka(getKsiazka(idKsiazki));
		egzemplarz.setWydanie(getWydanie(idWydania));


		egzemplarzDAO.save(egzemplarz);

		return "ksiazki";
	}
}
