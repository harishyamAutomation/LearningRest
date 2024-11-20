package userSpecification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

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
