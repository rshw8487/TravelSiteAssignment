# Travel site automation for Zen jobs

# CHECK24 Travel WebSite

In this project, I have created test cases on the site that range from Registration (Validation),Logging and search criteria. 
Selenium WebDriver is used for test automation for Chrome browser, TestNG for report generation, and Data Driven framework handles 
various data inputs for Registration and login page.

# Basic test framework structure

In this framework, 

-> Test Scenarios (Registration,Login and Search criteria) can find inside com.check24->TravelPage.java class

-> Reusable element locators can find inside seperate com.pagelocators->TravelPOM.java class(page object model)

-> Helper methods and excel data read code will be present inside com.util->HelperMethod.java and TestDataFromExcel.java class.

-> All external jars would be present under Lib folder

-> Conf.properties and Excel file to read different inputs for registration page can find under testdata older ->conf.properties
       and TravelTest.xlsx.
    
-> TestNG reports will be present under test-output folder.    

# Compilation
To compile, following dependencies needs to add to execute the test scenarios:

-> Requird jars:

   1.selenium-server-standalone-2.53.1.jar

   2.TestNG jars

   3.POI jars

   4.Other jars added in lib folder

   5.Chromedriver


->Config Properties

   1.Need to add excel file path from local drive inside conf.properties file.

   2.Add test data like 'email' and 'password' under created excel sheet e.g 
     TravelTest.xlsx under testdata folder to read the different inputs for registration and login.
  
  
-> How to run the project
   1. Right click on project->Run as "TestNG Test"

# Output
All of the test results can be found in the folder test-output. The emailable-report.html displays the results formatted in tables with other useful information about the tests
and also after successful registration message will be display in Result column of Test Data-> TravelTest.xlsx file.
