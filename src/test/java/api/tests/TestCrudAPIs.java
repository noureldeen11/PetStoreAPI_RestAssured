package api.tests;

import api.endpoints.Request.WebClient;
import api.endpoints.Request.UserData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import com.github.javafaker.Faker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestCrudAPIs {
	
	Faker faker;
	UserData user;
	public Logger logger; // for logs

	@BeforeClass
	public void setup()
	{
		faker=new Faker();
		user =new UserData();
		
		user.setId(faker.idNumber().hashCode());
		user.setUsername(faker.name().username());
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setEmail(faker.internet().safeEmailAddress());
		user.setPassword(faker.internet().password(5, 10));
		user.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger= LogManager.getLogger(this.getClass());
		
		logger.debug("debugging.....");
		
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("********** Creating user  ***************");
		System.out.println(user.getUsername());
		Response response= WebClient.createUser(user);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("**********WebClient is creatged  ***************");
			
	}
	
	@Test(priority=2)
	public void testGetUserByName()
	{
		logger.info("********** Reading WebClient Info ***************");
		
		Response response= WebClient.readUser(this.user.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("**********WebClient info  is displayed ***************");
		
	}
	
	@Test(priority=3)
	public void testUpdateUserByName()
	{
		logger.info("********** Updating WebClient ***************");
		
		//update data using payload
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setEmail(faker.internet().safeEmailAddress());
		
		Response response= WebClient.updateUser(this.user.getUsername(), user);
		response.then().log().body();
				
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("********** WebClient updated ***************");
		//Checking data after update
		Response responseAfterupdate= WebClient.readUser(this.user.getUsername());
		Assert.assertEquals(responseAfterupdate.getStatusCode(),200);
			
	}
	
	@Test(priority=4)
	public void testDeleteUserByName()
	{
		logger.info("**********   Deleting WebClient  ***************");
		
		Response response= WebClient.deleteUser(this.user.getUsername());
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("********** WebClient deleted ***************");
	}
}
