package org.ict.pages;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.ict.constants.AutomationConstants;
import org.ict.excel.ExcelUtility;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.ict.base.BaseClass;

public class Trainer extends BaseClass
{
	
	public Trainer(WebDriver driver) 
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	
	@FindBy(linkText = "Learners")   
	WebElement learner;

	@FindBy(xpath = "//ion-icon[@name='person-add-outline']/parent::button")
	WebElement  addLnrBtn;
	
	@FindBy(xpath = "//ion-icon[@name='cloud-upload']/parent::button")
	WebElement uploadBtn;
	
	@FindBy(xpath = "//table/thead/tr[1]/th[1]")  //Table heading
	WebElement tblheadingtxt;
	
	@FindBy(tagName = "h3") // Learner's form Title
	WebElement titleLnrForm;
	
	@FindBy(id="basic-nav-dropdown")
	WebElement tnr_uname;
	
	@FindBy(linkText = "Logout")
	WebElement logout;
	
	@FindBy(xpath = "//p[text()='ICTAK - Learner Tracker']")   // For Logout - Homepage Text
	WebElement ictText;
	
	@FindBy(xpath="//button[contains(text(),'Back')]")
	WebElement backBtn;
	
	
	//**** Elements in Upload page ****//
	
	@FindBy(xpath = "//label[contains(text(),'Upload')]")
	WebElement uploadtxt;
	
	@FindBy(xpath = "//button[text()='Submit']")
	WebElement submitFileBtn;
	
	@FindBy(xpath = "//button[text()='Cancel']")
	WebElement cancelFleBtn;
	
	@FindBy(partialLinkText = "sample csv")   //Link-Download sample csv
	WebElement sample;
	
	@FindBy(xpath="//input[@name='file' and @accept='.csv']")
	WebElement choose;
	
	@FindBy(xpath="//th[text()='Learner Id']")  // Table TH frm uploaded csv file
	WebElement listHead;
	
	//**** Elements in Learners Form ****//
	
	@FindBy(id="learnerid")
	WebElement lid;
	
	@FindBy(xpath="//input[@id='name' and @name='name']")
	WebElement name;
	
	@FindBy(xpath="//select[@name='course']")
	WebElement course;
	
	@FindBy(xpath="//select[@name='project']")
	WebElement project;
	
	@FindBy(xpath="//select[@name='batch']")
	WebElement batch;
	
	@FindBy(xpath="//select[@name='cstatus']")
	WebElement cstatus;

	@FindBy(xpath="//button[text()='Submit']")
	WebElement submitBtn;
	
	
	@FindBy(xpath="//p[contains(text(),'Please select a batch for the learner')]")    //Batch field err msg
	WebElement errText;
	
	
	@FindBy(xpath="//button[text()='OK']")    //alert box 'OK' button
	WebElement okBtn;
	
	@FindBy(xpath="//div[@id='swal2-html-container']")    //alert box text
	WebElement alertText;
	
		
	//TABLE Elements//
	
	@FindBy(xpath="//table[@class='table table-bordered table-hover']/tbody/tr[5]")   //Row
	List<WebElement> tblLnrList;
	
	@FindBy(xpath="//table[@class='table table-bordered table-hover']/tbody/tr[5]/td[1]")    //Learner Id
	WebElement lnrID;
		
	
	public void clkLearnersLink()
	{ 
		learner.click();
	}
	
	
	public String getLearnerHeading()
	{
		return tblheadingtxt.getText();
	}
	
	public void fillLearnerDetails(String strLid,String strName,String strCourse,String strPrj,String strBatch,String strCStatus)
	{
		
		lid.sendKeys(strLid);
		name.sendKeys(strName);
		course.sendKeys(strCourse);
		project.sendKeys(strPrj);
		batch.sendKeys(strBatch);
		cstatus.sendKeys(strCStatus);
		
	}
	
	
	//*******To Update/Delete ********//
		public String tableIteration(int intAction ,  String strMatch) {
	       
	     
			String strCrsNameTbl="";
			String strPrjName="";
			String strReturn = "";
			String strLnrId = "";
		  
			//// Get all the rows in the table
	        for (WebElement row : tblLnrList) {
	        	
	        	//// Get all the cells in the row
	             List<WebElement> cells = row.findElements(By.tagName("td"));
	             
	             strLnrId 		= cells.get(0).getText();
	             strCrsNameTbl  = cells.get(2).getText();
	             strPrjName     = cells.get(3).getText();

	              switch(intAction) {
	             case 1:
	            	 
	            	 cells.get(7).click();
	            	 break;
	            	 
	             case 2:
	            	 if ( strCrsNameTbl.compareTo(strMatch)== 0 )  
	            	 {
	            		 strReturn = strMatch;
		            	 
	            	 }
	            	
	                 break;
	                 
	             case 3:
	            	 	 
	            	 if ( strPrjName.compareTo(strMatch)== 0 )  
	            	 {
	            		 strReturn = strPrjName;
	            	 }
	            	 break;
	            	 
	             case 4:
	            	 
	            	 cells.get(8).click();
	            	 System.out.println("case 4 : Delete");
	                 break;
	                 
	             case 5:
	          	 	 
	            	 if ( strLnrId.compareTo(strMatch)!=0) 
	            	 {
	            		 System.out.println("LID is : " +strLnrId + " and " +strMatch );
	            		 System.out.println("Deletion confirmed");
	  
	            		 strReturn = strLnrId;
	            	 }
	            	
	                 break;
	                 
	            default:
	            	    
	           }
	         }
			
	      	return strReturn;

	    }
		
		public void displayLearnersForm() throws InterruptedException
		{
			addLnrBtn.click();
			
		} 
		
		public String getLearnerFormTitle()
		{
			return titleLnrForm.getText();
		}
		
		
		public void submitLearnerForm()
		{
			submitBtn.click();
		}
		
	
	public String getAlertText()
	{
		String strAlertText = alertText.getText();
		okBtn.click();
		return strAlertText;
		
	}
	
	public String getErrText()

	{
		String userErrText = errText.getText();
		return userErrText;
		
	}
	
	//**********File upload*************/
	public void displayUploadPg()
	{
		uploadBtn.click();
	}
	
	public String getUploadpgTxt()
	{
		return uploadtxt.getText();
	}
	
	public void chkSubmitFile()
	{
		submitFileBtn.click();	
	}
	
	public String getUploadAlrtTxt() throws InterruptedException
	{
		
		Thread.sleep(2000);
		Alert alert = driver.switchTo().alert();
		String filealerttxt = alert.getText();
		alert.accept();            
		return filealerttxt;
	}
	
	public void cancelFlSubmit()
	{
		cancelFleBtn.click();	
	}
	
	public void sampleFile()
	{
		sample.click();	
	}
	
	public void log_out() throws InterruptedException
	{
		Thread.sleep(2000);
		
		Actions act = new Actions(driver);
    	//act.moveToElement(plc_uname).build().perform();
		act.moveToElement(tnr_uname).perform();
		
		tnr_uname.click();
		logout.click();
	}
	
	
	public String getIctText()
	{
		return ictText.getText();
	}
		
		
	public void editLnrCourse() throws InterruptedException, IOException 
	{
		
		Thread.sleep(1000);
		Select drpcourse = new Select(course);
		
		String newcourse = ExcelUtility.getData(1, 7);
		drpcourse.selectByVisibleText(newcourse);
		
	}
	
	public void editLnrPrj() throws InterruptedException, IOException
	{
		
		Thread.sleep(1000);
		Select drpProj = new Select(project);
		
		String newProj = ExcelUtility.getData(2, 8);
		drpProj.selectByVisibleText(newProj);
		
	}
		
			
		//****Verify file download****/
		
		public  String checkFile()
		{
		
		String strdndPath = "C:\\Users\\91944\\Downloads"; 
		String strFindFile = AutomationConstants.LNR_SAMP_FILENAME;
		String strFileName;
		
	    boolean isFileDownloaded = isFileDownloaded(strdndPath, strFindFile);

		    if (isFileDownloaded) 
		    {
		        System.out.println("File download verified");
		        strFileName =  strFindFile;    
		    } 
		    else 
		    {
		        System.out.println("Not downloaded");
		        strFileName = null;
		    }
			return strFileName;
	    
		}

		//**** To check if a file exists in a directory.****//
	    private static boolean isFileDownloaded(String strdndPath, String fileName)
	    {
	        File dir = new File(strdndPath);
	        File[] dirContents = dir.listFiles();

	        if (dirContents != null)
	        {
	            for (File file : dirContents)
	            {
	                if (file.getName().equals(fileName))
	                {
	                    return true;
	                }
	            }
	        }
	        return false;
	    
	    }
			    
			    
	    public void chooseCSV() 
	    {
	    	choose.sendKeys(System.getProperty("user.dir")+"/src/main/resources/Learners_list.csv");
	    	
	    }
	    
	    public String getCSVHead() 
	    {
	    	return listHead.getText();
	    }
	    

		public void submitChosenFile()
		{
			submitFileBtn.click();	
		}
	    
		public void previousPage()
		{
			backBtn.click();
		}
					
		public String getLrnID()

		{ 
			return lnrID.getText();
			
		}
					
 }
