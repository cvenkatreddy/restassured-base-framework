package api.posts;

import static io.restassured.RestAssured.given;

import api.BaseAPI;

/**
 * 
 * Implementation of GET Service of /posts
 */

public class GetPosts extends BaseAPI {

	String apiPath = "/posts";
	int userId;

	public GetPosts(String baseURI) {
		super(baseURI);
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	protected void createRequest() {
		requestSpecBuilder.setBaseUri(baseURI);
		requestSpecBuilder.setBasePath(apiPath);
		requestSpecBuilder.addQueryParam("userId", userId);
		requestSpecification = requestSpecBuilder.build();
	}

	@Override
	protected void executeRequest() {
		apiResponse = given().spec(requestSpecification).get();
	}

	@Override
	protected void validateResponse() {
		responseSpecBuilder.expectStatusCode(expectedStatusCode);
		responseSpecification = responseSpecBuilder.build();
		apiResponse.then().spec(responseSpecification);
		//System.out.println(apiResponse.asString());
	}
}
