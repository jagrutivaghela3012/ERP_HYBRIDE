package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunction.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {

	WebDriver driver;
	String inputPath="./FileInput/Controller.xlsx";
	String outputPath="./FileOutput/ERPResult.xlsx";
	String Testcases="MasterTestCases";
	ExtentReports report;
	ExtentTest test;
	public void startTest() throws Throwable 
	{
		ExcelFileUtil xl=new ExcelFileUtil(inputPath);
		//itreate all rows from testcases sheet
		for(int i=1;i<=xl.rowCount(Testcases);i++)
		{
			if(xl.getCellData(Testcases, i, 2).equalsIgnoreCase("Y"))
			{
				String ModuleStatus="";
				//iterate respective sheet
				String TCModule=xl.getCellData(Testcases, i,1);
				report=new ExtentReports("./Reports/"+TCModule+"   "+FunctionLibrary.generateDate()+".html");
				test=report.startTest(TCModule);
				//iterate all row from Tcmodule sheet
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					//read cell from Tcmodule
					String Description=xl.getCellData(TCModule, j, 0);
					String Function_Name=xl.getCellData(TCModule, j, 1);
					String Locator_Type=xl.getCellData(TCModule, j, 2);
					String Locator_Value=xl.getCellData(TCModule, j, 3);
					String TestData=xl.getCellData(TCModule, j, 4);
					try
					{
						if(Function_Name.equalsIgnoreCase("startBrowser"))
						{
							driver=FunctionLibrary.startBrowser();
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, TestData);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, TestData);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(driver, TestData);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("selectDropDown"))
						{
							FunctionLibrary.selectDropDown(driver, Locator_Type, Locator_Value, TestData);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("captureStockItem"))
						{
							FunctionLibrary.captureStockItem(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("mouseclick"))
						{
							FunctionLibrary.mouseclick(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("categoryTable"))
						{
							FunctionLibrary.categoryTable(driver, TestData);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("captureSupplierNumber"))
						{
							FunctionLibrary.captureSupplierNumber(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("captureCustomerNo"))
						{
							FunctionLibrary.captureCustomerNo(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("customerTable"))
						{
							FunctionLibrary.customerTable(driver);
							test.log(LogStatus.INFO, Description);
						}
						xl.setCellData(TCModule, j, 5,"Pass",outputPath);
						test.log(LogStatus.PASS, Description);
						ModuleStatus="true";
						
					}catch (Exception e) {
						System.out.println(e.getMessage());
						xl.setCellData(TCModule, j, 5, "Fail", outputPath);
						test.log(LogStatus.FAIL, Description);
						ModuleStatus="False";
						
					}
					if(ModuleStatus.equalsIgnoreCase("True"))
					{
						xl.setCellData(Testcases, i, 3,"Pass", outputPath);
					}
					if(ModuleStatus.equalsIgnoreCase("False"))
					{
						xl.setCellData(Testcases, i, 3, "Fail", outputPath);
					}
					report.endTest(test);
					report.flush();	
					
				}
				
			}
			else
			{
				xl.setCellData(Testcases, i, 3, "Blocked",outputPath);
			}
		}
	}
	
}