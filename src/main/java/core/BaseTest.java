package core;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import helper.BaseTestHelper;
import utils.ExtentReport;

public class BaseTest {
	
	private static ThreadLocal<ExtentTest> extentLog = null;
	
	public static ThreadLocal<ExtentTest> getTest(){
		if(extentLog == null) {
			extentLog = new ThreadLocal<>();
		}
		return extentLog;
	}
	
	
    @BeforeSuite(alwaysRun = true)
	public static void config(@Optional("Optional name Automation ") String reportName, @Optional("API Report") String flow) {
		
		String subFolderPath = System.getProperty("user.dir")+"/reports/"+BaseTestHelper.currentDate();
		
		BaseTestHelper.createFolder(subFolderPath);
		
		ExtentReport.getInstance().initialize(subFolderPath+"/"+BaseTestHelper.Timestamp()+"_RestAPI_Automation.html");
		
	}
	
	@BeforeMethod(alwaysRun = true)
	public static void logBeforeMethod(Method method) {
		
	    //ExtentTest test = ExtentReport.getInstance().createTest(method.getName());
		//    extentLog.set(test); // Assign thread-specific instance
		
		ExtentReport.startTest(method.getName(), "Description for Method"+method.getName());
//		
		System.out.println("Test case execution has been started : >>>>>>>>>>>>>>>>>>>>>");
	}
	
	@AfterMethod(alwaysRun = true)
	public void getResult(ITestResult result) {
				
		if(result.getStatus()==ITestResult.SUCCESS) {
			//ExtentReport.getTest().log(LogStatus.PASS, "Test Case : "+result.getName()+" has been <b>PASSED</b>");
			ExtentReport.getTest().generateLog(Status.PASS, "Test Case : "+result.getName()+" has been <b>PASSED</b>");
		}else if (result.getStatus()==ITestResult.FAILURE) {
			ExtentReport.getTest().generateLog(Status.FAIL, "Test Case : "+result.getName()+" has been <b>FAILED</b>");
			ExtentReport.getTest().generateLog(Status.FAIL, "Test Case failed due to : "+result.getThrowable());
			
		}else if (result.getStatus()==ITestResult.SKIP){
			ExtentReport.getTest().generateLog(Status.SKIP, "Test Case : "+result.getName()+" has been <b>SKIPPED</b>");
		}
		
		//ExtentReport.getTest().endTest(ExtentReport.getTest());
	}
	
	@AfterSuite(alwaysRun = true)
	public void endReport() {
		ExtentReport.getInstance().flush();
	}
}
