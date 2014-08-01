package automate;




import org.openqa.selenium.WebDriver;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.*;
import org.jsoup.nodes.Document;


public class htmlRetriever {
                String htmlContent;
                String pageTitle;
                WebDriver drive = new HtmlUnitDriver();
                FileBus bus = new FileBus();
    
    public static void main(String[] args)
    {
    	new htmlRetriever("http://192.99.147.90/mirrors/Aransas%20Pass%20ISD/www.apisd.org/acb/indexcf3c.html","maincolumn","/UserFiles/Servers/Server_43687/File/migration/");
    }
    public htmlRetriever(String url, String contentID, String storageLoc)
    {
                //WebDriver drive = new HtmlUnitDriver();
                //WebDriver drive = new FirefoxDriver();
                htmlContent=pageSource(url, drive, contentID, storageLoc);
    }
    public String pageSource(String website, WebDriver oldStuff, String elementID, String storageLoc)
    {
    	 
    	try
    	{
    			System.out.println(website);
                oldStuff.get(website);
                String src = oldStuff.getPageSource();
                Document doc;
                int slashNum = StringUtils.countMatches(storageLoc, "/");
                String fileloc = "/UserFiles/Servers";
                for(int i=3;i<=slashNum;i++){
                                fileloc += "/"+storageLoc.split("/")[i];
                }                                     
                doc = Jsoup.parse(src);
                                                                
                pageTitle = doc.title();
                //pageTitle = doc.html().substring(doc.html().indexOf("<h1>")+5).split("</h1>")[0];
                
                oldStuff.close();
                
                //grabs the old website without anything after the first /
                String[] parts=website.split("/");
       		 	String oldStorage="";
       		 	for(int i=0;i<3;i++)
       		 		oldStorage+=parts[i]+"/";
       		 	String oldDomain = website.substring(0, website.lastIndexOf("/")+1);
                //filter code to be good
                String usefulCode=doc.getElementById(elementID).html();
                if(!doc.getElementsByTag("a").attr("href").startsWith(".."))
                {
                	for(int file=0;file<doc.getElementsByTag("a").size();file++)
                	{
                        String relativelink = doc.getElementsByTag("a").get(file).attr("href");
                        if(relativelink.endsWith("jpg")||
                                         relativelink.endsWith("png")||
                                         relativelink.endsWith("gif")||
                                         relativelink.endsWith("pdf")||
                                         relativelink.endsWith("ppt")||
                                         relativelink.endsWith("pptx")||
                                         relativelink.endsWith("doc")||
                                         relativelink.endsWith("docx")||
                                         relativelink.endsWith("jpeg"))
                        {
							        String newFilename = relativelink.substring(relativelink.lastIndexOf('/')+1);
							        if(!doc.getElementsByTag("a").get(file).attr("href").contains(oldStorage)&&doc.getElementsByTag("a").get(file).attr("href").contains("http"))
							        {
							        	System.out.println(doc.getElementsByTag("a").get(file).attr("href"));
								        bus.download(doc.getElementsByTag("a").get(file).attr("href"));
							        }
							        else
							        {
								        System.out.println(oldStorage+doc.getElementsByTag("a").get(file).attr("href"));
								        bus.download(oldStorage+doc.getElementsByTag("a").get(file).attr("href"));
							        }
							        doc.getElementsByTag("a").get(file).attr("href",fileloc+"/"+newFilename);
                        }

                	}
                }
                if(!doc.getElementsByTag("img").attr("src").startsWith(".."))
                {
                	for(int img=0;img<doc.getElementsByTag("img").size();img++)
                	{
                		
                		String relativelink = doc.getElementsByTag("img").get(img).attr("src");
                        if(relativelink.endsWith("jpg")||
                        relativelink.endsWith("png")||
                        relativelink.endsWith("gif")||
                        relativelink.endsWith("jpeg"))
                        {
                        
						        String newFilename = relativelink.substring(relativelink.lastIndexOf('/')+1);
						        if(!doc.getElementsByTag("img").get(img).attr("src").contains(oldStorage)&&doc.getElementsByTag("img").get(img).attr("src").contains("http"))
						        {
						        	System.out.println(doc.getElementsByTag("img").get(img).attr("src"));
							        bus.download((doc.getElementsByTag("img").get(img).attr("src")).replaceAll(oldStorage+oldStorage, oldStorage));
						        }
						        else
						        {
						        	System.out.println(oldStorage+doc.getElementsByTag("img").get(img).attr("src"));
							        bus.download((oldStorage+doc.getElementsByTag("img").get(img).attr("src")).replaceAll(oldStorage+oldStorage, oldStorage));
						        }
						        doc.getElementsByTag("img").get(img).attr("src", fileloc+"/"+newFilename);

                        }
                	
                	}
                }
                
                    doc.getElementsByAttribute("class").removeAttr("class");
                    doc.getElementsByAttribute("style").removeAttr("style");
			doc.getElementsMatchingOwnText("(.*javascript.*| 0x0| 0x1| ContentType| 0x01| 898)").remove();

//                usefulCode = usefulCode.replaceAll("class=\".*?\"", "");
//                usefulCode = usefulCode.replaceAll("style=\".*?\"", "");
//                usefulCode = usefulCode.replaceAll("&nbsp;&nbsp;", "");
//                usefulCode = usefulCode.replaceAll("<script.*(\n.*?){0,}</script>", "");
//                usefulCode = usefulCode.replaceAll("href=\"../", "href=\"");
//                usefulCode = usefulCode.replaceAll("&amp;","&");
                
                usefulCode=doc.getElementById(elementID).html();
                usefulCode=usefulCode.replaceAll(oldStorage+oldStorage, oldStorage);
                uploader(storageLoc.replaceAll("/UserFiles/Servers/Server_", "").replaceAll("/File/migration", ""));
                return usefulCode;
    	}
    	catch(Exception e)
    	{
    		pageTitle = website;
    		return "";
    	}
    }
    
    public String getHTMLContent()
    {
                return htmlContent;
    }
    public void uploader(String serverNumber)
    {
    	bus.connect("155.254.144.7", "Gordon.Duff" , "fr&4Eqe" , "/Production 6/Server_"+serverNumber+"/File/migration");
        bus.upload();
    }
   
}