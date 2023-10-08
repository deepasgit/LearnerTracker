package com.ict.testcases;

import java.io.IOException;
import java.time.Duration;

import org.ict.constants.AutomationConstants;
import org.ict.excel.ExcelUtility;
import org.ict.pages.Admin_Dashboard;
import org.ict.pages.Login;
import org.ict.pages.PlOfficer;
import org.ict.pages.Trainer;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ict.base.BaseClass;

public class Admin_DashboardTC extends BaseClass{
	  
		Login loginpg;
		Admin_Dashboard adm_dashboard;
		Trainer trainerpg;
		PlOfficer plofficerpg;
		
		
		@BeforeMethod
		public void tearUp() throws IOException
		{
			loginpg 	  = new Login(driver);
			adm_dashboard = new Admin_Dashboard(driver);
			trainerpg	  = new Trainer(driver);
			plofficerpg = new PlOfficer(driver);
			
			String strUserName = ExcelUtility.getData(1, 0);    //3,2
			String strPassword = ExcelUtility.getData(1, 1);    //4,2
			loginpg.validLogin(strUserName,strPassword);
			
		}

		@Test(priority=1) 
		public void display_all_users() throws InterruptedException
		{
			adm_dashboard.displayUsers();
			
			String expResult = adm_dashboard.displayUsers();
			String actResult = AutomationConstants.ADM_USERS_HEAD;
			Assert.assertEquals(actResult,expResult);
		}
			
	
		@Test(priority=2)
		public void add_new_user() throws IOException, InterruptedException
		{
			
			Thread.sleep(1000);
			
			SoftAssert softAssert = new SoftAssert();
			
			adm_dashboard.displayForm();
			String actResult = adm_dashboard.getAddFormTxt();
			String expResult = AutomationConstants.ADM_ADD_USER_HEAD;
			
			softAssert.assertEquals(actResult, expResult, "First soft assert: Add User - Heading");
			
			String strUname 	= ExcelUtility.getData(1, 2);
			String strEmailId 	= ExcelUtility.getData(2, 2);
			String strUserName	= ExcelUtility.getData(3, 2);
			String strPasswrd 	= ExcelUtility.getData(4, 2);
			String strUserRole	= ExcelUtility.getData(5, 2);
			
			adm_dashboard.fillUserDetails(strUname,strEmailId,strUserName,strPasswrd,strUserRole);
			adm_dashboard.submitForm();
			
			String expAlert = adm_dashboard.getAlertText();
			String actAlert = AutomationConstants.ADM_ADD_USER_ALERT;
			
			softAssert.assertEquals(actAlert, expAlert, "Second soft assert: Add User - Heading");
			
			softAssert.assertAll();
		
		}
		
	
		@Test(priority=3)
		public void add_new_user_invalid() throws IOException, InterruptedException
		{
			
			Thread.sleep(1000);
			adm_dashboard.displayForm();
			
			String strUname 	= ExcelUtility.getData(6, 2);
			String strEmailId 	= ExcelUtility.getData(2, 2);
			String strUserName 	= ExcelUtility.getData(3, 2);
			String strPasswrd 	= ExcelUtility.getData(4, 2);
			String strUserRole 	= ExcelUtility.getData(5, 2);
			
			adm_dashboard.fillUserDetails(strUname,strEmailId,strUserName,strPasswrd,strUserRole);
			adm_dashboard.submitForm();
			
			String expResult = adm_dashboard.getErrText();
			String actResult = AutomationConstants.ADM_ADD_USERNAME_ERR;
			
			Assert.assertEquals(actResult,expResult);
		
		}
		
	
		@Test(priority=4)  
		public void edit_user_pwd() throws InterruptedException, IOException
		{
			Thread.sleep(1000);
			
			adm_dashboard.iterateTbl(1,"");
			adm_dashboard.editUserDetails();
			adm_dashboard.submitForm();
			
			String expPass = ExcelUtility.getData(5, 1);
			
			adm_dashboard.iterateTbl(2, expPass);
			
			String updatedPass = adm_dashboard.iterateTbl(2, expPass);
			System.out.println("New pass is : " +updatedPass);
			Assert.assertEquals(expPass,updatedPass);
			
		}

		
		@Test(priority=5) 
		public void logout() throws InterruptedException
		{
			
			adm_dashboard.log_out();
			
			String actual	= adm_dashboard.getIctText();
			String expected = AutomationConstants.HME_ICT_TXT;
			System.out.println("ICT Text : " +actual);
			Assert.assertEquals(actual, expected);
				
		}


		@Test(priority=6)  
		public void delete_user() throws InterruptedException, IOException
		{
			
			Thread.sleep(1000);
			adm_dashboard.iterateTbl(3,"");
			
			String strUsrEmail = adm_dashboard.getEmail();   //e-mail in table row
			adm_dashboard.iterateTbl(4,strUsrEmail);
			System.out.println("Deleted Email is : " + strUsrEmail);
			
			System.out.println("Email found as : " + adm_dashboard.iterateTbl(4,strUsrEmail));
			Thread.sleep(2000);
			System.out.println("Current Email is : " + adm_dashboard.getEmail());
			Assert.assertNotEquals(adm_dashboard.iterateTbl(4,strUsrEmail),strUsrEmail);
				
		}
		
		
		
		/////[[[[Learners section]]]] //  [[[[[Trainer]]]]////
		
		@Test(priority=7) 
		public void disp_learners() throws InterruptedException
		{
			
			SoftAssert softAssert = new SoftAssert();
			trainerpg.clkLearnersLink();
			
			String actResult = trainerpg.getLearnerHeading();
			String expResult = AutomationConstants.LEARNER_TABLE_HEAD;
			
			softAssert.assertEquals(actResult, expResult, "First soft assert: Table Heading");
		
			trainerpg.displayLearnersForm();
			String actual= trainerpg.getLearnerFormTitle();
			String expected = AutomationConstants.LEARNER_FORM_TITLE;
			
			softAssert.assertEquals(actual, expected, "Second soft assert: Form title");
			softAssert.assertAll();
				
		}
		
		@Test(priority=8)
		public void add_learner() throws IOException, InterruptedException
		{
			
			Thread.sleep(1000);
			trainerpg.clkLearnersLink();
			trainerpg.displayLearnersForm();
			
			String strLid	  = ExcelUtility.getData(1, 4);
			String strName	  = ExcelUtility.getData(2, 4);
			String strCourse  = ExcelUtility.getData(3, 4);
			String strPrj 	  = ExcelUtility.getData(4, 4);
			String strBatch   = ExcelUtility.getData(5, 4);
			String strCStatus = ExcelUtility.getData(6, 4);
			
			trainerpg.fillLearnerDetails(strLid,strName,strCourse,strPrj,strBatch,strCStatus);
			trainerpg.submitLearnerForm();
			
			String expResult = trainerpg.getAlertText();
			String actResult = AutomationConstants.ADM_ADD_USER_ALERT;
			
			Assert.assertEquals(actResult,expResult);
			
		}
		
		@Test(priority=9)
		public void add_new_lrnr() throws IOException, InterruptedException
		{
			
			trainerpg.clkLearnersLink();
			trainerpg.displayLearnersForm();
			
			String strLid 	  = ExcelUtility.getData(1, 4);
			String strName 	  = ExcelUtility.getData(2, 4);
			String strCourse  = ExcelUtility.getData(3, 4);
			String strPrj 	  = ExcelUtility.getData(4, 4);
			String strBatch   = "";
			String strCStatus = "";
			
			trainerpg.fillLearnerDetails(strLid,strName,strCourse,strPrj,strBatch,strCStatus);
			trainerpg.submitLearnerForm();
			
			String expResult = trainerpg.getErrText();
			String actResult = AutomationConstants.LNR_BATCH_ERR;
			
			Assert.assertEquals(actResult,expResult);
			
		}
		
		
		@Test(priority=10)  
		public void csv_file_upload() throws InterruptedException
		{
			
			trainerpg.clkLearnersLink();
			Thread.sleep(500);
			trainerpg.displayUploadPg();
			
			String actResult = AutomationConstants.LNR_PG_TXT;
			String expResult = trainerpg.getUploadpgTxt();
			Assert.assertEquals(actResult,expResult);
			
		}
		
		@Test(priority=11)  
		public void choose_csv() throws InterruptedException
		{
			trainerpg.clkLearnersLink();
			Thread.sleep(2000);
			
			trainerpg.displayUploadPg();
			trainerpg.chooseCSV();
			
			String actResult = trainerpg.getCSVHead();
			String expResult = AutomationConstants.LEARNER_TABLE_HEAD;
			System.out.println("Act : " + actResult + " Exp :" +expResult);
			Assert.assertEquals(actResult,expResult);
			
			trainerpg.submitChosenFile();  
			
		}
		
	
		@Test(priority=12)   
		public void edit_lrnr_project() throws InterruptedException, IOException
		{
			trainerpg.clkLearnersLink();
			
			Thread.sleep(2000);
			trainerpg.tableIteration(1,"");
			
			trainerpg.editLnrPrj();
			trainerpg.submitLearnerForm();
			
			String expPrj = ExcelUtility.getData(2, 8);
			
			trainerpg.tableIteration(3, expPrj);
			
			String uptdPrj = trainerpg.tableIteration(3, expPrj);
			
			System.out.println("Updated project  : " +uptdPrj);
			System.out.println("Expected  project : " +expPrj);
			Assert.assertEquals(uptdPrj,expPrj);
			
		 }
		
		
		@Test(priority=13)  
		public void delete_learner() throws InterruptedException, IOException
		{
			trainerpg.clkLearnersLink();
			
			Thread.sleep(1000);
			String strUID = trainerpg.getLrnID();   //LID in table row
			System.out.println("Deleted Learner is : " + strUID);
			
			trainerpg.tableIteration(4,"");
			trainerpg.tableIteration(5,strUID);

			Thread.sleep(2000);
			Assert.assertNotEquals(trainerpg.getLrnID(),strUID);
		}
		
		
		@Test(priority=14)  
		public void check_LnrPrevious_btn() throws InterruptedException
		{
			SoftAssert softAssert = new SoftAssert();
			trainerpg.clkLearnersLink();
			
			String actResult = trainerpg.getLearnerHeading();
			String expResult = AutomationConstants.LEARNER_TABLE_HEAD;
			
			softAssert.assertEquals(actResult, expResult, "First soft assert: Table Head");
		
			trainerpg.displayLearnersForm();
			String actual= trainerpg.getLearnerFormTitle();
			String expected = AutomationConstants.LEARNER_FORM_TITLE;
			
			softAssert.assertEquals(actual, expected, "Second soft assert: Form title");
			
			Thread.sleep(1000);
			trainerpg.previousPage();
			
			String act = trainerpg.getLearnerHeading();
			String exp = AutomationConstants.LEARNER_TABLE_HEAD;
			
			softAssert.assertEquals(act, exp, "Third soft assert: Learner Hdng");
			softAssert.assertAll();
			
		}
	
	
		
		//[[[[[Placement Officer section]]]]
		
		@Test(priority=15) 
		public void show_learners_in_plc() throws InterruptedException
		{
			
			plofficerpg.clkPlacementLink();
			
			String actResult = plofficerpg.getLearnerHeading();
			String expResult = AutomationConstants.LEARNER_TABLE_HEAD;
			
			Assert.assertEquals(actResult,expResult);
					
		}
		
		@Test(priority=16) 
		public void edit_lrnrpstatus_in_plc() throws InterruptedException, IOException //old
		{
			Thread.sleep(1000);
			plofficerpg.clkPlacementLink();
			
			plofficerpg.iterateTable(1,""); 
			plofficerpg.editPlcStatus();
						
			plofficerpg.submitForm();
			String expStat = ExcelUtility.getData(2, 5);
 
			plofficerpg.iterateTable(2, expStat);
			String updatedStat = plofficerpg.iterateTable(2, expStat);
			
			System.out.println("New status  : " +expStat);
			System.out.println("Expected  : " +updatedStat);
			Assert.assertEquals(updatedStat,expStat);
			
		}

		
		@AfterMethod
		public void tearDown()
		{
			this.driver.close();
		} 

}
