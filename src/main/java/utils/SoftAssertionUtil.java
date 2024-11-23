package utils;

import org.testng.asserts.SoftAssert;

public class SoftAssertionUtil {

	private static SoftAssert softAssert;
	private static SoftAssertionUtil softAssertUtil;
	
	private SoftAssertionUtil() {
		//softAssert = new SoftAssert();
	}
	
	public static SoftAssertionUtil getInstance() {
		if(softAssertUtil == null) {
			softAssertUtil = new SoftAssertionUtil();
		}
		return softAssertUtil;
	}
	
	private static SoftAssert getSoftAssert() {
		if(softAssert==null) {
			softAssert = new SoftAssert();
		}
		return softAssert;
	}
	
	public void assertTrue(boolean condition, String message) {
		try {
			SoftAssertionUtil.getSoftAssert().assertTrue(condition, message);
		} catch (AssertionError e) {
			SoftAssertionUtil.getSoftAssert().fail(message);
			// TODO: handle exception
		}
	}
	
	public void assertEquals(Object actual, Object expected, String message) {
		try {
			SoftAssertionUtil.getSoftAssert().assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			SoftAssertionUtil.getSoftAssert().fail(message);
			// TODO: handle exception
		}
	}
	
	public void assertNotEquals(Object actual, Object expected, String message) {
		try {
			SoftAssertionUtil.getSoftAssert().assertNotEquals(actual, expected, message);
		} catch (AssertionError e) {
			SoftAssertionUtil.getSoftAssert().fail(message);
			// TODO: handle exception
		}
	}
	
	public void AssertAll() {
		SoftAssertionUtil.getSoftAssert().assertAll();
	}
	
}
