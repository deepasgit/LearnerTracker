package com.ict.base;

import java.time.Duration;
import java.util.Properties;
import java.io.*;
import java.nio.charset.Charset;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class BaseClass {

	public WebDriver driver;
	public static Properties properties;
	
	@BeforeMethod
	public void initialization() 
	{
		String browser=properties.getProperty("browser");
		if(browser.equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("edge"))
		{
			driver = new EdgeDriver(); 
		}
		
		driver.get(properties.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		
	}
	
	
	public BaseClass()
	{
	properties = new Properties();
	File file = new File(System.getProperty("user.dir")+"/src/main/java/com/ict/config/Config.properties");
	
	
	
	try {
		FileInputStream inputStream = new FileInputStream(file);
		properties.load(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
		} 
		catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		catch (IOException ioe)
			{
				ioe.printStackTrace();
			}

	}

	

@AfterSuite
	public void TearDown()
	{
		 //driver.close();
	}
	

	
	
}
