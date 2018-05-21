package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Ksiazka;

@Named
@Stateless
@LocalBean
public class KsiazkaDAO extends CrudDAO<Long, Ksiazka>{

}