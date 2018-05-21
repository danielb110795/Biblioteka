package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Autor;

@Named
@Stateless
@LocalBean
public class AutorDAO extends CrudDAO<Long, Autor>{

}