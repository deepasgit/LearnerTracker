package org.ict.pages;

import java.awt.Dialog;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.ict.excel.ExcelUtility;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.ict.base.BaseClass;

public class Admin_Dashboard  extends BaseClass
{
	
	public Admin_Dashboard(WebDriver driver) 
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="basic-nav-dropdown")
	WebElement adm_uname;
	
	@FindBy(xpath="//ion-icon[@name='person-add-outline']/parent::button")
	WebElement addUserBtn;
	
	@FindBy(xpath="//th[text()='Role']")
	WebElement tblHeadTxt;
	
	
	//Elements in Add User Form
	
	@FindBy(xpath="//h3[text()='Add Users']")  
	WebElement addUserHeadTxt;
	
	@FindBy(id="name")
	WebElement name;
	
	@FindBy(id="email")
	WebElement email;
	
	@FindBy(id="username")
	WebElement username;
	
	@FindBy(id="password")
	WebElement password;
	
	@FindBy(name="roleInputs")
	WebElement role;

	@FindBy(xpath="//button[text()='Submit']")
	WebElement submitBtn;
		
	@FindBy(xpath="//p[contains(text(),'Must contain letters')]")    //Name field err msg
	WebElement errText;
	
	@FindBy(xpath="//button[text()='OK']")    //alert box 'OK' button
	WebElement okBtn;
	
	@FindBy(xpath="//div[@id='swal2-html-container']")    //alert box text
	WebElement alertText;
	
	@FindBy(linkText = "Logout")
	WebElement logout;
	
	@FindBy(xpath = "//p[text()='ICTAK - Learner Tracker']")   // For Logout - Homepage Text
	WebElement ictText;
	
	@FindBy(xpath="//table[@class='table table-bordered table-hover']/tbody/tr[5]")
	List<WebElement> tblUsersList;
	
	@FindBy(xpath="//table[@class='table table-bordered table-hover']/tbody/tr[5]/td[2]") 
	WebElement usrEmail;
	
	@FindBy(xpath="//table[@class='table table-bordered table-hover']")
	List<WebElement> table;

	
	public void displayForm()
	{
		addUserBtn.click();
	}

	 
	public void fillUserDetails(String strName,String strEmail,String strUsername,String strPwd,String strRole)
	{
		name.sendKeys(strName);
		email.sendKeys(strEmail);
		username.sendKeys(strUsername);
		password.sendKeys(strPwd);
		role.sendKeys(strRole);
			
	}
	
	
	//**To Edit user details**//
	public String editUserDetails() throws InterruptedException, IOException
	{
		
		Thread.sleep(1000);
		password.clear();
		    
		String newpass = ExcelUtility.getData(5, 1);
		password.sendKeys(newpass);
		System.out.println("New password : " +newpass); 
	
		return newpass;

	}
	
	
	//*******To Update/Delete the record********//
	public String iterateTbl(int intAction ,  String strMatch)
	{
	      
		String strPassTbl="";
		String strPwd="";
		String strLnrEmail="";
	
		//// Get all the rows in the table
        for (WebElement row : tblUsersList) {
        	
        	//// Get all the cells in the row
             List<WebElement> cells = row.findElements(By.tagName("td"));
			 
             strPassTbl  = cells.get(3).getText();
             strPwd 	 = cells.get(4).getText();
             strLnrEmail = cells.get(1).getText();
             
             switch(intAction) {
             case 1:
            	 cells.get(5).click();
            	 System.out.println("Passwrd : " +strPassTbl);
            	 
            	 break;
            	 
             case 2:
            	 	             	 	 
            	 if ( strPassTbl.compareTo(strMatch)==0) 
            	 {
            		 System.out.println("Password : " +strPassTbl + " " +strMatch );
            		 //strPwd = strMatch;
            		 strPassTbl = strMatch;
            	 }
            	
                 break;
                 
             case 3:
            	 
            	 cells.get(6).click();
            	 System.out.println("case 3 : Delete");
                 break;
                 
            case 4:
          	 	 
            	 if ( strLnrEmail.compareTo(strMatch)!=0) 
            	 {
            		 System.out.println("Email is : " +strLnrEmail + " and " +strMatch );
            		 System.out.println("Deletion confirmed");
  
            		 strPassTbl = strLnrEmail;
            	 }
            	
                 break;
                 
            default:
              
           }
         }

      	return strPassTbl;

    }
	
	public String getEmail()

	{ 
		return usrEmail.getText();
		
	}
	
	public void log_out() throws InterruptedException
	{
		Thread.sleep(2000);
		
		Actions act = new Actions(driver);
    	//act.moveToElement(plc_uname).build().perform();
		act.moveToElement(adm_uname).perform();
		
		adm_uname.click();
		logout.click();
	}
	
	public String getIctText()
	{
		return ictText.getText();
	}
	
	public String getAddFormTxt()
	{
		return addUserHeadTxt.getText();
	}
	
	
	public void submitForm()
	{
		submitBtn.click();
	}
	
	
	public String getAlertText()
	{
		String strAlertText = alertText.getText();
		okBtn.click();
		return strAlertText;
	}
	
	public String displayUsers()
	{ 
		return tblHeadTxt.getText();	
	}
	
	public String getErrText()
	{
		String userErrText = errText.getText();
		return userErrText;
	}
	
}

