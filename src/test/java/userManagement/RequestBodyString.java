package userManagement;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

import org.testng.annotations.Test;

import core.StatusCode;
import io.restassured.response.Response;

public class RequestBodyString {
	
	 @Test(description = "Request Body as String")
	    public void requestBodyAsString() {
	        baseURI = "https://reqres.in/api";

	        Response res = given()
	                .when().body("\"{\\\"name\\\":\\\"HarishyamSharma\\\",\\\"job\\\":\\\"QAEngineer\\\"}\"")
	                .post("/users");
	        res.then().statusCode(StatusCode.CREATED.code);
	        
	    } 
	
}
