package controller;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
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
	
	/*public String getBookingEtaps() {
		return "booking-etaps.xhtml";
	}
	
	public String getClientAccountMenu() {
		return "client-account-menu.xhtml";
	}
	
	public String getConfigurationMenu() {
		return "configuration-menu.xhtml";
	}
	
	public String getTasksMenu() {
		return "tasks-menu.xhtml";
	}*/
	
}