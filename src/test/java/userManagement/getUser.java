package userManagement;

import utility.BaseTest;
import core.StatusCode;
import helper.BaseTestHelper;
import io.restassured.authentication.OAuthSignature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ExtentReport;
import utils.JSONReader;
import utils.PropertyReader;
import utility.SoftAssertionUtil;

import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class getUser extends BaseTest{
		
    //Get all users detail for the page 2
    @Test(description = "Get all users data from page 2", priority=1)
    public void getUserData() {
        given().
                when().get("https://reqres.in/api/users?page=2").
                then().assertThat().statusCode(200);
    }

    @Test(description = "Validate the response body i.e. Status code using then method", priority=2)
    public void validateResponseBodyUsingThen() {
        baseURI = "https://reqres.in/api";
        given()
                .when().get("/users/2")
                .then().assertThat().statusCode(200).body(not(empty()));
    }

    @Test(description = "Validate the response body i.e. Status code using Response Interface method", priority=3)
    public void validateResponseBodyUsingVariable() {
        baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when().get("/todos/1")
                .then().extract().response();
        assertThat(response.getBody().asString(), not(emptyString()));
        assertThat(response.getBody().asString(), containsString("delectus aut autem"));
        assertThat(response.getBody().jsonPath().get("userId"), equalTo(1));
    }

    @Test(description = "Store response in a variable and validate particular field is present or not.", priority=4)
    public void validateResponseHasItems() {
        baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when().get("/posts");

        assertThat(response.jsonPath().getList("title"), hasItems("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));

    }

    @Test(description = "Validate response shold have 500 comments.", priority=5)
    public void validateResponseHasSize() {
        baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when().get("/comments");

        assertThat(response.jsonPath().getList(""), hasSize(500));
    }

    @Test(description = "Check if response body contains specific email/items", priority=6)
    public void validateListContainsItems() {
        baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when().get("/users");

        List<String> expectedEmails = Arrays.asList("Sincere@april.biz", "Shanna@melissa.tv", "Lucio_Hettinger@annie.ca");
        assertThat(response.jsonPath().getList("email"), hasItems(expectedEmails.toArray(new String[0])));
    }

    @Test(description = "Check if response body contains specific names/items", priority=7)
    public void validateListContainsNames() {
        baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when().get("/users");

        List<String> expectedNames = Arrays.asList("Leanne Graham", "Ervin Howell");
        assertThat(response.jsonPath().getList("name"), hasItems(expectedNames.toArray(new String[0]))); //Need to analyze why we passing new String[0], as without this also it's fine.
    }

    @Test(description = "Get users using single qyery parameter", priority=8)
    public void getUsersWithQueryParams() {
        baseURI = "https://reqres.in/api";

        Response res = given().queryParam("page", 2)
                .when().get("/users");

        System.out.println("++++++++++++++++" + res.getStatusCode() + "*******************\n");

        System.out.println("******************* Start Headers *********************");
        System.out.println(res.getHeaders());
        System.out.println("******************** End Headers ********************");

        System.out.println("******************* Start Body *********************");
        System.out.println(res.getBody().asString());
        System.out.println("******************** End Body ********************");
    }

    @Test(description = "Validate Response Body all values", priority=9)
    public void validateResponseBody() {
        baseURI = "https://reqres.in/api";

        Response response = given().queryParam("page", 2)
                .when().get("/users")
                .then().statusCode(200).extract().response();


        if (response.getStatusCode() == 200) {

                System.out.println("Header : " + response.getHeader("Content-Type"));
                System.out.println("Cookie : " + response.getCookies());

                System.out .println("************* Body : ********");
                System.out.println("Data Size : "+response.getBody().asString());

            response.then().body("data", hasSize(6));
            response.then().body("data[1].id", equalTo(8));
            response.then().body("data[1].email", equalTo("lindsay.ferguson@reqres.in"));
            response.then().body("data[1].first_name", equalTo("Lindsay"));
            response.then().body("data[1].last_name", equalTo("Ferguson"));
            response.then().body("data[1].avatar", equalTo("https://reqres.in/img/faces/8-image.jpg"));
            response.then().body("total", is(12));

        }

    }

    @Test(description = "Automate multiple query parameters", priority=10)
    public void automateMultipleQueryParams(){
        baseURI = "https://reqres.in/api";

        Response response = given()
                .queryParam("page", 2)
                .queryParam("per_page", 6)
                .when().get("/users")
                .then().assertThat().statusCode(200).extract().response();

        System.out.println("Body : "+response.getBody().asString());
    }

    @Test(description = "Automate Path parameters", priority=11)
    public void automatePathParams(){
       String raceSeason = "2017";

        baseURI = "http://ergast.com/api/f1";
        Response response = given().pathParam("raceSeason", raceSeason)
                .when().get("/{raceSeason}/circuits.json")
                .then().assertThat().statusCode(StatusCode.SUCCESS.code).extract().response();

        System.out.println("Body : "+response.getBody().asString());
    }

    @Test(description = "Automate Form Parameters", priority=12) //This function needs to be verified, as Postman completing the request but RestAssured not.
    public void automateFormParams(){
        baseURI = "https://reqres.in/api";
        Response response = given().contentType(ContentType.URLENC)
                .formParam("name", "Harishyam")
                .formParam("job", "QA Engineer")
                .when().post("/users")
                .then().assertThat().statusCode(201).extract().response();
        response.then().body("name", equalTo("Harishyam"));
        response.then().body("job", equalTo("QA Engineer"));

        System.out.println("Body : "+response.getBody().asString());

    }
    
    //Need to write auth Test methods here :
    
    @Test(description = "Automate Basic Authorization", priority=13)
    public void automateBasicAuth() {
    	baseURI = "https://postman-echo.com";
    	
    	Response resp = given().auth().basic("postman", "password").get("/basic-auth");
    	
    	System.out.println("BasicAuth : \n"+resp.getBody().asString());
    }
    
    @Test(description = "Automate Digest Authorization", priority=14)
    public void automateDigestAuth() {
    	baseURI = "https://postman-echo.com";
    	
    	Response resp = given().auth().digest("postman", "password").get();
    	
    	System.out.println("DigestAuth : \n"+resp.getBody().asString());
    }
    
    @Test(description = "Automate OAuth1.0 Authorization", groups = "RegressionSuite", priority = 15, enabled = false) //Need to deep dive into this
    public void automateOAuth1() {
    	baseURI = "https://postman-echo.com";
    	
    	//ExtentReport.extentLog = ExtentReport.extentReport.startTest("automateOAuth1", "Automate OAuth1.0 Authorization");// first parameter will be the name and second parameter will have the description about the test case
    	
    	Response resp = given().auth().oauth("RKCGzna7bv9YD57c", "D+EdQ-gs$-%@2Nu7", "", "", OAuthSignature.HEADER).get();
    	
    	System.out.println("OAuth1.0 : \n"+resp.getBody().asString());
    }
    
    @Test(description = "Fetch the TestData from JSON file and use in the script", groups = "RegressionSuite", priority=16)
    public void testDataFromJSONFile() throws IOException, ParseException {
    	String username = JSONReader.getTestData("username");
    	String password = JSONReader.getTestData("password");
    	
    	baseURI = "https://postman-echo.com";
    	
    	
    	//ExtentReport.extentLog = ExtentReport.extentReport.startTest("testDataFromJSONFile", "Fetch the TestData from JSON file and use in the script");// first parameter will be the name and second parameter will have the description about the test case
    	
    	Response resp = given().auth().basic(username, password).get("/basic-auth");
    	
    	assertEquals(resp.statusCode(), StatusCode.SUCCESS.code);
    	
    	System.out.println("********************* testDataFromJSONFile - PASS ***************************");
    }
    
    @Test(description = "Automate script using TestData from JSON and config from Properties file", groups = "RegressionSuite", priority = 17)
    public void testDataFromJSON_dataFromPropertiesFile() throws IOException, ParseException {
    	
    	//String baseURI = PropertyReader.propertyReader("config.properties", "baseURI");
    	String endPointBasic = PropertyReader.propertyReader("config.properties", "endPointBasic");
    	String username = JSONReader.getTestData("username");
    	String password = JSONReader.getTestData("password");
    	
    	//ExtentReport.extentLog = ExtentReport.extentReport.startTest("testDataFromJSON_dataFromPropertiesFile", "Automate script using TestData from JSON and config from Properties file");// first parameter will be the name and second parameter will have the description about the test case
    	
    	Response resp = given().auth().basic(username, password).get(endPointBasic);
    	
    	assertEquals(resp.statusCode(), StatusCode.SUCCESS.code);
    	
    	System.out.println("********************* testDataFromJSON_dataFromPropertiesFile - PASS ***************************");
    }
    
    @Test(description = "Automate multiple query parameters using Map", groups = "RegressionSuite", priority=18)
    public void automateMultipleQueryParamsUsingMap(){
        baseURI = "https://reqres.in/api";

        Map<String, Integer> queryParams = new HashMap<>();
        queryParams.put("page", 2);
        queryParams.put("per_page", 6);
        
      // ExtentReport.startTest("automateMultipleQueryParamsUsingMap", "Automate multiple query parameters using Map");// first parameter will be the name and second parameter will have the description about the test case
       
        Response response = given()
                .params(queryParams)
                .when().get("/users");
                
        SoftAssertionUtil.getInstance().assertEquals(response.statusCode(), StatusCode.SUCCESS.code, "Response is not 200");
        SoftAssertionUtil.getInstance().AssertAll();

        System.out.println("Body : "+response.getBody().asString());
    }
    
    @DataProvider(name="testData")
    public Object [][] testData(){
    	return new Object[][] {
    		{"2", "6"},
    		{"3", "7"},
    		{"4", "8"}
    	};
    	
    };
    
    @Test(dataProvider = "testData")
    @Parameters({"page", "per_page"})
    public void testEndPoints(String page, String per_page) {
    	
    	Response resp = given().queryParam("page", page).queryParam("per_page", per_page).when().get("https://reqres.in/api/users");
    	
    	SoftAssertionUtil.getInstance().assertEquals(resp.statusCode(), StatusCode.SUCCESS.code, "Status code in not 200");
    	SoftAssertionUtil.getInstance().AssertAll();
    	
    }
}
