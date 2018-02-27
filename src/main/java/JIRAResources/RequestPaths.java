package JIRAResources;

public class RequestPaths {
	public static String createSession(){
		String res = "/rest/auth/1/session";
		return res;
		
	}
	
	public static String createIssue(){
		String res = "/rest/api/2/issue";
		return res;
		
	}
	public static String deleteIssue(String issueid){
		
		String res = "/rest/api/2/issue/"+issueid;
		return res;
		
	}
	public static String addComment(String issueid){
		String res = "/rest/api/2/issue/"+issueid+"/comment";
		return res;
		
	}
	public static String updateComment(String issueid, String commentid){
		String res = "/rest/api/2/issue/"+issueid+"/comment/"+commentid;
		return res;
		
	}

}
