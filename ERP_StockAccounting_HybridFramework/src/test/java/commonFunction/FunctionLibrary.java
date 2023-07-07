package commonFunction;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties conpro;
	public static String Expecteddata = "";
	public static String Actualdata = "";
	//method for launch browser 
	public static WebDriver startBrowser() throws Throwable 
	{
		conpro=new Properties();
		conpro.load(new FileInputStream("./PropertyFile/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver=new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver=new FirefoxDriver();
		}
		return driver;
	}
	//method for launch url
	public static void openUrl(WebDriver driver)
	{
		driver.get(conpro.getProperty("Url"));
	}
	//method for wait for element
	public static void waitForElement(WebDriver driver,String LocaterType,String LocaterValue,String Wait)
	{
		WebDriverWait Mywait= new WebDriverWait(driver, Integer.parseInt(Wait));
		if(LocaterType.equalsIgnoreCase("id"))
		{
			Mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocaterValue)));
		}
		else if(LocaterType.equalsIgnoreCase("name"))
		{
			Mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocaterValue)));
		}
		else if(LocaterType.equalsIgnoreCase("xpath"))
		{
			Mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocaterValue)));
		}
	}
	//method for Texboxes
	public static void typeAction(WebDriver driver,String LocaterType,String LocaterValue,String Testdata)
	{
		if(LocaterType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocaterValue)).clear();
			driver.findElement(By.id(LocaterValue)).sendKeys(Testdata);
		}
		else if(LocaterType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocaterValue)).clear();
			driver.findElement(By.name(LocaterValue)).sendKeys(Testdata);
		}
		else if(LocaterType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocaterValue)).clear();
			driver.findElement(By.xpath(LocaterValue)).sendKeys(Testdata);
		}
	}
	//method for Click any link , button, image, checkbox, radiobutton
	public static void clickAction(WebDriver driver,String LocaterType,String LocaterValue)
	{
		if(LocaterType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocaterValue)).sendKeys(Keys.ENTER);
		}
		else if(LocaterType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocaterValue)).click();
		}
		else if(LocaterType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocaterValue)).click();
		}
	}
	//method for validate Title
	public static void validateTitle(WebDriver driver,String ExpectedTitle)
	{
		String ActualTitle=driver.getTitle();
		try {
			Assert.assertEquals(ActualTitle, ExpectedTitle,"Title is not Matching");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}
	//method for close browser
	public static void closeBrowser(WebDriver driver)
	{
		driver.quit();
	}
	//method for listboxes
	public static void selectDropDown(WebDriver driver,String LocaterType,String LocaterValue,String TestData)
	{
		if(LocaterType.equalsIgnoreCase("xpath"))
		{
			int value=Integer.parseInt(TestData);
			WebElement element=driver.findElement(By.xpath(LocaterValue));
			Select select=new Select(element);
			select.selectByIndex(value);
		}
		else if(LocaterType.equalsIgnoreCase("id"))
		{
			int value=Integer.parseInt(TestData);
			WebElement element=driver.findElement(By.id(LocaterValue));
			Select select=new Select(element);
			select.selectByIndex(value);
		}
	}
	//method for capture stock item
	public static void captureStockItem(WebDriver driver,String LocaterType,String LocaterValue)
	{
		Expecteddata=driver.findElement(By.xpath(LocaterValue)).getAttribute("value");
	}
	//method for check Stock table
	public static void stockTable(WebDriver driver) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//if search textbox is not displayed then click on search panel or else not click
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expecteddata);
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Actualdata=driver.findElement(By.xpath("//table[@id='tbl_a_stock_itemslist']/tbody/tr[1]/td[8]/div/span/span")).getText();
		System.out.println(Expecteddata+"          "+Actualdata);
		Assert.assertEquals(Expecteddata,Actualdata,"Stock Number is not Matching");
	}
	//method for mouse click
	public static void mouseclick(WebDriver driver) throws Throwable
	{
		Actions ac=new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Items')])[2]"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Categories')])[2]"))).click().perform();
	}
	//method for category Table
	public static void categoryTable(WebDriver driver,String ExpectedData) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//if search textbox is not displayed then click on search panel or else not click
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(ExpectedData);
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String ActualData =driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr[1]/td[4]/div/span/span")).getText();
		System.out.println(ExpectedData+"     "+ActualData);
		Assert.assertEquals(ExpectedData, ActualData,"Stock category Name not Matching");
	}
	//method for capture supplier number
	public static void captureSupplierNumber(WebDriver driver,String LocatorType,String LocatorValue)
	{
		Expecteddata=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}
	//method for supplier Table
	public static void supplierTable(WebDriver driver) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//if search textbox is not displayed then click on search panel or else not click
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expecteddata);
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Actualdata=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
		System.out.println(Expecteddata+"      "+Actualdata);
		Assert.assertEquals(Expecteddata, Actualdata,"Supplier Number Not Matching");
	}
	public static String generateDate()
	{
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("dd_MM_YYYY"+"hh_mm_ss");
		return df.format(date);
	}
	public static void captureCustomerNo(WebDriver driver,String Locatortype,String LocatorValue)
	{
		Expecteddata=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}
	public static void customerTable(WebDriver driver) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//if search textbox is not displayed then click on search panel or else not click
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expecteddata);
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Actualdata=driver.findElement(By.xpath("//table[@id='tbl_a_customerslist']/tbody/tr[1]/td[5]/div/span/span")).getText();
		System.out.println(Expecteddata+"    "+Actualdata);
		Assert.assertEquals(Expecteddata, Actualdata,"Customer Number Not Matching");
		
	}
}

