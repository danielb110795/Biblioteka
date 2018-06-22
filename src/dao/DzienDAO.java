package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Dzien;

@Named
@Stateless
@LocalBean
public class DzienDAO extends CrudDAO<Long, Dzien>{

}