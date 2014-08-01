package automate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class ExternalPage 
{
	public static void main(String[] args)
	{
		WebDriver driver = new FirefoxDriver();
		driver.get("http://cromwellwis.ss6.sharpschool.com/cms/One.aspx?portalId=71751&pageId=734717&action=addextlinkpage&parentId=734717");
		
		WebElement userQuery = driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtUsername"));
        userQuery.sendKeys("tian.zeng");
        WebElement passwordQuery = driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtPassword"));
        passwordQuery.sendKeys("1qa2ws3ed");
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnLogin")).click();
		
		new ExternalPage(driver, "http://www.google.ca");
	}
	
	public ExternalPage(WebDriver driver, String URL)
	{
		//go to the page creation page
				driver.findElement(By.linkText("Page Properties")).click();
				driver.findElement(By.linkText("Add New Page")).click();
		        driver.findElement(By.linkText("External Link Page")).click();
		        
		        WebDriver titGrabber = new HtmlUnitDriver();
		        titGrabber.get(URL);
		        
		        driver.findElements(By.tagName("input")).get(9).sendKeys(titGrabber.getTitle());
		        driver.findElements(By.tagName("input")).get(10).sendKeys(titGrabber.getTitle());
		        driver.findElements(By.tagName("input")).get(11).sendKeys(URL);
		        driver.findElements(By.className("button")).get(0).click();
	}
}
