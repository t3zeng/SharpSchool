package automate;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SharpSchoolImplementer {
	
	 // The Firefox driver supports javascript 
    String title;
    String in;
    
    public SharpSchoolImplementer(String input, String titleName)
    {
        // Go to the sharpschool login page
        title=titleName;
        in = input;
    }
    
    
    public void run(WebDriver driver) throws InterruptedException
    {
        
    	WebDriverWait wait = new WebDriverWait(driver, 10);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Page Properties")));
        //navigate to the creation page
        driver.findElement(By.linkText("Page Properties")).click();
        driver.findElement(By.linkText("Add New Page")).click();
        driver.findElement(By.linkText("Content Space Page")).click();       

        //Puts in the information
        
        //WebElement titleQuery = driver.findElement(By.id("ctl00_ContentPlaceHolder1_ctl03_txtTitle"));
        Thread.sleep(1000);
        WebElement titleQuery = driver.findElements(By.tagName("input")).get(8);
        titleQuery.sendKeys(title);
        try
        {
        	driver.findElements(By.tagName("input")).get(14).click(); //12,14,15
        	driver.findElement(By.className("reMode_html")).click();
        }
        catch (StaleElementReferenceException|NoSuchElementException|UnhandledAlertException e)
        {
	        	driver.navigate().refresh();
	        	Alert alert = driver.switchTo().alert(); 
	            alert.accept();
	            Thread.sleep(2000);
	            driver.switchTo().parentFrame();
	        	titleQuery = driver.findElements(By.tagName("input")).get(8);
	        	titleQuery.clear();
	        	titleQuery.sendKeys("(Duplicate Code: "+(int)(Math.random()*9999+1)+")");
	        	driver.findElement(By.xpath("//img[@title='Generate Name']"));
	            driver.findElements(By.tagName("input")).get(14).click(); //12,14,15
	            Thread.sleep(1000);
	            driver.findElement(By.className("reMode_html")).click();
        }
        
        
        //WebElement contentQuery = driver.switchTo().frame(driver.findElement(By.xpath(".//iframe[@frameborder='0']"))).findElement(By.tagName("textarea"));
        //WebElement contentQuery = driver.findElement(By.tagName("textarea"));
        //contentQuery.sendKeys(input);
        setValue(driver.switchTo().frame(driver.findElements(By.tagName("iframe")).get(1)).findElement(By.tagName("textarea")),in,driver);
        driver.switchTo().parentFrame();
        driver.findElement(By.className("reMode_design")).click();
        driver.findElement(By.className("FormatStripper")).click();
        driver.findElements(By.className("reButton_text")).get(4).click();
        driver.findElements(By.className("button")).get(1).click();
        driver.findElement(By.xpath("//input[@value='Yes']")).click();
        
        
        //quits page
        //driver.quit();
    }
    
    
    private static void setValue(WebElement element, String value, WebDriver driver) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].value = arguments[1]", element, value);
    }
    
    
    
}