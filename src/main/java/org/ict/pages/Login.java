package org.ict.pages;

import java.io.IOException;
import java.nio.charset.*;
import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.chrome.*;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.ict.constants.AutomationConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ict.base.BaseClass;


public class Login extends BaseClass
{
	
	public Login(WebDriver driver) 
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(id="username")
	WebElement name;
	
	@FindBy(id="password")
	WebElement pwd;
	
	@FindBy(xpath="//button[text()='Login']")
	WebElement loginBtn;
	
	@FindBy(xpath="//div[@role='alert']")
	WebElement errMsg;
	
	
	@FindBy(id="basic-nav-dropdown")	//Dashboard - Logged in user)
	WebElement userText;
	
	//div[@id='basic-navbar-nav']/child::div/child::div/child::div/a[1] //Dashboard - Role (Dropdown)
	@FindBy(xpath="//div[@data-bs-popper='static']/a[1]")    
	WebElement role;
	
	
	public void validLogin(String strUser , String strPwd)  throws IOException 
	{
	
		name.sendKeys(strUser);
		pwd.sendKeys(strPwd);
		loginBtn.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		 
	}
	
	public String resultText()
	{

		Actions act = new Actions(driver);
    	act.moveToElement(userText).perform();
		userText.click();
		
		String strResult = role.getText();
        return strResult;
	
	}
	
	
	public void invalidLogin(String strInvUser , String strInvPwd ) throws IOException
	{
		 
		name.sendKeys(strInvUser);
		pwd.sendKeys(strInvPwd);
		loginBtn.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	
	}   
	
	
	public String getErrMsg()
	{
		 String strErrMsg = errMsg.getText();
         return strErrMsg;
		
	}	

}

	
	 
