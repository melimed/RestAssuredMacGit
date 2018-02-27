package BasicTests;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import TWITTERAPI.twitter;
import groovy.util.logging.Log;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Basic2 {
	private static Logger Log = LogManager.getLogger(Basic2.class.getName());
	@Test
	public void postData() {
		RestAssured.baseURI="https://maps.googleapis.com";
		given().
				queryParam("key","AIzaSyBKv86vxfkzsZ5njlHMjpG3sARGuvV_hEg").
				body("{\n" + 
						"  \"location\": {" + 
						"    \"lat\": -33.8669710," + 
						"    \"lng\": 151.1958750" + 
						"  }," + 
						"  \"accuracy\": 50," + 
						"  \"name\": \"Google Shoes!\"," + 
						"  \"phone_number\": \"(02) 9374 4000\"," + 
						"  \"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\"," + 
						"  \"types\": [\"shoe_store\"],\n" + 
						"  \"website\": \"http://www.google.com.au/\"," + 
						"  \"language\": \"en-AU\"" + 
						"}").
				when().
				post("/maps/api/place/add/json").
				then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.JSON).and().
				//body("results[0].geometry.location.lat",equalTo("-33.86759"));
				body("scope",equalTo("APP")).and().
				body("status",equalTo("OK"));
		
		
	}
	@Test
	public void postanddeleteData() {
		//Sma epost ast previous test but with variables
		RestAssured.baseURI="https://maps.googleapis.com";
		
		String body ="{\n" + 
				"  \"location\": {" + 
				"    \"lat\": -33.8669710," + 
				"    \"lng\": 151.1958750" + 
				"  }," + 
				"  \"accuracy\": 50," + 
				"  \"name\": \"Google Shoes!\"," + 
				"  \"phone_number\": \"(02) 9374 4000\"," + 
				"  \"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\"," + 
				"  \"types\": [\"shoe_store\"],\n" + 
				"  \"website\": \"http://www.google.com.au/\"," + 
				"  \"language\": \"en-AU\"" + 
				"}";
		//pass the response on a variable
		Response response = given().
				queryParam("key","AIzaSyBKv86vxfkzsZ5njlHMjpG3sARGuvV_hEg").
				body(body).
				when().
				post("/maps/api/place/add/json").
				then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.JSON).and().
				body("scope",equalTo("APP")).and().
				body("status",equalTo("OK")).and().
				extract().response();
		//convert the response on a string
		String responseString =response.asString();
		System.out.println(responseString);
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
		post("/maps/api/place/delete/json").
		then().assertThat().statusCode(200).and().assertThat().contentType(ContentType.JSON);
	}

}
