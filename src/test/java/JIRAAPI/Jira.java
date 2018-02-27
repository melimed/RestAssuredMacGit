package JIRAAPI;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import JIRAResources.RequestPaths;
import JIRAResources.ReusableMethods;
import JIRAResources.payLoad;
import TWITTERAPI.twitter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Jira {
	private static Logger Log = LogManager.getLogger(Jira.class.getName());
	Properties prop = new Properties();
	@BeforeTest
	public void getData() throws IOException {
	
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/JIRAResources/env.properties");
		prop.load(fis);
		
	}
	@Test
	public void createIssue() throws IOException
	{
		
		//Creating session
		RestAssured.baseURI=prop.getProperty("baseURI");
		Response res=given().header("Content-Type","application/json").header("Cookie","JSESSIONID="+ReusableMethods.jiraSession()).
		body(payLoad.createIssue()).
		when().
		post(RequestPaths.createIssue()).
		then().assertThat().statusCode(201).
		extract().response();
		
		JsonPath js=ReusableMethods.RawtoJson(res);

	}
	
	@Test
	public void commentIssue() throws IOException
	{
		RestAssured.baseURI=prop.getProperty("baseURI");
		Response res=given().header("Content-Type","application/json").header("Cookie","JSESSIONID="+ReusableMethods.jiraSession()).
		body(payLoad.addComment()).
		when().
		post(RequestPaths.addComment(ReusableMethods.createtodeleteIssue())).
		then().assertThat().statusCode(201).
		extract().response();
		
		JsonPath js=ReusableMethods.RawtoJson(res);
	}
	@Test
	public void updateComment() throws IOException
	{
		String issue = ReusableMethods.createtodeleteIssue();
		String session = ReusableMethods.jiraSession();
		RestAssured.baseURI=prop.getProperty("baseURI");
		Response resc=given().header("Content-Type","application/json").header("Cookie","JSESSIONID="+session).
				body(payLoad.addComment()).
				when().
				post(RequestPaths.addComment(issue)).
				then().assertThat().statusCode(201).
				extract().response();
		JsonPath jsc=ReusableMethods.RawtoJson(resc);
		String comment_id=jsc.get("id");
		Log.info(comment_id);
		
		Response resu=given().header("Content-Type","application/json").header("Cookie","JSESSIONID="+session).
		body(payLoad.editComment()).
		when().
		put(RequestPaths.updateComment(issue,comment_id)).
		then().assertThat().statusCode(200).
		extract().response();
		
		JsonPath jsu=ReusableMethods.RawtoJson(resu);
		String upcomment_id=jsu.get("id");
		Log.info(upcomment_id);
	}
	@Test
	public void deteleIssue() throws IOException
	{
		RestAssured.baseURI=prop.getProperty("baseURI");
		given().header("Content-Type","application/json").header("Cookie","JSESSIONID="+ReusableMethods.jiraSession()).
		when().
		delete(RequestPaths.deleteIssue(ReusableMethods.createtodeleteIssue())).
		then().assertThat().statusCode(204).
		extract().response();
		
	}

}
