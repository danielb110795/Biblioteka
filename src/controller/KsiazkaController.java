package controller;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import dao.AutorDAO;
import dao.KategoriaDAO;
import dao.KsiazkaDAO;
import dao.WydanieDAO;
import dao.WydawnictwoDAO;
import entity.Autor;
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
	private List <Autor> autors; 
	private List <Kategoria> kategorias;
	
	
	public String saveKategoria() {
		Kategoria kategoria = new Kategoria();
		kategoria.setNazwa(nazwa);
		kategoriaDAO.save(kategoria);
		
		return "ksiazki";
	}
	
	public String saveAutor() {
		Autor autor = new Autor();
		autor.setImie(imie);
		autor.setNazwisko(nazwisko);
		autorDAO.save(autor);

		return "ksiazki";
	}
	
	public String saveWydawnictwo() {
		Wydawnictwo wydawnictwo = new Wydawnictwo();
		wydawnictwo.setNazwa(nazwaWydawnictwa);
		wydawnictwoDAO.save(wydawnictwo);

		return "ksiazki";
	}
	
	public String saveWydanie() {
		Wydanie wydanie = new Wydanie();
		wydanie.setRokWydania(rokWydania);
		wydanie.setWydawnictwo(getWydanictwo(idWydawnictwa));
		wydanieDAO.save(wydanie);

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
	
	public String saveKsiazka() {
		Ksiazka ksiazka = new Ksiazka();
		ksiazka.setTytul(tytul);
		ksiazka.setOpis(opis);
		ksiazka.setStan("Niezniszczona");
		ksiazka.setZdjecie(zdjecie);
		ksiazka.setAutor(autors);
		ksiazka.setKategoria(kategorias);
		
		ksiazkaDAO.save(ksiazka);

		return "ksiazki";
	}
	
}
