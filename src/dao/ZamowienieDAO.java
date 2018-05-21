package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Zamowienie;

@Named
@Stateless
@LocalBean
public class ZamowienieDAO extends CrudDAO<Long, Zamowienie>{

}