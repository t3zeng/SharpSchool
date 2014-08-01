
package automate;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;

public class SiteTraversal 
{
           
            public static void main(String[] args)
            {
                        new SiteTraversal("http://www.delranschools.org/site/default.aspx?PageID=1");
            }
            public SiteTraversal(String hp)
            {
                        
                        WebDriver driver = new HtmlUnitDriver();
                        driver.get(hp);
                        String src = driver.getPageSource();
                        Document doc;
                                                        
                        doc = Jsoup.parse(src);
                                                                        
                        driver.close();
                        
                        //grabs the old website without anything after the first /
                        String[] parts=hp.split("/");
               		 	String oldStorage="";
               		 	for(int i=0;i<3;i++)
               		 		oldStorage+=parts[i]+"/";

                        //filter code to be good
                        //String usefulCode=doc.getElementById(elementID).html();
               		 	ArrayList links = new ArrayList();
               		 	for(int i=0;i<doc.getElementsByAttribute("href").size();i++)
               		 	{
               		 		if(doc.getElementsByAttribute("href").get(i).outerHtml().contains("href=\""+oldStorage)||doc.getElementsByAttribute("href").get(i).outerHtml().contains("href=\"/"))
               		 			if(doc.getElementsByAttribute("href").get(i).html().replaceAll("<.*?>", "").startsWith(" "))
               		 				links.add(doc.getElementsByAttribute("href").get(i).html().replaceAll("<.*?>", ""));
            
               		 	}
               		 	System.out.println(doc.getElementsByAttribute("href").size());
               		 	for(int j=0;j<links.size();j++)
               		 		System.out.println(links.get(j));
            }
            
            
            //might be useful
}
