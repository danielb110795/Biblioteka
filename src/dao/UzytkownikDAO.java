package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Uzytkownik;

@Named
@Stateless
@LocalBean
public class UzytkownikDAO extends CrudDAO<Long, Uzytkownik>{

}