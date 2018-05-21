package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Kategoria;

@Named
@Stateless
@LocalBean
public class KategoriaDAO extends CrudDAO<Long, Kategoria>{

}