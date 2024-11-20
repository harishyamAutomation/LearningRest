package core;

import org.testng.ITestResult;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.LogStatus;

import helper.BaseTestHelper;
import utils.ExtentReport;

public class BaseTest {
	
    private static ThreadLocal<com.relevantcodes.extentreports.ExtentTest> extentLog = new ThreadLocal<>();
	
	@BeforeSuite(alwaysRun = true)
	public void config(@Optional("Optional name Automation ") String reportName, @Optional("API Report") String flow) {
		
		String subFolderPath = System.getProperty("user.dir")+"/reports/"+BaseTestHelper.Timestamp();
		
		BaseTestHelper.createFolder(subFolderPath);
		
		ExtentReport.initialize(subFolderPath+"/"+"RestAPI_Automation.html");
		
	}
	
	@BeforeMethod(alwaysRun = true)
	public static void logBeforeMethod() {
		System.out.println("Test case execution has been started : >>>>>>>>>>>>>>>>>>>>>");
	}
	
	@AfterMethod(alwaysRun = true)
	public void getResult(ITestResult result) {
				
		if(result.getStatus()==ITestResult.SUCCESS) {
			ExtentReport.extentLog.log(LogStatus.PASS, "Test Case : "+result.getName()+" has been <b>PASSED</b>");
		}else if (result.getStatus()==ITestResult.FAILURE) {
			ExtentReport.extentLog.log(LogStatus.FAIL, "Test Case : "+result.getName()+" has been <b>FAILED</b>");
			ExtentReport.extentLog.log(LogStatus.FAIL, "Test Case failed due to : "+result.getThrowable());
			
		}else if (result.getStatus()==ITestResult.SKIP){
			ExtentReport.extentLog.log(LogStatus.SKIP, "Test Case : "+result.getName()+" has been <b>SKIPPED</b>");
		}
		
		ExtentReport.extentReport.endTest(ExtentReport.extentLog);
	}
	
	@AfterSuite(alwaysRun = true)
	public void endReport() {
		ExtentReport.extentReport.flush();
		ExtentReport.extentReport.close();
	}
}
