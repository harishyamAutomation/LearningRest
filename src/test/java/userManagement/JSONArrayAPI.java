package userManagement;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import core.StatusCode;
import io.restassured.response.Response;
import utils.JSONReader;
import utils.SoftAssertionUtil;

public class JSONArrayAPI {
	
	SoftAssertionUtil softAssert = new SoftAssertionUtil();
	
	@Test(description = "Get user data from page 2 and match first record of dataArray", priority=1)
    public void getUserData() throws IOException, ParseException {
		
        Response response = given().
                when().get("https://gorest.co.in/public/v2/users/7517363");
       
       String testData = FileUtils.readFileToString(new File("resources/TestData/UserArray.json"));
       
       
       softAssert.assertEquals(response.statusCode(), StatusCode.SUCCESS.code, "Something Went Wrong!!");
       
       System.out.println("Actual ID from response : "+JSONReader.getTestData(response.getBody().asString(), "name"));
       System.out.println("Expected ID from response : "+JSONReader.getTestData(testData, "name"));
       
       
    }
}
