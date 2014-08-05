package automate;

import java.io.IOException;
import org.dom4j.DocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class Setup {
	
	
	public Setup(String n, String i, String s, String p) throws IOException, DocumentException, InterruptedException
	{
		//initializes the driver that does everything
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().setSize(new Dimension(1920,1080));
		String username = "tian.zeng";
		String password = "1qa2ws3ed";
		String storageFile = "/UserFiles/Servers/Server_"+s+"/File/migration";
		String storageImage = "/UserFiles/Servers/Server_"+s+"/File/migration";
		
		driver.get(n);
		//driver.findElement(By.linkText("Login"));
		//driver.findElement(By.xpath("//a[@href='/gateway/Login.aspx?returnUrl=%2f']")).click();
		driver.findElement(By.className("admin_menu")).click();
		WebElement userQuery = driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtUsername"));
        userQuery.sendKeys(username);
        WebElement passwordQuery = driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtPassword"));
        passwordQuery.sendKeys(password);
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnLogin")).click();
		
		XML info = new XML();
		String oldSite = info.getURL(0).split("/")[2];
		for(int j = info.getIndex()-1; j >= 0; j--)
		{
			System.out.println(info.getURL(j));
			Thread.sleep(200);
			
			if(!info.getURL(j).contains(oldSite) && info.getURL(j).contains("http"))
			{
				new ExternalPage(driver, info.getURL(j));
				
				try
				{
					if(info.getWeight(j) > info.getWeight(j-1))
					{
						driver.navigate().back();
						driver.navigate().back();
					}
				}
				catch(Exception e){}
			}
			else
			{
				System.out.println("Retrieving old data");
				htmlRetriever old = new htmlRetriever(info.getURL(j), i, storageFile, storageImage, p);
				
				System.out.println("Old data retrieved. Implementing data to new site...");
				SharpSchoolImplementer newer = new SharpSchoolImplementer(old.getHTMLContent(), old.pageTitle);
				newer.run(driver);
				
				try
				{
					System.out.println("Current weight:"+info.getWeight(j)+"\nNext weight:"+info.getWeight(j-1));
					if(info.getWeight(j) == info.getWeight(j-1))
					{
						driver.navigate().back();
						driver.navigate().back();
						driver.navigate().back();
					}
					if(info.getWeight(j) > info.getWeight(j-1))
					{
						for(int k=0;k<=(info.getWeight(j)-info.getWeight(j-1));k++)
						{
							driver.navigate().back();
							driver.navigate().back();
							driver.navigate().back();
						}
					}
				}
				catch(Exception e)
				{
					
				}
			}
			
			System.out.println("Page complete");
		}
		driver.quit();
		System.out.println("Bot is done.");
	}
}
