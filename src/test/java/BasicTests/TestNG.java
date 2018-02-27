package BasicTests;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class TestNG {
	@Test
	public void Test()
	{
		RestAssured.baseURI="https://maps.googleapis.com";
		given().
				param("location","-33.8670,151.1957").
				param("key","AIzaSyBKv86vxfkzsZ5njlHMjpG3sARGuvV_hEg").
				param("radius","500").
				param("type","restauran").
				param("keyword","cruise").
				when().
				get("/maps/api/place/nearbysearch/json").
				then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.JSON).and().
				body("results[0].name",equalTo("Sydney Princess Cruises"));
		
	}

}
