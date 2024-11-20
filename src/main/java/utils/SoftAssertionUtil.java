package utils;

import org.testng.asserts.SoftAssert;

public class SoftAssertionUtil {

	private SoftAssert softAssert;
	private SoftAssertionUtil softAssertUtil;
	
	public SoftAssertionUtil() {
		softAssert = new SoftAssert();
	}
	
	public SoftAssert getInstance() {
		if(softAssert == null) {
			softAssert = new SoftAssert();
		}
		return softAssert;
	}
	
	public void assertTrue(boolean condition, String message) {
		try {
			softAssert.assertTrue(condition, message);
		} catch (AssertionError e) {
			softAssert.fail(message);
			// TODO: handle exception
		}
	}
	
	public void assertEquals(Object actual, Object expected, String message) {
		try {
			softAssert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			softAssert.fail(message);
			// TODO: handle exception
		}
	}
	
	public void assertNotEquals(Object actual, Object expected, String message) {
		try {
			softAssert.assertNotEquals(actual, expected, message);
		} catch (AssertionError e) {
			softAssert.fail(message);
			// TODO: handle exception
		}
	}
	
	public void AssertAll() {
		softAssert.assertAll();
	}
	
}
