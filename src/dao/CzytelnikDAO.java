package dao;



import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;


import entity.Czytelnik;


@Named
@Stateless
@LocalBean
public class CzytelnikDAO extends CrudDAO<Long, Czytelnik>{


}