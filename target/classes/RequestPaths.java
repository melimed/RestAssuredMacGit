package Resources;

public class RequestPaths {
	
	public static String getPath(){
		String res = "/maps/api/place/nearbysearch/json";
		return res;
		
	}
	public static String postPath(){
		String res = "/maps/api/place/add/json";
		return res;
		
	}
	public static String deletePath(){
		String res = "/maps/api/place/delete/json";
		return res;
		
	}
	public static String getPathxml(){
		String res = "/maps/api/place/nearbysearch/xml";
		return res;
		
	}
	public static String postPathxml(){
		String res = "/maps/api/place/add/xml";
		return res;
		
	}
	public static String deletePathxml(){
		String res = "/maps/api/place/delete/xml";
		return res;
		
	}

}
