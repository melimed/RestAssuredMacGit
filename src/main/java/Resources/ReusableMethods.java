package Resources;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

import JIRAResources.RequestPaths;
import JIRAResources.payLoad;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ReusableMethods {
	static Properties prop = new Properties();
	
	public static FileInputStream loadFile() throws IOException {
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/JIRAResources/env.properties");
		return fis;
	}
	
	public static XmlPath RawtoXML(Response res) {
		String responseString =res.asString();
		//System.out.println(responseString);
		//convert the string on a xml
		XmlPath xml = new XmlPath(responseString);
		return xml;
	}
	public static JsonPath RawtoJson(Response res) {
		String responseString =res.asString();
		//System.out.println(responseString);
		//convert the string on a xml
		JsonPath json = new JsonPath(responseString);
		return json;
	}
	public static String jiraSession() throws IOException {
		prop.load(loadFile());
		RestAssured.baseURI=prop.getProperty("baseURI");
		Response res=given().header("Content-Type","application/json").
		body("{\"username\": \""+prop.getProperty("username")+"\", \"password\": \""+prop.getProperty("password")+"\"}").
		when().
		post(RequestPaths.createSession()).
		then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.JSON).and().
		extract().response();
		
		JsonPath js=ReusableMethods.RawtoJson(res);
		String session_id=js.get("session.value");
		return session_id;
	}
	public static String createtodeleteIssue() throws IOException
	{
		prop.load(loadFile());
		RestAssured.baseURI=prop.getProperty("baseURI");
		Response res=given().header("Content-Type","application/json").header("Cookie","JSESSIONID="+ReusableMethods.jiraSession()).
		body(payLoad.createIssue()).
		when().
		post(RequestPaths.createIssue()).
		then().assertThat().statusCode(201).
		extract().response();
		
		JsonPath js=ReusableMethods.RawtoJson(res);
		String issue_id=js.get("id");
		//System.out.println(issue_id);
		return issue_id;
	}

	

}
