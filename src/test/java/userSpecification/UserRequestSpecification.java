package userSpecification;

import io.restassured.authentication.OAuthSignature;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ExtentReport;
import utils.SoftAssertionUtil;

import org.testng.annotations.Test;

import core.StatusCode;
import helper.BaseTestHelper;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class UserRequestSpecification {
	
	//Singleton Design Pattern
	private static UserRequestSpecification userRequestSpecification; 
	public UserRequestSpecification() {}
	public static UserRequestSpecification getInstance() {
		if(userRequestSpecification == null) {
			userRequestSpecification = new UserRequestSpecification();
		}
		return userRequestSpecification;
	}
	
	
	//Builder Design Pattern
	private RequestSpecification requestSpecification = null;
	
	public RequestSpecification getRequestSpecification(String uri, String contentType) {
		requestSpecification = new RequestSpecBuilder()
				.setBaseUri(uri)
				.setContentType(contentType)
				.build();
		
		return requestSpecification;
	}
	
}
