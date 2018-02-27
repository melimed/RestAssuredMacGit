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
import Resources.payLoad;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class Basic4XML {
	private static Logger Log = LogManager.getLogger(Basic4XML.class.getName());
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
				get(RequestPaths.getPathxml()).
				then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.XML);
		
	}
	@Test
	public void postanddeleteData() throws IOException {
		//Sma epost ast previous test but with variables
		RestAssured.baseURI= prop.getProperty("baseURI");
		
		//pass the response on a variable
		Response response = given().
				queryParam("key",prop.getProperty("key")).
				body(payLoad.getPostDataxml()).
				when().
				post(RequestPaths.postPathxml()).
				then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.XML).and().
				extract().response();
		
		XmlPath xml = ReusableMethods.RawtoXML(response);
		//Get the desired parameter from the xml
		String place_id=xml.get("PlaceAddResponse.place_id");
		Log.info(place_id);
		
		given().
		queryParam("key","AIzaSyBKv86vxfkzsZ5njlHMjpG3sARGuvV_hEg").
		//pass the variable value to the new request to delete
		body("<PlaceDeleteRequest>\n" + 
				"<place_id>"+ place_id +"</place_id>\n" + 
				"</PlaceDeleteRequest>").
		when().
		post(RequestPaths.deletePathxml()).
		then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.XML);
	}


}
