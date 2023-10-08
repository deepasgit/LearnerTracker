package com.ict.testcases;

import java.io.IOException;
import java.time.Duration;

import org.ict.constants.AutomationConstants;
import org.ict.excel.ExcelUtility;
import org.ict.pages.Login;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ict.base.BaseClass;

public class LoginTC extends BaseClass{
	  
		Login loginpg;
	
		@BeforeMethod
		public void tearUp()
		{
			loginpg = new Login(driver);
			
		}
		
		@Test(priority=3)
		public void verify_admin_login() throws IOException
		{
			String strUserName = ExcelUtility.getData(1, 0);
			String strPassword = ExcelUtility.getData(1, 1);
			loginpg.validLogin(strUserName,strPassword);
			
			String actTitle = loginpg.resultText();
			String expTitle = ExcelUtility.getData(1, 6);
			
			Assert.assertEquals(actTitle,expTitle);
		
		}
		
		@Test(priority=4)
		public void verify_trainer_login() throws IOException 
		{
		    String strUserName = ExcelUtility.getData(2, 0);
			String strPassword = ExcelUtility.getData(2, 1);
			System.out.println(strUserName + " " + strPassword );
			loginpg.validLogin(strUserName,strPassword);
			
			String actTitle = ExcelUtility.getData(2, 6);
			String expTitle = loginpg.resultText();
			
			Assert.assertEquals(actTitle,expTitle);
			
		
		}
		
		@Test(priority=5)
		public void verify_officer_login() throws IOException 
		{
		    String strUserName = ExcelUtility.getData(3, 0);
			String strPassword = ExcelUtility.getData(3, 1);
			System.out.println(strUserName + " " + strPassword );
			loginpg.validLogin(strUserName,strPassword);
			
			String actTitle = ExcelUtility.getData(3, 6);
			String expTitle = loginpg.resultText();
			
			Assert.assertEquals(actTitle,expTitle);
		
		}
	
		
		
		@Test(priority=1)
		public void check_invalid_login() throws IOException
		{
			
			String strInvUser = ExcelUtility.getData(4, 0);   
			String strInvPassword = ExcelUtility.getData(4, 1);  
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			loginpg.invalidLogin(strInvUser,strInvPassword);
			
			System.out.println(strInvUser +","+ strInvPassword);
			String expTitle = loginpg.getErrMsg();
					
			Assert.assertEquals(expTitle,AutomationConstants.LGN_USER_MSG);
			
		}
		
		@Test(priority=2)
		public void check_invalid_loginPwd() throws IOException
		{
			
			String strInvUser = ExcelUtility.getData(5, 0);  
			String strInvPassword = ExcelUtility.getData(5, 1);  
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			loginpg.invalidLogin(strInvUser,strInvPassword);
			
			System.out.println(strInvUser +","+ strInvPassword);
			String expTitle = loginpg.getErrMsg(); 
					
			Assert.assertEquals(expTitle,AutomationConstants.LGN_USER_ERR);
		}
		
		

		@AfterMethod
		public void tearDown()
		{
			this.driver.close();
		} 

}
