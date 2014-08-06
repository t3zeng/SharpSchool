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

	public static void main(String[] args) {
		new htmlRetriever("url","maincolumn_full","/UserFiles/Servers/Server_27593/File/migration", "/UserFiles/Servers/Server_27593/Image/migration", "6");
	}

	public htmlRetriever(String url, String contentID, String storageFile, String storageImage, String prod) {
		// WebDriver drive = new HtmlUnitDriver();
		// WebDriver drive = new FirefoxDriver();
		htmlContent = pageSource(url, drive, contentID, storageFile, storageImage, prod);
	}

	public String pageSource(String website, WebDriver oldStuff,
			String elementID, String storageFile, String storageImage, String prod) {

		try {
			System.out.println(website);
			oldStuff.get(website);
			String src = oldStuff.getPageSource();
			Document doc;
			int slashNum = StringUtils.countMatches(storageFile, "/");
			String fileloc = "/UserFiles/Servers";
			String imageloc = "/UserFiles/Servers";
			for (int i = 3; i <= slashNum; i++) {
				fileloc += "/" + storageFile.split("/")[i];
				imageloc += "/" + storageImage.split("/")[i];
			}
			doc = Jsoup.parse(src);

			pageTitle = doc.title();
			// pageTitle =
			// doc.html().substring(doc.html().indexOf("<h1>")+5).split("</h1>")[0];

			oldStuff.close();

			// grabs the old website without anything after the first /
			String[] parts = website.split("/");
			String oldStorage = "";
			String newFilename;
			for (int i = 0; i < 3; i++)
				oldStorage += parts[i] + "/";
			// String oldDomain = website.substring(0,
			// website.lastIndexOf("/")+1);
			// filter code to be good
			String usefulCode = doc.getElementById(elementID).html();

			for (int file = 0; file < doc.getElementsByTag("a").size(); file++) {
				String relativelink = doc.getElementsByTag("a").get(file).attr("href");
				if (relativelink.endsWith("jpg")
						|| relativelink.endsWith("png")
						|| relativelink.endsWith("gif")
						|| relativelink.endsWith("pdf")
						|| relativelink.endsWith("ppt")
						|| relativelink.endsWith("pptx")
						|| relativelink.endsWith("doc")
						|| relativelink.endsWith("docx")
						|| relativelink.endsWith("jpeg")
						|| relativelink.endsWith("JPG")
						|| relativelink.endsWith("PNG")
						|| relativelink.endsWith("GIF")
						|| relativelink.endsWith("PDF")
						|| relativelink.endsWith("PPT")
						|| relativelink.endsWith("PPTX")
						|| relativelink.endsWith("DOC")
						|| relativelink.endsWith("DOCX")
						|| relativelink.endsWith("JPEG")) {
					String fullLink = UrlParser.parse(website, relativelink);
					newFilename = relativelink.substring(relativelink.lastIndexOf('/') + 1);
					
					System.out.println(fullLink);
					bus.download(fullLink);
					doc.getElementsByTag("a").get(file).attr("href", fileloc + "/" + newFilename);
				}

			}

			for (int img = 0; img < doc.getElementsByTag("img").size(); img++) {

				String relativelink = doc.getElementsByTag("img").get(img).attr("src");
				if (relativelink.endsWith("jpg")
						|| relativelink.endsWith("png")
						|| relativelink.endsWith("gif")
						|| relativelink.endsWith("jpeg")
						|| relativelink.endsWith("JPG")
						|| relativelink.endsWith("PNG")
						|| relativelink.endsWith("GIF")
						|| relativelink.endsWith("JPEG")) {
					newFilename = relativelink.substring(relativelink.lastIndexOf('/') + 1);
					String fullLink = UrlParser.parse(website, relativelink);
					System.out.println(fullLink);
					bus.download(fullLink);
					doc.getElementsByTag("img").get(img).attr("src", imageloc + "/" + newFilename);
					
				}

			}
			
			for (int input = 0; input < doc.getElementsByTag("input").size(); input++) {
				String relativelink = doc.getElementsByTag("input").get(input).attr("src");
				if (relativelink.endsWith("jpg")
						|| relativelink.endsWith("png")
						|| relativelink.endsWith("gif")
						|| relativelink.endsWith("jpeg")
						|| relativelink.endsWith("JPG")
						|| relativelink.endsWith("PNG")
						|| relativelink.endsWith("GIF")
						|| relativelink.endsWith("JPEG")) {

					newFilename = relativelink.substring(relativelink.lastIndexOf('/') + 1);
					String fullLink = UrlParser.parse(website, relativelink);
					System.out.println(fullLink);
					bus.download(fullLink);
					doc.getElementsByTag("input").get(input).attr("src", imageloc + "/" + newFilename);
					
				}

			}

			doc.getElementsByAttribute("class").removeAttr("class");
			doc.getElementsByAttribute("style").removeAttr("style");
			doc.getElementsByAttribute("script").removeAttr("script");

			
			// usefulCode.replaceAll("<script.*(\n.*?){0,}</script>", "");
			// usefulCode = usefulCode.replaceAll("href=\"../", "href=\"");

			usefulCode = doc.getElementById(elementID).html();
			usefulCode = usefulCode.replaceAll("&nbsp;&nbsp;", "");
			usefulCode = usefulCode.replaceAll("<p>&nbsp;</p>", "");
			usefulCode = usefulCode.replaceAll(oldStorage + oldStorage,
					oldStorage);
			uploader(storageFile.replaceAll("/UserFiles/Servers/Server_", "").replaceAll("/File/migration", ""), prod);
			return usefulCode;
		} catch (Exception e) {
			System.out.println("Invalid Site: Creating Blank Page");
			pageTitle = website;
			return "";
		}
	}

	public String getHTMLContent() {
		return htmlContent;
	}

	public void uploader(String serverNumber, String prod) {
		if(prod.equals("6")||prod.equals("5"))
		{
			bus.connect("155.254.144.7", "Gordon.Duff", "fr&4Eqe",
					"/Production "+prod+"/Server_" + serverNumber + "/File/migration");
			bus.upload();
			bus.connect("155.254.144.7", "Gordon.Duff", "fr&4Eqe",
					"/Production "+prod+"/Server_" + serverNumber + "/Image/migration");
			bus.upload();
		}
		else if(prod.equals("4")||prod.equals("3")||prod.equals("2")||prod.equals("1"))
		{
			bus.connect("ftp.sharpschool.com", "Gordon.Duff", "g$du77",
					"/Production "+prod+"/Server_" + serverNumber + "/File/migration");
			bus.upload();
			bus.connect("ftp.sharpschool.com", "Gordon.Duff", "g$du77",
					"/Production "+prod+"/Server_" + serverNumber + "/Image/migration");
			bus.upload();
		}
		else
			System.out.println("Invalid upload location");
	}

}