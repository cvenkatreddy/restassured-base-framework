package api.users;

import static io.restassured.RestAssured.given;

import api.BaseAPI;

/**
 * 
 * Implementation of GET Service of /users
 */

public class GetUsers extends BaseAPI {

    String apiPath="/users";
    String userId;
    String userName;

    public GetUsers(String baseURI) {
        super(baseURI);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    protected void createRequest() {
        requestSpecBuilder.setBaseUri(baseURI);
        requestSpecBuilder.setBasePath(apiPath);
        requestSpecBuilder.addQueryParam("username", userName);
        requestSpecification=requestSpecBuilder.build();
    }

    @Override
    protected void executeRequest() {
        apiResponse = given().spec(requestSpecification).get();
    }

    @Override
    protected void validateResponse() {
        responseSpecBuilder.expectStatusCode(expectedStatusCode);
        responseSpecification=responseSpecBuilder.build();
        apiResponse.then().spec(responseSpecification);
        //System.out.println(apiResponse.asString());
    }
}
