package org.ict.pages;

import java.io.IOException;
import java.util.List;

import org.ict.excel.ExcelUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


import com.ict.base.BaseClass;

public class PlOfficer extends BaseClass
{
	
	public PlOfficer(WebDriver driver) 
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	

	@FindBy(linkText = "Placement")   
	WebElement placement;
	
	@FindBy(tagName = "h3") // Learner's form Title
	WebElement titleLnrForm;
	
	@FindBy(id="basic-nav-dropdown")
	WebElement plc_uname;
	
	@FindBy(xpath="//button[contains(text(),'Back')]")
	WebElement backBtn;
	
	@FindBy(linkText = "Logout")
	WebElement logout;
	
	@FindBy(xpath = "//p[text()='ICTAK - Learner Tracker']")   // For Logout - Homepage Text
	WebElement ictText;
		
	@FindBy(xpath = "//table/thead/tr[1]/th[1]")  //Table heading
	WebElement tblheadingtxt;
	
	@FindBy(xpath="//table[@class='table table-bordered table-hover']/tbody/tr[4]")
	List<WebElement> tblLnrList;
	
		
	//**** Elements in Learners Form ****//
	
	@FindBy(xpath="//select[@name='pstatus']")
	WebElement plcStatus;

	@FindBy(xpath="//button[text()='Submit']")
	WebElement submitBtn;
	
	@FindBy(xpath="//div[@id='swal2-html-container']")    //alert box text
	WebElement alertText;
	
	@FindBy(xpath="//select[@name='pstatus']/child::option[1]")  //-Select-
	WebElement plcStatusInv;
	
	
	public String getLearnerHeading()
	{
		return tblheadingtxt.getText();
	}
	 
	
	public String getLearnerFormTitle()
	{
		return titleLnrForm.getText();
	}
	
	
	public void submitForm()
	{
		submitBtn.click();
	}
	
	
	public void editPlcStatus(String strPlcStatus)
	{
		plcStatus.sendKeys(strPlcStatus);
	}
	
	public String getAlertText()
	{
		String strAlertText = alertText.getText();
		return strAlertText;
	}
	
		
		//**To Edit the placement status of the Learner**//
		public void editPlcStatus() throws InterruptedException, IOException  
		{
			
			Thread.sleep(500);
			Select drpStatus = new Select(plcStatus);
			
			String newStatus = ExcelUtility.getData(2, 5);
			drpStatus.selectByVisibleText(newStatus);
			
			//System.out.println("Edited Status : " +newStatus); 
		}
				
		
		public void log_out() throws InterruptedException
		{
			Thread.sleep(2000);
			
			Actions act = new Actions(driver);
	    	//act.moveToElement(plc_uname).build().perform();
			act.moveToElement(plc_uname).perform();
			
			plc_uname.click();
			logout.click();
		}
		
		
		public String getIctText()
		{
			return ictText.getText();
		}
		
		//**Edit the placement status [-Select-]**//
		public String editPlcStatNil() throws InterruptedException, IOException  
		{
			
			Thread.sleep(500);
			Select drpStatus = new Select(plcStatus);
			
			String newStatus = ExcelUtility.getData(4, 5);
			drpStatus.selectByVisibleText(newStatus);
			
			return plcStatusInv.getText();
			
		}
				
		
	public void previousPage()
	{
		backBtn.click();
	}

		
	//*******To Update the placement status********//
		public String iterateTable(int intAction ,  String strMatch) {
	       
	     
			String strPlStatus="";
			String strLID="";
		
			//// Get all the rows in the table
	        for (WebElement row : tblLnrList)
	        {
	        	//// Get all the cells in the row
	             List<WebElement> cells = row.findElements(By.tagName("td"));
				 
	             strPlStatus  = cells.get(6).getText();
	             strLID 	  = cells.get(0).getText();
	
	             
	             switch(intAction) {
	             case 1:
	            	 
	            	 cells.get(7).click();
	            	 System.out.println("Placement status : " +strPlStatus);
	            	 
	            	 break;
	            	 
	             case 2:
	            	 	 //System.out.println("Case 2 Plc status OUT :" +strPlStatus + "| |" +strMatch +"|" );
	            	 if ( strPlStatus.compareToIgnoreCase(strMatch)== 0 )  
	            	 {
	            		 //System.out.println("Case 2 Plc status IN : " +strPlStatus + " " +strMatch );
	            		 strPlStatus = strMatch;
	            	 }
	                 break;
	                 
	            default:
	            }
	         }
			
	      	return strPlStatus;
			}
		
		
		public void clkPlacementLink()
		{ 
			placement.click();
		}
		
		
		
	  	/*
		public String result_text(String strLrnID) throws InterruptedException
		{
			String text="";

			Thread.sleep(1000); 
			for (WebElement element : tblLnrIDs) 
			{
				
				System.out.println("inside loop");
	             text = element.getText();
	             System.out.println( text + " first " + strLrnID );
	        }
			System.out.println( text + " second  " + strLrnID );
			return text;
				 
		}
		*/
		
}