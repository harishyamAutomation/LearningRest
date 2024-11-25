package userManagement;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

import core.StatusCode;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import userSpecification.UserRequestSpecification;
import utility.BaseTest;
import utils.ExtentReport;

public class UsersSchemaValidation extends BaseTest {
	
	@Test(priority=1, description="Validate Schema for the user")
	public void getAndValidateUserSchema() throws IOException {
		String uri = "http://localhost:3000/";
		String contentType = "application/json";
		String userid = "b390";
		
		//ExtentReport.extentLog = ExtentReport.extentReport.startTest("getAndValidateUserSchema", "Validate Schema for the user");
		
		File schema = new File("resources/userSchema.json");
		System.out.println(schema.exists()?"Expected Schema found":"Sxpected Schema File not found");
		
		Response resp = given()
						.spec(UserRequestSpecification.getInstance().getRequestSpecification(uri, contentType))
						.when()
						.get("/users/"+userid);
		
		
		resp.then().assertThat().statusCode(StatusCode.SUCCESS.code)
								.body(JsonSchemaValidator.matchesJsonSchema(schema));
		
		System.out.println("****************** getAndValidateUserSchema : Extract the schema for User Executed Successfully *******************");
		
	}
}
