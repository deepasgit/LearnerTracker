package com.ict.testcases;

import java.io.IOException;

import org.ict.constants.AutomationConstants;
import org.ict.excel.ExcelUtility;
import org.ict.pages.Login;
import org.ict.pages.PlOfficer;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ict.base.BaseClass;

public class PlOfficer_TC extends BaseClass{
	  
		Login loginpg;
		PlOfficer plofficerpg;
		
	
		@BeforeMethod
		public void tearUp() throws IOException
		{
			loginpg = new Login(driver);
			plofficerpg = new PlOfficer(driver);
		
			String strUserName = ExcelUtility.getData(3, 0);  
			String strPassword = ExcelUtility.getData(3, 1);  
			loginpg.validLogin(strUserName,strPassword);
			
		}
		

		@Test(priority=1)
		public void show_learners() throws IOException, InterruptedException
		{
			
			SoftAssert softAssert = new SoftAssert();
			
			String actResult = plofficerpg.getLearnerHeading();
			String expResult = AutomationConstants.LEARNER_TABLE_HEAD;
			System.out.println("LearnerHeading : " +actResult);
			softAssert.assertEquals(actResult, expResult, "First soft assert");
		
			Thread.sleep(1000);
			//plofficerpg.displayLearnersForm();
			plofficerpg.iterateTable(1,"");
			
			String actual	= plofficerpg.getLearnerFormTitle();
			String expected = AutomationConstants.LEARNER_FORM_TITLE;
			System.out.println("LearnerFormTitle : " +actual);
			softAssert.assertEquals(actual, expected, "Second soft assert");
			
			softAssert.assertAll();
			
		}
		
		
	  	@Test(priority=2)  
		public void edit_lrnr_status() throws InterruptedException, IOException //old
		{
			Thread.sleep(1000);
			
			plofficerpg.iterateTable(1,""); 
			plofficerpg.editPlcStatus();
						
			plofficerpg.submitForm();
			String expStat = ExcelUtility.getData(2, 5);
 
			plofficerpg.iterateTable(2, expStat);
			String updatedStat = plofficerpg.iterateTable(2, expStat);
			
			System.out.println("New status : " + updatedStat + " Exp :" +expStat);
			Assert.assertEquals(updatedStat,expStat);
			
		}

		
		@Test(priority=3)   
		public void edit_lrnr_stat_invalid() throws InterruptedException, IOException
		{
			Thread.sleep(1000);
			
			plofficerpg.iterateTable(1,"");  
			
			String actResult = plofficerpg.editPlcStatNil();
			String expResult = AutomationConstants.PLC_STAT;
			
			plofficerpg.submitForm();
			
			System.out.println("Actual : " +actResult);
			System.out.println("Expected : " +expResult);
			
			Assert.assertNotEquals(actResult, expResult);
			
		}
				
	
		@Test(priority=4)  
		public void check_previous_btn() throws InterruptedException
		{
			
			plofficerpg.iterateTable(1,""); 
			Thread.sleep(1000);
			plofficerpg.previousPage();
			
			String actResult =  plofficerpg.getLearnerHeading();
			String expResult =  AutomationConstants.LEARNER_TABLE_HEAD;
			System.out.println("Act : " + actResult + " Exp :" +expResult);
			Assert.assertEquals(actResult,expResult);
			
		}
		
		@Test(priority=5) 
		public void logout() throws InterruptedException
		{
			
			plofficerpg.log_out();
			
			String actual	= plofficerpg.getIctText();
			String expected = AutomationConstants.HME_ICT_TXT;
			System.out.println("ICT Text : " +actual);
			Assert.assertEquals(actual, expected);
				
		}
	


		/*

	  	@Test(priority=2)      //old
		public void edit_lrnr_status() throws InterruptedException, IOException
		{
			Thread.sleep(1000);
			
			plofficerpg.iterateTable(1); 
			plofficerpg.editPlcStatus();
						
			plofficerpg.submitForm();
		
			String expResult = ExcelUtility.getData(2, 5);
			//System.out.println("Status  : " +actResult);  
			
			String actResult = plofficerpg.getUpdatedStat();
			//System.out.println("Updated status in test#2  : " +actResult);
			
			Assert.assertEquals(actResult,expResult);
			
		 }
	  	
	  	*/
			/*
			String actResult = AutomationConstants.LNR_UPDATE_MSG;
			String expResult = plofficerpg.getAlertText();
			
			System.out.println("Title : " +actResult);
			System.out.println("Expected : " +expResult);
			
			Assert.assertEquals(actResult,expResult);
			*/
		
	

		
		@AfterMethod
		public void tearDown()
		{
			this.driver.close();
		} 

}
