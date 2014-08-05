package automate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class InternalFilePage 
{
	//WIP DO NOT USE YET (NEED TO FIGURE OUT HOW TO DOWNLOAD FILE ON A PDF PAGE SINCE YOU CANT VIEW PAGE SOURCE)
	public InternalFilePage(WebDriver driver, String URL)
	{
		//go to the page creation page
				driver.findElement(By.linkText("Page Properties")).click();
				driver.findElement(By.linkText("Add New Page")).click();
		        driver.findElement(By.linkText("External Link Page")).click();
		        
		        WebDriver titGrabber = new HtmlUnitDriver();
		        titGrabber.get(URL);
		        
		        driver.findElements(By.tagName("input")).get(9).sendKeys(titGrabber.getTitle());
		        driver.findElements(By.tagName("input")).get(10).sendKeys(titGrabber.getTitle());
		        driver.findElements(By.tagName("input")).get(11).sendKeys(URL);//needs to send sharpschoolsite/file/migration/filename.file
		        driver.findElements(By.className("button")).get(0).click();
	}
}
