package automate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
public class ExternalPage 
{
	
	
	public ExternalPage(WebDriver driver, String URL) throws InterruptedException
	{
		//go to the page creation page
				Thread.sleep(1000);
				driver.findElement(By.linkText("Page Properties")).click();
				Thread.sleep(1000);
				driver.findElement(By.linkText("Add New Page")).click();
				Thread.sleep(1000);
		        driver.findElement(By.linkText("External Link Page")).click();
		        
		        WebDriver titGrabber = new HtmlUnitDriver();
		        titGrabber.get(URL);
		        
		        Thread.sleep(1000);
		        driver.findElements(By.tagName("input")).get(9).sendKeys(titGrabber.getTitle());
		        driver.findElements(By.tagName("input")).get(10).sendKeys(titGrabber.getTitle());
		        driver.findElements(By.tagName("input")).get(11).sendKeys(URL);
		        driver.findElements(By.className("button")).get(0).click();
	}
}
