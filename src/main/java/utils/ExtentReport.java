package utils;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentReport {
	public static ExtentReports extentReport;
	public static ExtentTest extentLog;	
	
	
	public static void initialize(String extentReportFilePath) {
		
		if(extentReport == null) {
			extentReport = new ExtentReports(extentReportFilePath, true);
			
			extentReport.addSystemInfo("Host Name", System.getProperty("user.name"));
			extentReport.addSystemInfo("Environment", "QA");
			extentReport.addSystemInfo("OS", "Windows 11 Intel core i3 12th Gen");
			extentReport.loadConfig(new File(System.getProperty("user.dir")+"/"+"resources/extent-config.xml"));
		}
		
	}
}
