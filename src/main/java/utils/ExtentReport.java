package utils;

import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;

public class ExtentReport {
	private static ExtentReport instance = null;
	private static ExtentReports extentReport;
	private static ThreadLocal<ExtentTest> extentLog = new ThreadLocal<>();
	
	
	public static ExtentReport getInstance() {
		if(instance==null) {
			instance = new ExtentReport();
		}
		
		return instance;
	}
	
	public synchronized void initialize(String extentReportFilePath) {
		
		System.out.println(">>>>>>>>>>>>Under initialize");
		
		if(extentReport == null) {
			
			extentReport = new ExtentReports();
			
			System.out.println(">>>>>>>>>>>>Under initialize if block");
			
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFilePath);
			
			System.out.println(">>>>>>>>>>>>Under initialize after sparker");
			
			try {
				sparkReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"/"+"resources/extent-config.xml"));
				
				System.out.println(">>>>>>>>>>>>Under initialize xml config loaded");
				
				extentReport.attachReporter(sparkReporter);
				
				System.out.println(">>>>>>>>>>>>Under initialize sparker attached");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			extentReport.setSystemInfo("Host Name", System.getProperty("user.name"));
			extentReport.setSystemInfo("Environment", "QA");
			extentReport.setSystemInfo("OS", "Windows 11 Intel core i3 12th Gen");
			
			System.out.println(">>>>>>>>>>>>Under initialize end of initialize");
			//extentReport.loadConfig(new File(System.getProperty("user.dir")+"/"+"resources/extent-config.xml"));
		}
		
	}
	
	public static void startTest(String testName, String testDescription) {
		
		System.out.println(">>>>>>>>>>>>Under startTest");
		
		if (extentReport != null) {
            ExtentTest test = extentReport.createTest(testName, testDescription);
            extentLog.set(test); // Set ThreadLocal
        } else {
            throw new IllegalStateException("ExtentReports is not initialized. Call initialize() first.");
        }
		
		System.out.println(">>>>>>>>>>>>Under startTest Line -2 ");
		//extentLog.set(test);
		
		System.out.println("startTed > End Line");
	}
	
	public static ExtentTest getTest() {
		ExtentTest test = extentLog.get();
        if (test == null) {
            throw new IllegalStateException("No test started. Call startTest() before logging.");
        }
        return test;
	}
	
	public void flush() {
		if(extentReport != null) {
			extentReport.flush();
		}
	}
}
