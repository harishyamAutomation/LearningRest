package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import core.StatusCode;

public class FailRetry implements IRetryAnalyzer{

	private int retryCount = 0;
	private static final int MAX_RETRY_COUNT = 2;
	
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("Under FailRetry -> retry");
		if(retryCount<MAX_RETRY_COUNT) {
			retryCount++;
			
            System.out.println("Retrying test " + result.getName() + " (attempt " + retryCount + ")");
			
			return true;
		}
		System.out.println("after FailRetry -> retry");
		return false;
	}

}
