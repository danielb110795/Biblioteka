package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Wydanie;


@Named
@Stateless
@LocalBean
public class WydanieDAO extends CrudDAO<Long, Wydanie>{

}