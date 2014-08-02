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
		// new
		new htmlRetriever("http://www.pinehill.k12.nj.us/index.php/glenn-school-main-menu","maincolumn_full","/UserFiles/Servers/Server_43687/File/migration/", "/UserFiles/Servers/Server_27285/Image/migration/");
	}

	public htmlRetriever(String url, String contentID, String storageFile,
			String storageImage) {
		// WebDriver drive = new HtmlUnitDriver();
		// WebDriver drive = new FirefoxDriver();
		htmlContent = pageSource(url, drive, contentID, storageFile,
				storageImage);
	}

	public String pageSource(String website, WebDriver oldStuff,
			String elementID, String storageFile, String storageImage) {

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
						|| relativelink.endsWith("jpeg")) {
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
						|| relativelink.endsWith("jpeg")) {

					newFilename = relativelink.substring(relativelink.lastIndexOf('/') + 1);
					String fullLink = UrlParser.parse(website, relativelink);
					System.out.println(fullLink);
					bus.download(fullLink);
					doc.getElementsByTag("img").get(img).attr("src", imageloc + "/" + newFilename);
					
				}

			}

			doc.getElementsByAttribute("class").removeAttr("class");
			doc.getElementsByAttribute("style").removeAttr("style");
			doc.getElementsMatchingOwnText(
					"(.*javascript.*| 0x0| 0x1| ContentType| 0x01| 898)")
					.remove();

			// usefulCode = usefulCode.replaceAll("class=\".*?\"", "");
			// usefulCode = usefulCode.replaceAll("style=\".*?\"", "");
			// usefulCode = usefulCode.replaceAll("&nbsp;&nbsp;", "");
			// usefulCode =
			// usefulCode.replaceAll("<script.*(\n.*?){0,}</script>", "");
			// usefulCode = usefulCode.replaceAll("href=\"../", "href=\"");
			// usefulCode = usefulCode.replaceAll("&amp;","&");

			usefulCode = doc.getElementById(elementID).html();
			usefulCode = usefulCode.replaceAll(oldStorage + oldStorage,
					oldStorage);
			uploader(storageFile.replaceAll("/UserFiles/Servers/Server_", "")
					.replaceAll("/File/migration", ""));
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

	public void uploader(String serverNumber) {
		bus.connect("155.254.144.7", "Gordon.Duff", "fr&4Eqe",
				"/Production 6/Server_" + serverNumber + "/File/migration");
		bus.upload();
		bus.connect("155.254.144.7", "Gordon.Duff", "fr&4Eqe",
				"/Production 6/Server_" + serverNumber + "/Image/migration");
		bus.upload();
	}

}