package automate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class ExternalPage 
{
	
	
	public ExternalPage(WebDriver driver, String URL, WebDriverWait wait) throws InterruptedException
	{
		//go to the page creation page
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Page Properties")));
				driver.findElement(By.linkText("Page Properties")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add New Page")));
				driver.findElement(By.linkText("Add New Page")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("External Link Page")));
		        driver.findElement(By.linkText("External Link Page")).click();
		        
		        WebDriver titGrabber = new HtmlUnitDriver();
		        titGrabber.get(URL);
		        
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@size='60']")));
		        driver.findElement(By.xpath("//input[@size='60']")).sendKeys(titGrabber.getTitle());//9
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@maxlength='100']")));
		        driver.findElement(By.xpath("//input[@maxlength='100']")).sendKeys(titGrabber.getTitle());//10
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@size='50']")));
		        driver.findElement(By.xpath("//input[@size='50']")).sendKeys(URL);//11
		        driver.findElements(By.className("button")).get(0).click();
	}
}
