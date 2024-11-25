package userManagement;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import core.StatusCode;
import io.restassured.response.Response;
import utility.BaseTest;
import utils.ExternalJSONRequestUtil;

public class RequestBodyExternalJSON extends BaseTest {
	@Test(description = "Request Body as External JSON")
    public void requestBodyAsExternalJSON() throws FileNotFoundException, IOException {
        baseURI = "https://reqres.in/api";
        
        //ExtentReport.extentLog = ExtentReport.extentReport.startTest("requestBodyAsExternalJSON", "Request Body as External JSON");// first parameter will be the name and second parameter will have the description about the test case

        Response res = given()
                .when().body(IOUtils.toString(new FileInputStream(new File("resources/TestData/RequestBody.json"))))
                .post("/users");
        res.then().statusCode(StatusCode.CREATED.code);
        
    }
	
	@Test(description = "Request Body as External JSON using Utility class")
    public void requestBodyAsExternalJSONUtility() throws FileNotFoundException, IOException {
        baseURI = "https://reqres.in/api";
        
        //ExtentReport.extentLog = ExtentReport.extentReport.startTest("requestBodyAsExternalJSONUtility", "Request Body as External JSON using Utility class");// first parameter will be the name and second parameter will have the description about the test case

        Response res = given()
                .when().body(ExternalJSONRequestUtil.getRequestBody(new File("resources/TestData/RequestBody.json")))
                .post("/users");
        res.then().statusCode(StatusCode.CREATED.code);
        
    }
}
