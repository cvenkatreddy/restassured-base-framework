package api.comments;

import static io.restassured.RestAssured.given;

import api.BaseAPI;

/**
* 
* Implementation of GET Service of /comments
*/

public class GetComments extends BaseAPI {
	String apiPath = "/comments";
	int postId;

	public GetComments(String baseURI) {
		super(baseURI);
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	@Override
	protected void createRequest() {
		requestSpecBuilder.setBaseUri(baseURI);
		requestSpecBuilder.setBasePath(apiPath);
		requestSpecBuilder.addQueryParam("postId", postId);
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

//	public static void main(String[] args) {
//		GetComments comments = new GetComments("https://jsonplaceholder.typicode.com");
//		comments.postId = 1;
//		comments.createRequest();
//		comments.executeRequest();
//		comments.validateResponse();
//	}
}
