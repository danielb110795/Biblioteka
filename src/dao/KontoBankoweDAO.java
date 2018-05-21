package dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import entity.KontoBankowe;

@Named
@Stateless
@LocalBean
public class KontoBankoweDAO extends CrudDAO<Long, KontoBankowe>{

}