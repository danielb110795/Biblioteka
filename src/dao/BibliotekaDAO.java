package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Biblioteka;

@Named
@Stateless
@LocalBean
public class BibliotekaDAO extends CrudDAO<Long, Biblioteka>{

}