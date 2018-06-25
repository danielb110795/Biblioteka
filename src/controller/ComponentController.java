package controller;

import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Data;

@RequestScoped
@Named
@Data
@LocalBean
public class ComponentController {

	public String getMainMenu() {
		return "mainMenu.xhtml";
	} 
	
	public String getHead() {
		return "head.xhtml";
	}
	
	public String getMenuAdmin() {
		return "menuAdmin.xhtml";
	}
	
	public String getMenuPracownik() {
		return "menuPracownik.xhtml";
	}
	
	public String getMenuCzytelnik() {
		return "menuCzytelnik.xhtml";
	}
}