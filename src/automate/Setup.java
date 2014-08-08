package automate;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Setup {
	
    public static void main (String [] asdf)
    {
                    WebDriver name = new FirefoxDriver();
                    name.get("http://rpschurchillhoover.ss5.sharpschool.com/cms/One.aspx?portalId=3084239&pageId=4332009&action=addTypedPage&parentId=4332009&pageType=Content+Space+Page");
                    
                    name.findElement(By.id("ctl00_ContentPlaceHolder1_txtUsername")).sendKeys("vincent.luong");
                    name.findElement(By.id("ctl00_ContentPlaceHolder1_txtPassword")).sendKeys("sharppass");
                    name.findElement(By.id("ctl00_ContentPlaceHolder1_btnLogin")).click();
                    
                    System.out.println(name.findElements(By.tagName("input")).size());
//                    for(int i=0;i<name.findElements(By.tagName("input")).size();i++)
//                    {
//                                    try
//                                    {
//                                                    name.findElements(By.tagName("input")).get(i).sendKeys(i+"");
//                                    }
//                                    catch(Exception e)
//                                    {
//                                                    
//                                    }
//                    }
    }

	public Setup(String n, String i, String s, String p) throws IOException, DocumentException, InterruptedException
	{
		//initializes the driver that does everything
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.manage().window().setSize(new Dimension(1920,1080));
		String username = "tian.zeng";
		String password = "1qa2ws3ed";
		String storageFile = "/UserFiles/Servers/Server_"+s+"/File/migration";
		String storageImage = "/UserFiles/Servers/Server_"+s+"/Image/migration";
		
		driver.get(n);
		//driver.findElement(By.linkText("Login"));
		//driver.findElement(By.xpath("//a[@href='/gateway/Login.aspx?returnUrl=%2f']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("admin_menu")));
		driver.findElement(By.className("admin_menu")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_txtUsername")));
		WebElement userQuery = driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtUsername"));
        userQuery.sendKeys(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_txtPassword")));
        WebElement passwordQuery = driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtPassword"));
        passwordQuery.sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_btnLogin")));
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnLogin")).click();
		
		XML info = new XML();
		String oldSite = info.getURL(0).split("/")[2];
		
		//array to keep track of how much to press back
		
		for(int j = info.getIndex()-1; j >= 0; j--)
		{
			System.out.println(info.getURL(j));
			try
			{
				System.out.println("Current weight:"+info.getWeight(j)+"\nNext weight:"+info.getWeight(j-1));
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.out.println("Current weight:"+info.getWeight(j));
			}
			
			if(!info.getURL(j).contains(oldSite) && info.getURL(j).contains("http"))
			{
				new ExternalPage(driver, info.getURL(j), wait);
			}
			else
			{
				System.out.println("Retrieving old data");
				htmlRetriever old = new htmlRetriever(info.getURL(j), i, storageFile, storageImage, p);
				
				System.out.println("Old data retrieved. Implementing data to new site...");
				SharpSchoolImplementer newer = new SharpSchoolImplementer(old.getHTMLContent(), old.pageTitle);
				newer.run(driver, wait);
			}
			
			//use the right number of backs
			try
			{
				for(int k=0;k<=(info.getWeight(j)-info.getWeight(j-1));k++)
				{
					driver.navigate().back();
					driver.navigate().back();
					System.out.println(driver.findElements(By.tagName("input")).size());
					while(driver.findElements(By.tagName("input")).size()>10)
					{
						driver.navigate().back();
					}
				}
			}
			catch(Exception e){}
			
			System.out.println("Page complete");
		}
		driver.quit();
		System.out.println("Bot is done.");
	}
}
