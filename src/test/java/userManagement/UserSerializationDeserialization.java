package userManagement;

import org.testng.annotations.*;

import core.BaseTest;
import core.StatusCode;
import io.restassured.response.Response;
import pojo.CityList;
import pojo.UserMainPOJO;
import utils.ExtentReport;
import utils.SoftAssertionUtil;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

/*
To Test this Test Suit/Script Please follow below instructions : 
1. Navigate to the location where users.json file is located >>> D:\\Learning\\API Testing\\users.json
2. Open CMD in that location
3. run command json-server --watch users.json
4. JSON Server will be started
5. Copy the URL Address to use, where-ever needed.
*/

public class UserSerializationDeserialization extends BaseTest {
	
	SoftAssertionUtil softAssert = new SoftAssertionUtil();
	private static String userid;
	
	String name = "Harishyam";
	String job = "SDET";
	
	CityList noida = new CityList();
	CityList delhi = new CityList();
	CityList lucknow = new CityList();
	
	public UserMainPOJO user() {
		noida.setName("Noida");
		noida.setTemperature("30");
		
		delhi.setName("Delhi");
		delhi.setTemperature("40");
		
		lucknow.setName("Lucknow");
		lucknow.setTemperature("25");
		List<CityList> cityList = new ArrayList<>();
		cityList.add(noida);
		cityList.add(delhi);
		cityList.add(lucknow);
		
		UserMainPOJO createUser = new UserMainPOJO();
		createUser.setName(name);
		createUser.setJob(job);
		createUser.setCity(cityList);
		
		return createUser;
	}
	
	@Test(priority=1, description = "Create a New User", groups = "UserChaining")
	public void createNewUser() {
		
		baseURI = "http://localhost:3000/";
		
//		String name = "Harishyam";
//		String job = "SDET";
//		
//		CityList noida = new CityList();
//		CityList delhi = new CityList();
//		CityList lucknow = new CityList();
		
//		noida.setName("Noida");
//		noida.setTemperature("30");
//		
//		delhi.setName("Delhi");
//		delhi.setTemperature("40");
//		
//		lucknow.setName("Lucknow");
//		lucknow.setTemperature("25");
//		List<CityList> cityList = new ArrayList<>();
//		cityList.add(noida);
//		cityList.add(delhi);
//		cityList.add(lucknow);
//		
//		UserMainPOJO createUser = new UserMainPOJO();
//		createUser.setName(name);
//		createUser.setJob(job);
//		createUser.setCity(cityList);
		
		//ExtentReport.extentLog = ExtentReport.extentReport.startTest("createNewUser", "Create a New User");// first parameter will be the name and second parameter will have the description about the test case
		
		Response resp = given()
							.body(user())
						.when()
							.post("/users");
		
		resp.then().assertThat().statusCode(StatusCode.CREATED.code);
		
		UserMainPOJO userMainResponse = resp.as(UserMainPOJO.class);
		
		softAssert.assertEquals(userMainResponse.getName(), name, "Name is not as expected");
		softAssert.assertEquals(userMainResponse.getJob(), job, "Job is not as expected");
		softAssert.assertEquals(userMainResponse.getCity().get(0).getName(), noida.getName(), "Name of the First City is not as expected");
		softAssert.assertEquals(userMainResponse.getCity().get(0).getTemperature(), noida.getTemperature(), "Temperature of the First City is not as expected");
		softAssert.AssertAll();
		
		userid = userMainResponse.getId();
	
		System.out.println("****************** createNewUser : User created with User ID : "+userid+" Created Successfully *******************");		
	}
	
	@Test(priority=2, description="Fetch the created user's data", groups = "UserChaining")
	public void getSingleUser() {
		baseURI = "http://localhost:3000/";
		
		//ExtentReport.extentLog = ExtentReport.extentReport.startTest("getSingleUser", "Fetch the created user's data");
		
		Response resp = given()
						.when()
						.get("/users/"+userid);
		
		resp.then().assertThat().statusCode(StatusCode.SUCCESS.code);
		System.out.println("****************** getSingleUser : Extract the data for UserID : "+userid+" Executed Successfully *******************");
		System.out.println(resp.body().asString());
		
	}
	
	@Test(priority = 3, description = "Updated the Job for the created user", groups = "UserChaining")
	public void updateJobUsingCompletePayload() {
		baseURI = "http://localhost:3000/";
		
		job = "Lead SDET";
		
		//ExtentReport.extentLog = ExtentReport.extentReport.startTest("updateJobUsingCompletePayload", "Updated the Job for the created user");
		
		Response resp = given()
						.body(user())
						.when().put("/users/"+userid);
		
		resp.then().assertThat().statusCode(StatusCode.SUCCESS.code);
		System.out.println("****************** updateJobUsingCompletePayload : Job updated for UserID : "+userid+" Executed Successfully *******************");
		System.out.println(resp.body().asString());
	}
	
	@Test(priority=4, description = "Delete the created user's record", groups = "UserChaining")
	public void deleteSingleUser() {
		baseURI = "http://localhost:3000/";
		
		//ExtentReport.extentLog = ExtentReport.extentReport.startTest("deleteSingleUser", "Delete the created user's record");
		
		Response resp = given()
						.when()
						.delete("/users/"+userid);
		
		resp.then().assertThat().statusCode(StatusCode.SUCCESS.code);
		System.out.println("****************** deleteSingleUser : Delete data for UserID : "+userid+" Executed Successfully *******************");
		System.out.println(resp.body().asString());
	}

}
