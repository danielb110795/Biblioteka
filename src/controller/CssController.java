package controller;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
@LocalBean
public class CssController {
	private final String basePath = "css/";

	private String getCssPath(String cssPath) {
		return basePath + cssPath;
	}
	
	public String getMainCss() {
		return getCssPath("main.css");
	}
}