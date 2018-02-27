package Resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class payLoad {
	
	public static String getPostData() {
		
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
		return body;
	} 
	public static String getPostDataxml() throws IOException {
			
		String body = GenerateStringFromResource("/Users/Melissa/eclipse-workspace/Git/src/main/java/Resources/getPostData.xml");
		return body;
	} 
	
	public static String GenerateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}
}
