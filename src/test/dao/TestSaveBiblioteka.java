package test.dao;

import org.junit.jupiter.api.Test;

import controller.BibliotekaController;
import dao.BibliotekaDAO;
import dao.DzienDAO;
import entity.Dzien;

import static org.mockito.Mockito.*;

public class TestSaveBiblioteka {
    
    private BibliotekaController bibliotekaController = new BibliotekaController();
   
    @Test
    public void tmp() { 
        BibliotekaDAO mockBibliotekaDAO = mock(BibliotekaDAO.class);
        DzienDAO mockDzienDAO = mock(DzienDAO.class);
         
        when(mockBibliotekaDAO.save(any())).thenReturn(null);
        when(mockDzienDAO.save(any())).thenReturn(new Dzien());
       
        bibliotekaController.setBibliotekaDAO(mockBibliotekaDAO);
        bibliotekaController.setDzienDAO(mockDzienDAO);
        
        bibliotekaController.saveBiblioteka();      

        verify(mockDzienDAO, times(6)).save(any());
        verify(mockBibliotekaDAO, times(1)).save(any());
    }

}