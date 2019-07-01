
package com.pagelocators;

import org.openqa.selenium.By;

public interface TravelPOM {
	
	By EMAIL = By.id("email");
	
	By PASSWORD = By.id("password");
	
	By PASSWORDREP = By.id("passwordrepetition");
	
	By REGISTER = By.name("register");
	
	By LOGIN = By.name("login");
	
	By DPTVISIBLE = By.id("dptVisible");
	
	By DSTVISIBLE = By.id("dstVisible");
	
	By DFCB = By.id("directFlightsCheckbox");
	
	By SERCHFLGSUBMIT = By.id("search-flights-submit");

}
