package test;

import org.junit.jupiter.api.Test;

import controller.BibliotekaController;
import dao.BibliotekaDAO;
import dao.DzienDAO;
import entity.Dzien;

import static org.mockito.Mockito.*;

public class ExampleTest {
    
    private BibliotekaController controller = new BibliotekaController();
   
    @Test
    public void tmp() { 
        BibliotekaDAO mockBibliotekaDAO = mock(BibliotekaDAO.class);
        DzienDAO mockDzienDAO = mock(DzienDAO.class);
         
        when(mockBibliotekaDAO.save(any())).thenReturn(null);
        when(mockDzienDAO.save(any())).thenReturn(new Dzien());
       
        controller.setBibliotekaDAO(mockBibliotekaDAO);
        controller.setDzienDAO(mockDzienDAO);
        
        controller.saveBiblioteka();      

        verify(mockDzienDAO, times(6)).save(any());
        verify(mockBibliotekaDAO, times(1)).save(any());
    }

}