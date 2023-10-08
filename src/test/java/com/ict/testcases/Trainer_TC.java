package com.ict.testcases;

import static org.testng.Assert.assertNotEquals;

import java.io.IOException;
import java.time.Duration;

import org.ict.constants.AutomationConstants;
import org.ict.excel.ExcelUtility;
import org.ict.pages.Admin_Dashboard;
import org.ict.pages.Login;
import org.ict.pages.Trainer;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ict.base.BaseClass;

public class Trainer_TC extends BaseClass{
	  
		Login loginpg;
		Trainer trainerpg;
		
	
		@BeforeMethod
		public void tearUp() throws IOException
		{
			
			loginpg = new Login(driver);
			trainerpg = new Trainer(driver);
		
			String strUserName = ExcelUtility.getData(2, 0);
			String strPassword = ExcelUtility.getData(2, 1);
			loginpg.validLogin(strUserName,strPassword);
			
		}
		
		
		@Test(priority=1)
		public void show_learners() throws IOException, InterruptedException
		{
			
			SoftAssert softAssert = new SoftAssert();
			trainerpg.clkLearnersLink();
			
			String actResult = trainerpg.getLearnerHeading();
			String expResult = AutomationConstants.LEARNER_TABLE_HEAD;
			
			softAssert.assertEquals(actResult, expResult, "First soft assert");
		
			trainerpg.displayLearnersForm();
			String actual= trainerpg.getLearnerFormTitle();
			String expected = AutomationConstants.LEARNER_FORM_TITLE;
			
			softAssert.assertEquals(actual, expected, "Second soft assert");
			softAssert.assertAll();
			
		}
		
	
		@Test(priority=2)
		public void add_new_learner() throws IOException, InterruptedException
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
		
		
		@Test(priority=3)
		public void add_new_learnr() throws IOException, InterruptedException
		{
			
			trainerpg.clkLearnersLink();
			trainerpg.displayLearnersForm();
			
			String strLid 	  = ExcelUtility.getData(1, 4);
			String strName 	  = ExcelUtility.getData(2, 4);
			String strCourse  = ExcelUtility.getData(3, 4);
			String strPrj 	  = ExcelUtility.getData(4, 4);
			String strBatch   = "";
			String strCStatus = ExcelUtility.getData(6, 4);
			
			trainerpg.fillLearnerDetails(strLid,strName,strCourse,strPrj,strBatch,strCStatus);
			trainerpg.submitLearnerForm();
			
			String expResult = trainerpg.getErrText();
			String actResult = AutomationConstants.LNR_BATCH_ERR;
			
			Assert.assertEquals(actResult,expResult);
			
		}
		
		
		@Test(priority=4)  
		public void go_to_upload_page() throws InterruptedException
		{
			Thread.sleep(500);
			trainerpg.displayUploadPg();
			
			String actResult = AutomationConstants.LNR_PG_TXT;
			String expResult = trainerpg.getUploadpgTxt();
			Assert.assertEquals(actResult,expResult);
			
		}
		
		@Test(priority=5)  
		public void choose_csv_file() throws InterruptedException
		{
			Thread.sleep(2000);
			
			trainerpg.displayUploadPg();
			trainerpg.chooseCSV();
			
			String actResult = trainerpg.getCSVHead();
			String expResult = AutomationConstants.LEARNER_TABLE_HEAD;
			System.out.println("Act : " + actResult + " Exp :" +expResult);
			Assert.assertEquals(actResult,expResult);
			
			trainerpg.submitChosenFile();  
			
		}
			
	
		@Test(priority=6)  
		public void check_blank_upload() throws InterruptedException
		{
			Thread.sleep(200);
			trainerpg.displayUploadPg();
			trainerpg.chkSubmitFile();
			
			String actResult = AutomationConstants.LNR_UPLDALRT_MSG;
			String expResult = trainerpg.getUploadAlrtTxt();
			//System.out.println(expResult);
			Assert.assertEquals(actResult,expResult);
			
		}
		
		
		@Test(priority=7)  
		public void cancel_file_upld() throws InterruptedException
		{
			Thread.sleep(200);
			trainerpg.displayUploadPg();
			
			Thread.sleep(1000);
			trainerpg.cancelFlSubmit();
			
			String actResult = trainerpg.getLearnerHeading();
			String expResult = AutomationConstants.LEARNER_TABLE_HEAD;
			
			Assert.assertEquals(actResult,expResult);
			
		}
		
		@Test(priority=8)  
		public void dwnload_csv() throws InterruptedException
		{
			Thread.sleep(2000);
			
			trainerpg.displayUploadPg();
			//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			trainerpg.sampleFile();
			
			String actResult = trainerpg.checkFile();
			String expResult = AutomationConstants.LNR_SAMP_FILENAME;
			
			System.out.println("Act : " + actResult + " Exp :" +expResult);
			Assert.assertEquals(actResult,expResult);
				
		}
	
		
		@Test(priority=9)   
		public void edit_lrnr_crs() throws InterruptedException, IOException
		{
			Thread.sleep(2000);
			trainerpg.tableIteration(1,"");
			
			trainerpg.editLnrCourse();
			trainerpg.submitLearnerForm();
			
			String expCourse = ExcelUtility.getData(1, 7);
			
			Thread.sleep(2000);
			trainerpg.tableIteration(2, expCourse);
			
			String uptdCourse = trainerpg.tableIteration(2, expCourse);
			
			System.out.println("Updated course  : " +expCourse);
			System.out.println("Expected  : " +uptdCourse);
			Assert.assertEquals(uptdCourse,expCourse);
			
		 }

		
		@Test(priority=10)   
		public void edit_lrnr_proj() throws InterruptedException, IOException
		{
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
  	
			
		@Test(priority=11)  
		public void delete_learner() throws InterruptedException, IOException
		{
			Thread.sleep(1000);
			String strUID = trainerpg.getLrnID();   //LID in table row
			System.out.println("Deleted Learner is : " + strUID);
			
			trainerpg.tableIteration(4,"");
			trainerpg.tableIteration(5,strUID);

			Thread.sleep(2000);
			Assert.assertNotEquals(trainerpg.getLrnID(),strUID);
		}
		

		@Test(priority=12)  
		public void chk_previous_nav() throws InterruptedException
		{
			
			SoftAssert softAssert = new SoftAssert();
			
			trainerpg.displayLearnersForm();
			String actual= trainerpg.getLearnerFormTitle();
			String expected = AutomationConstants.LEARNER_FORM_TITLE;
			
			softAssert.assertEquals(actual, expected, "First soft assert: Previouspg");
			
			trainerpg.previousPage();
			
			String actResult = trainerpg.getLearnerHeading();
			String expResult = AutomationConstants.LEARNER_TABLE_HEAD;
			
			softAssert.assertEquals(actResult, expResult, "Second soft assert: Previouspg");
			softAssert.assertAll();
			
		}
		

		@Test(priority=13) 
		public void logout() throws InterruptedException
		{
			
			trainerpg.log_out();
			
			String actual	= trainerpg.getIctText();
			String expected = AutomationConstants.HME_ICT_TXT;
			System.out.println("ICT Text : " +actual);
			Assert.assertEquals(actual, expected);
				
		}
	
		@AfterMethod
		public void tearDown()
		{
			this.driver.close();
		} 

}
