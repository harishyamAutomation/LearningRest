package userManagement;

import org.testng.annotations.Test;

import core.StatusCode;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;

public class FileUploadDownload {
	
	@Test
	public void fileUpload() {
		
		File file = new File("resources/TestData/testData.json");
		
		Response resp = given().multiPart(file).when().post("http://postman-echo.com/post");
		
		resp.then().statusCode(StatusCode.SUCCESS.code);
	}
}
