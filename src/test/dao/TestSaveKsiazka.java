package test.dao;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import controller.KsiazkaController;
import dao.AutorDAO;
import dao.KategoriaDAO;
import dao.WydawnictwoDAO;
import entity.Kategoria;

public class TestSaveKsiazka {

	private KsiazkaController ksiazkaController = new KsiazkaController();

	@Test
	public void testSaveKategoria() {

		KategoriaDAO mockKategoriaDAO = mock(KategoriaDAO.class);
		when(mockKategoriaDAO.save(any())).thenReturn(null);
		ksiazkaController.setKategoriaDAO(mockKategoriaDAO);
		ksiazkaController.saveKategoria(1);
		verify(mockKategoriaDAO, times(1)).save(any());
	}

	@Test
	public void testSaveWydawnictwo() {

		WydawnictwoDAO mockWydawnictwoDAO = mock(WydawnictwoDAO.class);
		when(mockWydawnictwoDAO.save(any())).thenReturn(null);
		ksiazkaController.setWydawnictwoDAO(mockWydawnictwoDAO);
		ksiazkaController.saveWydawnictwo(1);
		verify(mockWydawnictwoDAO, times(1)).save(any());
	}

	@Test
	public void testSaveAutor() {

		AutorDAO mockAutorDAO = mock(AutorDAO.class);
		when(mockAutorDAO.save(any())).thenReturn(null);
		ksiazkaController.setAutorDAO(mockAutorDAO);
		ksiazkaController.saveAutor(1);
		verify(mockAutorDAO, times(1)).save(any());
	}

	@Test
	public void testSaveKategoria2() {
		String nazwaKategorii = "test";
		KategoriaDAO mockKategoriaDAO = mock(KategoriaDAO.class);

		when(mockKategoriaDAO.save(any())).thenReturn(null);
		when(mockKategoriaDAO.findAll()).thenReturn(new LinkedList<>());
		ksiazkaController.setKategoriaDAO(mockKategoriaDAO);
		ksiazkaController.setNazwa(nazwaKategorii);

		ksiazkaController.saveKategoria(1);
		when(mockKategoriaDAO.findAll()).thenReturn(Arrays.asList(Kategoria.builder().nazwa(nazwaKategorii).build()));
		ksiazkaController.saveKategoria(1);

		verify(mockKategoriaDAO, times(1)).save(any());
	}
}
