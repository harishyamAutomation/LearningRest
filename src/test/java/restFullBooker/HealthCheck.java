package restFullBooker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import core.BaseTest;
import core.StatusCode;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.AuthTokenPOJO;
import pojo.BookingDetailPOJO;
import userSpecification.UserRequestSpecification;
import utils.JSONReader;
import utils.SoftAssertionUtil;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class HealthCheck extends BaseTest{

	private String baseURI = "https://restful-booker.herokuapp.com";
	private static String authToken;
	
	@Test(description = "RB_01 : Test to validate the Healthcheck point for the API.", priority=1)
	public void pingServerForHealthCheck() throws IOException, ParseException {
		
		RequestSpecification spec = UserRequestSpecification.getInstance().getRequestSpecification(baseURI);
		
		Response response = given()
			.spec(spec)
			.when()
			.get("/ping");
		
		System.out.println("****************************** pingServerForHealthCheck ************************************");	
		System.out.println(response.body().asString());	
		SoftAssertionUtil.getInstance()
								.assertEquals(response.statusCode(), StatusCode.CREATED.code, "Response Code is not 201 instead got : "+response.statusCode());
		SoftAssertionUtil.getInstance()
								.assertTrue(response.body().asString().indexOf("Created")!=-1, "Response body null!");
		SoftAssertionUtil.getInstance()
								.AssertAll();
		
	}
	
	private AuthTokenPOJO authTokenBody() {
		AuthTokenPOJO getAuthTokenBody = new AuthTokenPOJO();
		getAuthTokenBody.setUsername("admin");
		getAuthTokenBody.setPassword("password123");
		
		return getAuthTokenBody;
	}
	
	@Test(description = "Test to create new auth token for booking", priority=2)
	public void getAuthToken() throws IOException, ParseException {
		
		System.out.println(authTokenBody().toString());
		Response response = given()
			.spec(UserRequestSpecification.getInstance().getRequestSpecification(baseURI, "application/json"))
			.body(authTokenBody())
			.when()
			.post("/auth");
		
		System.out.println("****************************** getAuthToken ************************************");
		
		String body = response.body().asString();
		
		System.out.println("Response Body : "+body);
		
		SoftAssertionUtil.getInstance()
								.assertEquals(response.statusCode(), StatusCode.SUCCESS.code, "Response Code is not 200 instead get : "+response.statusCode());
		
		System.out.println("*************** Status Code validated ******************");

		SoftAssertionUtil.getInstance()
								.assertTrue(response.body().asString().indexOf("token")!=-1, "Token not found");
		
		System.out.println("*************** Token Found and validated ******************");
		
		authToken = JSONReader.getTestData(body, "token");
		//System.out.println("Token : "+authToken);
		
		SoftAssertionUtil.getInstance()
								.AssertAll();
		
	}
	
	@Test(description = "Test to fetch all the booking IDs", priority=3)
	public void fetchAllBookingIDs() {
		Response response = given()
			.spec(UserRequestSpecification.getInstance().getRequestSpecification(baseURI))
			.when()
			.get("/booking");
		
		System.out.println("****************************** fetchAllBookingIDs ************************************");
		
		SoftAssertionUtil.getInstance()
								.assertEquals(response.statusCode(), StatusCode.SUCCESS.code, "Response Code is not 200 instead get : "+response.statusCode());
		
		String body = response.body().asString();
		System.out.println("Response Body : "+body);
		
		System.out.println(">>>>>>>> Status Code Validated");
		
		SoftAssertionUtil.getInstance().assertTrue(body.indexOf("bookingid")!=-1, "BookingIDs not found");
		
		System.out.println(">>>>>>>> Booking IDs found");
		
		SoftAssertionUtil.getInstance().AssertAll();
		
	}
	
	@Test(description="Test to fetch all the booking IDs filtered by first & last name", priority=4, enabled=false)
	public void filterBookingIDsByName() {
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("firstname", "sally");
		queryParams.put("lastname", "brown");
		
		Response response = given()
			.spec(UserRequestSpecification.getInstance().getRequestSpecification(baseURI))
			.queryParams(queryParams)
			.when()
			.get("/booking");
		
		System.out.println("****************************** filterBookingIDsByName ************************************");
		
		SoftAssertionUtil.getInstance()
								.assertEquals(response.statusCode(), StatusCode.SUCCESS.code, "Response Code is not 200 instead get : "+response.statusCode());
		
		System.out.println(">>>>>>>>> Status Code validated");
		String responseBody = response.body().asString();
		System.out.println("Response Body : "+responseBody);
		
		SoftAssertionUtil.getInstance()
								.assertTrue(responseBody.indexOf("bookingid")!=-1, "BookingIDs not found");
		
		System.out.println(">>>>>>>> Booking IDs found");
		
		SoftAssertionUtil.getInstance().AssertAll();
	}
	
	@Test(description="Test to fetch all the booking IDs filtered by checkIn & checkOut Dates", priority=5, enabled=false)
	public void filterBookingIDsByCheckInOutDate() {
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("checkin", "2014-03-13");
		queryParams.put("checkout", "2014-05-21");
		
		Response response = given()
			.spec(UserRequestSpecification.getInstance().getRequestSpecification(baseURI))
			.queryParams(queryParams)
			.when()
			.get("/booking");
		
		System.out.println("****************************** filterBookingIDsByCheckInOutDate ************************************");
		
		SoftAssertionUtil.getInstance()
								.assertEquals(response.statusCode(), StatusCode.SUCCESS.code, "Response Code is not 200 instead get : "+response.statusCode());
		
		System.out.println(">>>>>>>>> Status Code validated");
		String responseBody = response.body().asString();
		System.out.println("Response Body : "+responseBody);
		
		SoftAssertionUtil.getInstance()
								.assertTrue(responseBody.indexOf("bookingid")!=-1, "BookingIDs not found");
		
		System.out.println(">>>>>>>> Booking IDs found");
		
		SoftAssertionUtil.getInstance().AssertAll();
	}
	
	@Test(description="Test to fetch the customer detail by booking ID", priority=6)
	public void fetchCustomerDetailByID() {
		
		int customerID = 15;
		String firstname = "Josh";
		String lastname = "Allen";
		int totalprice = 111;
		boolean depositpaid = true;
		String checkin = "2018-01-01";
		String checkout = "2019-01-01";
		String additionalneeds = "midnight snack";
				
		Response response = given()
			.spec(UserRequestSpecification.getInstance().getRequestSpecification(baseURI))
			.when()
			.get("/booking/"+customerID);
		
		System.out.println("****************************** filterBookingIDsByCheckInOutDate ************************************");
		
		SoftAssertionUtil.getInstance()
								.assertEquals(response.statusCode(), StatusCode.SUCCESS.code, "Response Code is not 200 instead get : "+response.statusCode());
		
		System.out.println(">>>>>>>>> Status Code validated");
		BookingDetailPOJO responseBody = response.body().as(BookingDetailPOJO.class);
		System.out.println("Response Body : "+responseBody.toString());
		
		SoftAssertionUtil.getInstance()
								.assertEquals(responseBody.getFirstname(), firstname, "FirstName not as expected");
		
		System.out.println(">>>>>>>> FirstName Matched");
		
		SoftAssertionUtil.getInstance()
		.assertEquals(responseBody.getLastname(), lastname, "LastName not as expected");

		System.out.println(">>>>>>>> LastName Matched");
		
		SoftAssertionUtil.getInstance()
		.assertEquals(responseBody.getTotalprice(), totalprice, "totalprice not as expected");

		System.out.println(">>>>>>>> totalprice Matched");
		
		SoftAssertionUtil.getInstance()
		.assertEquals(responseBody.isDepositpaid(), depositpaid, "depositpaid not as expected");

		System.out.println(">>>>>>>> depositpaid Matched");
		
		SoftAssertionUtil.getInstance()
		.assertEquals(responseBody.getBookingdates().get("checkin"), checkin, "checkin not as expected");

		System.out.println(">>>>>>>> checkin Matched");
		
		SoftAssertionUtil.getInstance()
		.assertEquals(responseBody.getBookingdates().get("checkout"), checkout, "checkin not as expected");

		System.out.println(">>>>>>>> checkout Matched");
		
		SoftAssertionUtil.getInstance()
		.assertEquals(responseBody.getAdditionalneeds(), additionalneeds, "totalprice not as expected");

		System.out.println(">>>>>>>> additionalneeds Matched");
		
		SoftAssertionUtil.getInstance().AssertAll();
	}
}
