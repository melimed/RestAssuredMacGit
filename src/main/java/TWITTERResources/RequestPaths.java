package TWITTERResources;

public class RequestPaths {
	
	public static String postTweet(){
		String res = "/update.json";
		return res;
		
	}
	public static String getAllTweets(){
		String res = "/home_timeline.json";
		return res;
		
	}
	public static String deleteTweet(String tweet_id){
		String res = "/destroy/"+tweet_id+".json";
		return res;
		
	}

}
