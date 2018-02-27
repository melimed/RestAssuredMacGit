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
import Resources.ReusableMethods;
import TWITTERAPI.twitter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Basic5 {
	private static Logger Log = LogManager.getLogger(Basic5.class.getName());
	Properties prop = new Properties();
	@BeforeTest
	public void getData() throws IOException {
	
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/Resources/env.properties");
		prop.load(fis);
		
	}
	@Test
	public void Test()
	{
		RestAssured.baseURI=prop.getProperty("baseURI");
		Response res=given().
				param("location","-33.8670,151.1957").
				param("key","AIzaSyBKv86vxfkzsZ5njlHMjpG3sARGuvV_hEg").
				param("radius","500").
				param("type","restauran").
				param("keyword","cruise").
				when().
				get(RequestPaths.getPath()).
				then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.JSON).and().
				body("results[0].name",equalTo("Sydney Princess Cruises")).
				extract().response();
				JsonPath js=ReusableMethods.RawtoJson(res);
				
				int count =js.get("results.size()");
				Log.info(count);
				
				for(int i=0;i<count;i++) {
					String rname =js.getString("results["+i+"].name");
					Log.info(rname);
					
				}
					
		
	}
}
