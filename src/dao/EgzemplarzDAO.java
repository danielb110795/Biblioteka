package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Egzemplarz;


@Named
@Stateless
@LocalBean
public class EgzemplarzDAO extends CrudDAO<Long, Egzemplarz>{

}