package userManagement;

import io.restassured.authentication.OAuthSignature;
import io.restassured.response.Response;
import utility.BaseTest;
import utils.ExtentReport;

import org.testng.annotations.Test;

import core.StatusCode;
import helper.BaseTestHelper;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class PostmanEcho extends BaseTest{
	  
    @Test(description = "Automate Basic Authorization", groups = {"RegressionSuite", "SmokeSuite"}, priority=13)
    public void automateBasicAuth() {
    	baseURI = "https://postman-echo.com";
    	
    	//ExtentReport.extentLog = ExtentReport.extentReport.startTest("automateBasicAuth", "Automate Basic Authorization");// first parameter will be the name and second parameter will have the description about the test case
    	
    	Response resp = given().auth().basic("postman", "password").get("/basic-auth");
    	
    	//System.out.println("BasicAuth : \n"+resp.getBody().asString());
    	resp.then().statusCode(StatusCode.SUCCESS.code);
    }
    
    @Test(description = "Automate Digest Authorization", priority=14, groups = "SmokeSuite")
    public void automateDigestAuth() {
    	baseURI = "https://postman-echo.com";
    	
    	Response resp = given().auth().digest("postman", "password").get();
    	
    	System.out.println("DigestAuth : \n"+resp.getBody().asString());
    	
    	resp.then().statusCode(StatusCode.CREATED.code);
    }
    
    @Test(description = "Automate OAuth1.0 Authorization", priority = 15, enabled = true) //Need to deep dive into this
    public void automateOAuth1() {
    	baseURI = "https://postman-echo.com";
    	
    	Response resp = given().auth().oauth("RKCGzna7bv9YD57c", "D+EdQ-gs$-%@2Nu7", "", "", OAuthSignature.HEADER).get();
    	
    	//System.out.println("OAuth1.0 : \n"+resp.getBody().asString());
    	resp.then().statusCode(StatusCode.SUCCESS.code);
    }
    
}
