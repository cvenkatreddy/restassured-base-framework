package test;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.comments.GetComments;
import api.posts.GetPosts;
import api.users.GetUsers;
import env.ApplicationProperties;
import env.Environment;
import pojo.Comments;
import pojo.Posts;
import pojo.Users;
import validators.EmailValidator;

/**
 * Tests for verify the emails fetched from comments of the users
 * 
 */

public class VerifyEmailTest extends BaseTest {

	private final Logger logger = LoggerFactory.getLogger(VerifyEmailTest.class);
	ApplicationProperties appProps = Environment.INSTANCE.getApplicationProperties();
	EmailValidator emailValidator  = new EmailValidator();

	/**
	 * This test is to verify the email is valid or not
	 * @param testData
	 * @param result
	 * @throws IOException
	 */
	@Test(dataProvider = "testData")
	public void verifyEmailPositiveTest(Map<String, String> testData, String result) throws IOException {
		GetUsers getUsers = new GetUsers(appProps.getBaseURL());
		getUsers.setExpectedStatusCode(Integer.parseInt(testData.get("statuscode")));
		getUsers.setUserName(String.valueOf(testData.get("username")));
		getUsers.perform();
		Users[] users = getUsers.getAPIResponseAsPOJO(Users[].class);
		logger.info(" user {}'s user id:: {} of /users API",testData.get("username"), users[0].getId());

		GetPosts getPosts = new GetPosts(appProps.getBaseURL());
		getPosts.setUserId(users[0].getId());
		getPosts.setExpectedStatusCode(Integer.parseInt(testData.get("statuscode")));
		getPosts.perform();
		Posts[] posts = getPosts.getAPIResponseAsPOJO(Posts[].class);
		for (int postCounter = 1; postCounter <= posts.length; postCounter++) {
			int postid = posts[postCounter - 1].getId();
			logger.info("Fetched post id from /posts:: {} ", postid);
			GetComments getComments = new GetComments(appProps.getBaseURL());
			getComments.setExpectedStatusCode(Integer.parseInt(testData.get("statuscode")));
			getComments.setPostId(postid);
			getComments.perform();
			Comments[] comments = getComments.getAPIResponseAsPOJO(Comments[].class);
			for (int commentCounter = 1; commentCounter <= comments.length; commentCounter++) {
				logger.info("Fetched email from /comments:: {} ", comments[commentCounter - 1].getEmail());
				Assert.assertTrue(emailValidator.isValid(comments[commentCounter - 1].getEmail()),
						"Fetched email from /comments is not valid");
			}
		}
	}
}