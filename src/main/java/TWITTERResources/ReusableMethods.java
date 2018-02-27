package TWITTERResources;

import java.util.Properties;
import java.util.Random;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ReusableMethods {
	static Properties prop = new Properties();
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

	public static String RandomWord()
	{
	    String randomStrings;
	    Random random = new Random();
	        char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
	        for(int j = 0; j < word.length; j++)
	        {
	            word[j] = (char)('a' + random.nextInt(26));
	        }
	        randomStrings= new String(word);
	   
	    return randomStrings;
	}
	
}
