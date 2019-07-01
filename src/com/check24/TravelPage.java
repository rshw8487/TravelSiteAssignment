package com.check24;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.pagelocators.TravelPOM;
import com.util.TestDataFromExcel;

public class TravelPage implements TravelPOM {

	WebDriver driver;

	@BeforeMethod
	public void beforeMethod() {
		
		/* Create object for Web driver */
		driver = new ChromeDriver(); 
		
		
		/*Configure the system properties available*/
           System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\rahul.sonawane\\git\\Travel\\chromedriver.exe"); 

		
		/* The login URL */
	    String baseUrl = "https://kundenbereich.check24.de/user/register.html"; 
		driver.get(baseUrl);
		driver.manage().window().maximize();
	}
	

	@Test(dataProvider = "getData")
	/*
	 * To Read the data from the DataDrivenModel(Currently using excel file to
	 * read the data
	 */
	
	public void testRegisterForJourney(String email, String password,String Result) {

		/*
		 * Registration And validation reading data from Excel file by using
		 * Data driven approach
		 */
		
		waitFor(5000);
		
		/* Click on the Register Button*/
		  	 		 
		((JavascriptExecutor) driver)
				.executeScript("document.getElementById('register').click()");
		waitFor(5000);
		
		/* Get the element locator for the EMAIL Field*/
		 
		driver.findElement(EMAIL).sendKeys(email.trim()); 
		waitFor(2000);

		/*Get the element locator for the PASSWORD Field*/
		 	 
		WebElement ele = driver.findElement(PASSWORD); 
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView();", ele);

		// wait for the auto complete options to appear for the origin

		driver.findElement(PASSWORD).sendKeys(password.trim()); 
		waitFor(2000);

		// Pass the Password data that read from Excel file
		
		driver.findElement(PASSWORDREP).sendKeys(password.trim());
		waitFor(2000);

		// Click on the Register Button
		driver.findElement(REGISTER).click(); 

		waitFor(5000);
		String parentWindowHandle1 = driver.getWindowHandle(); 
		
		// To handle the multiple windows
		Set<String> allWindows1 = driver.getWindowHandles();

		for (String window : allWindows1) {
			driver.switchTo().window(window);

			
			// Click on the pop-up window accept check box
			
			WebElement element = driver.findElement(By.id("termsaccepted")); 
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click().perform();

			waitFor(5000);
			
			// Click on the pop-up window button
			
			driver.findElement(
					By.xpath("//*[@id='c24-dialog-points-modal']/div/div/button")) .click();
			waitFor(5000);
			driver.switchTo().window(parentWindowHandle1);
		}
		
		waitFor(5000);

		WebElement divElement = driver.findElement(By
				.xpath("//*[@id='c24-kb-container']/div[1]/div/div/h1")); //

		String result = divElement.getText();

		if (result.trim().equals("CHECK24 Punkte")) {
			new TestDataFromExcel("TravelTest.xlsx", "Register")
					.updateDateToSpecificCell("Result",
							"Registeration sucessfully done");
		}

		/* Logout from the dash board page */
		String LogoutUrl = "https://kundenbereich.check24.de/user/logout.html";
		driver.get(LogoutUrl);
		waitFor(5000);

		// Login to the Account using login credentials
		String LoginUrl = "https://kundenbereich.check24.de/user/login.html";
		driver.get(LoginUrl);
		waitFor(5000);
		
		// Click on Register button
		((JavascriptExecutor) driver)
				.executeScript("document.getElementById('c24-kb-register-btn').click()"); 
		waitFor(5000);
		
		// Find the password element	
		WebElement ele1 = driver.findElement(PASSWORD); 
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView();", ele1);

		//wait for the auto complete options to appear for the origin
		//Pass the password information	
		driver.findElement(PASSWORD).sendKeys(password.trim()); 																												
		waitFor(2000);
		driver.findElement(LOGIN).click();
		waitFor(5000);

		// Search Action
		String searchUrl = "https://flug.check24.de/produkte"; 														
		driver.get(searchUrl);
		waitFor(6000);
		
		//Key in the departure place
		driver.findElement(DPTVISIBLE).sendKeys("Spanien");													
		waitFor(5000);
		Actions action = new Actions(driver);
		action.sendKeys(Keys.TAB).build().perform();
		
		// Key in the destination place
		driver.findElement(DSTVISIBLE).sendKeys("BRE"); 														
		waitFor(5000);
		driver.findElement(DFCB).click();
		waitFor(5000);
		
		// Click on search button
		driver.findElement(SERCHFLGSUBMIT).click(); 
		waitFor(8000);

	}

	@DataProvider
	public Object[][] getData() {
		
		// Get the data from the excel
		TestDataFromExcel sheet = new TestDataFromExcel("TravelTest.xlsx",
				"Register"); // Get the excel work book name and Sheet name

		Map<Integer, ArrayList> memoMap = sheet.readAllRows();

		Object[][] data = new Object[memoMap.size()][];
		int i = 0;
		for (Map.Entry<Integer, ArrayList> entry : memoMap.entrySet()) {
			String row = entry.getValue().toString().replace("[", "")
					.replace("]", "");
			String[] arr = row.split(",");
			data[i++] = arr;
		}
		return data;
	}

	private void waitFor(int durationInMilliSeconds) {
		try {
			Thread.sleep(durationInMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace(); // To Get the time if any error occurred
		}
	}

	@AfterMethod
	public void afterMethod() {
		
		// To close the driver
		driver.quit();
	}
}