package JIRAResources;

public class payLoad {
	
	public static String createIssue() {
		
		String body ="{\n" + 
				"	\"fields\":{\n" + 
				"		\"project\":\n" + 
				"		{\n" + 
				"			\"key\":\"JIR\"\n" + 
				"		},\n" + 
				"		\"summary\":\"Second issue via restAssured\",\n" + 
				"		\"description\":\"Creating an issue with comments using the RESTAssured\",\n" + 
				"		\"issuetype\":{\n" + 
				"			\"name\":\"Bug\"\n" + 
				"		}\n" + 
				"	}\n" + 
				"}";
		return body;
		
	} 
	public static String addComment() {
		
		String body ="{\n" + 
				"    \"body\": \"comment added via restAssured.\",\n" + 
				"    \"visibility\": {\n" + 
				"       \"type\": \"role\",\n" + 
				"       \"value\": \"Administrators\"\n" + 
				"    }\n" + 
				"}";
		return body;
	}
	public static String editComment() {
		
		String body ="{\n" + 
				"    \"body\": \"comment edited via restAssured.\",\n" + 
				"    \"visibility\": {\n" + 
				"       \"type\": \"role\",\n" + 
				"       \"value\": \"Administrators\"\n" + 
				"    }\n" + 
				"}";
		return body;
	}
		
}
