package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Wypozyczenie;

@Named
@Stateless
@LocalBean
public class WypozyczenieDAO extends CrudDAO<Long, Wypozyczenie>{

}