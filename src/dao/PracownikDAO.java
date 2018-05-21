package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Pracownik;

@Named
@Stateless
@LocalBean
public class PracownikDAO extends CrudDAO<Long, Pracownik>{

}