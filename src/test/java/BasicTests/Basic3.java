package BasicTests;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import Resources.RequestPaths;
import Resources.payLoad;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Basic3 {
	private static Logger Log = LogManager.getLogger(Basic3.class.getName());
	Properties prop = new Properties();
	//if you make your method static on the class you don have to create and object on the class where you want to call it
	//RequestPaths requestPaths = new RequestPaths();
	
	@BeforeTest
	public void getData() throws IOException {
	
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/Resources/env.properties");
		prop.load(fis);
		
	}
	@Test
	public void Test()
	{
		RestAssured.baseURI=prop.getProperty("baseURI");
		given().
				param("location","-33.8670,151.1957").
				param("key","AIzaSyBKv86vxfkzsZ5njlHMjpG3sARGuvV_hEg").
				param("radius","500").
				param("type","restauran").
				param("keyword","cruise").
				when().
				get(RequestPaths.getPath()).
				then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.JSON).and().
				body("results[0].name",equalTo("Sydney Princess Cruises"));
		
	}
	@Test
	public void postanddeleteData() {
		//Sma epost ast previous test but with variables
		RestAssured.baseURI= prop.getProperty("baseURI");
		
		//pass the response on a variable
		Response response = given().
				queryParam("key",prop.getProperty("key")).
				body(payLoad.getPostData()).
				when().
				post(RequestPaths.postPath()).
				then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.JSON).and().
				body("scope",equalTo("APP")).and().
				body("status",equalTo("OK")).and().
				extract().response();
		//convert the response on a string
		String responseString =response.asString();
		Log.info(responseString);
		//convert the string on a json
		JsonPath js = new JsonPath(responseString);
		//Get the desired parameter from the json
		String place_id=js.get("place_id");
		Log.info(place_id);
		
		//Post request delete
		given().
		queryParam("key","AIzaSyBKv86vxfkzsZ5njlHMjpG3sARGuvV_hEg").
		//pass the variable value to the new request to delete
		body("{"
				+ "\"place_id\":"+ "\""+ place_id +"\""
			+ "}").
		when().
		post(RequestPaths.deletePath()).
		then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.JSON);
	}


}
