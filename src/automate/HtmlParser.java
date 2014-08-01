package automate;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParser {
	HtmlParser(){
		
	}
	String Parse(String s, String storageLoc, String Host, String username, String password, String currentpage){
                String src = s;
                FileBus bus = new FileBus();
                bus.connect(Host, username , password , storageLoc);
                Document doc;
                int slashNum = StringUtils.countMatches(storageLoc, "/");
                String fileloc = "/UserFiles/Servers";
                for(int i=2;i<=slashNum;i++){
                                fileloc += "/"+storageLoc.split("/")[i];
                }                                     
                doc = Jsoup.parse(src);
                String relativelink;                                                 
                //pageTitle = doc.html().substring(doc.html().indexOf("<h1>")+5).split("</h1>")[0];
                
                
                //grabs the old website without anything after the first /
       		 	String oldStorage="";
                //filter code to be good

                	for(int file=0;file<doc.getElementsByTag("a").size();file++)
                	{
                        relativelink = doc.getElementsByTag("a").get(file).attr("href");
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
							        bus.download(UrlParser.parse(currentpage, relativelink));
							        System.out.println(UrlParser.parse(currentpage, relativelink));
							      /*  System.out.println(doc.getElementsByTag("a").get(file).attr("href")+" "+doc.getElementsByTag("a").get(file).attr("href").contains(oldStorage));
							        if(!doc.getElementsByTag("a").get(file).attr("href").contains(oldStorage)&&doc.getElementsByTag("a").get(file).attr("href").contains("http"))
							        {
							        	System.out.println(doc.getElementsByTag("a").get(file).attr("href"));
								        bus.download(doc.getElementsByTag("a").get(file).attr("href"));
							        }
							        else
							        {
								        System.out.println(oldStorage+doc.getElementsByTag("a").get(file).attr("href"));
								        bus.download(oldStorage+doc.getElementsByTag("a").get(file).attr("href"));
							        }*/
							        doc.getElementsByTag("a").get(file).attr("href",fileloc+"/"+newFilename);
							        
                        }
                	}


                	for(int img=0;img<doc.getElementsByTag("img").size();img++)
                	{
                		relativelink = doc.getElementsByTag("img").get(img).attr("src");
                        if(relativelink.endsWith("jpg")||
                        relativelink.endsWith("png")||
                        relativelink.endsWith("gif")||
                        relativelink.endsWith("jpeg"))
                        {
                        
						        String newFilename = relativelink.substring(relativelink.lastIndexOf('/')+1);
						        bus.download(UrlParser.parse(currentpage, relativelink));
						        System.out.println(UrlParser.parse(currentpage, relativelink));
						 /*       System.out.println(doc.getElementsByTag("img").get(img).attr("src")+" "+doc.getElementsByTag("img").get(img).attr("src").contains(oldStorage));
						        if(!doc.getElementsByTag("img").get(img).attr("src").contains(oldStorage)&&doc.getElementsByTag("img").get(img).attr("src").contains("http"))
						        {
						        	System.out.println(doc.getElementsByTag("img").get(img).attr("src"));
							        bus.download((doc.getElementsByTag("img").get(img).attr("src")).replaceAll(oldStorage+oldStorage, oldStorage));
						        }
						        else if(doc.getElementsByTag("img").get(img).attr("src").startsWith("..")){
						        	bus.download((oldStorage+ doc.getElementsByTag("img").get(img).attr("src")).replaceAll(oldStorage+oldStorage, oldStorage));
						        }
						        else
						        {
						        	System.out.println(oldStorage+doc.getElementsByTag("img").get(img).attr("src"));
							        bus.download((oldStorage+doc.getElementsByTag("img").get(img).attr("src")).replaceAll(oldStorage+oldStorage, oldStorage));
						        }*/
						        doc.getElementsByTag("img").get(img).attr("src", fileloc+"/"+newFilename);

                        }
                	}

                
                    doc.getElementsByAttribute("class").removeAttr("class");
                    doc.getElementsByAttribute("style").removeAttr("style");
		//	doc.getElementsMatchingOwnText("(.*javascript.*| 0x0| 0x1| ContentType| 0x01| 898)").remove();

//                usefulCode = usefulCode.replaceAll("class=\".*?\"", "");
//                usefulCode = usefulCode.replaceAll("style=\".*?\"", "");
//                usefulCode = usefulCode.replaceAll("&nbsp;&nbsp;", "");
//                usefulCode = usefulCode.replaceAll("<script.*(\n.*?){0,}</script>", "");
//                usefulCode = usefulCode.replaceAll("href=\"../", "href=\"");
//                usefulCode = usefulCode.replaceAll("&amp;","&");
                
                String usefulCode=doc.html();
                usefulCode=usefulCode.replaceAll(oldStorage+oldStorage, oldStorage);
                bus.upload();
                return usefulCode;
	}
}
