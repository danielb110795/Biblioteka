package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.Wydawnictwo;

@Named
@Stateless
@LocalBean
public class WydawnictwoDAO extends CrudDAO<Long, Wydawnictwo>{

}