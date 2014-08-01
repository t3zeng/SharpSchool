package automate;

public class UrlParser {
	static String parse(String currentpage, String link){
		if (link.contains("http")){
			return link;
		}
		else if(link.startsWith("..")){
			String s1 = currentpage.substring(0,currentpage.lastIndexOf('/'));
		//	String s2 = s1.substring(0,s1.lastIndexOf('/'));
			return parse(s1,link.substring(3));
		}
		else if(link.startsWith("/")){
			return currentpage.indexOf('/',9) + link;
		}
		else return currentpage.substring(0,currentpage.lastIndexOf('/')) + "/" + link;
	}
}
