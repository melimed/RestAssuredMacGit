package TWITTERAPI;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import TWITTERResources.RequestPaths;
import TWITTERResources.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class twitter {
	private static Logger Log = LogManager.getLogger(twitter.class.getName());
	Properties prop = new Properties();
	String id;
	@BeforeTest
	public void getData() throws IOException {
	
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/TWITTERResources/env.properties");
		prop.load(fis);
	}
	@Test
	public void getAllTweets() throws IOException
	{	
		Log.info("Base URI ="+prop.getProperty("baseURI"));
		RestAssured.baseURI=prop.getProperty("baseURI");
		Response res=given().auth().oauth(prop.getProperty("consumerkey"), prop.getProperty("consumersecret"), prop.getProperty("token"), prop.getProperty("tokensecret")).
		when().
		get(RequestPaths.getAllTweets()).
		then().assertThat().statusCode(200).
		extract().response();
		
		JsonPath js=ReusableMethods.RawtoJson(res);
		Log.info(js);
	}
	@Test
	public void postTweet() throws IOException
	{
		RestAssured.baseURI=prop.getProperty("baseURI");
		Response res=given().auth().oauth(prop.getProperty("consumerkey"), prop.getProperty("consumersecret"), prop.getProperty("token"), prop.getProperty("tokensecret")).
		queryParam("status", "hi, "+ReusableMethods.RandomWord()).
		when().
		post(RequestPaths.postTweet()).
		then().assertThat().statusCode(200).
		extract().response();
		JsonPath js=ReusableMethods.RawtoJson(res);
		id=js.get("id").toString();
		Log.info(id);
	}
	@Test
	public void deleteTweet() throws IOException
	{
		postTweet();
		RestAssured.baseURI=prop.getProperty("baseURI");
		Response res=given().auth().oauth(prop.getProperty("consumerkey"), prop.getProperty("consumersecret"), prop.getProperty("token"), prop.getProperty("tokensecret")).
		when().
		post(RequestPaths.deleteTweet(id)).
		then().assertThat().statusCode(200).
		extract().response();
		
		JsonPath js=ReusableMethods.RawtoJson(res);
		String tweet_id=js.get("id").toString();
		Log.info(tweet_id);
	}
}
