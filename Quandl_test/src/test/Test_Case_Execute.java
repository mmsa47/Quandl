package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Test_Case_Execute {
	
	static WebDriver driver;
	public static WebDriver launch_FireFoxbrowser(String url){
 		ProfilesIni profile = new ProfilesIni();
 		FirefoxProfile myprofile = profile.getProfile("AnyProfileTesterWants");
	    driver = new FirefoxDriver(myprofile);
	    Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss") ;
 		System.out.println("Web automation Test starts at: " + dateFormat.format(date));
		driver.manage().window().maximize();
		driver.get(url);
		return driver;
	}

	public static WebDriver close_firefoxDriver(WebDriver driver) throws InterruptedException
 	{
 		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss") ;
 		System.out.println("Web automation Test ends at: " + dateFormat.format(date));
 		System.out.println("Closing the browser in three seconds.");
		Thread.sleep(3000);
		driver.quit();
		return driver;
 	}
	
	public static void wait_and_click(WebDriver driver, WebElement element){
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	}
	
	
	@BeforeClass
	public static void launch_URL() throws InterruptedException{
		String url ="http://www.quandl.com";
		launch_FireFoxbrowser(url);
		System.out.println(url +": URL is launched.");
		//waiting for few seconds 
		Thread.sleep(5000);
	}
	@Test
	public void logo_Quandl_Test(){
		System.out.println("Logo and URL test.");
		int visbile_logo_coutner = 0;
		WebElement active_Logo = null;
		String avtive_Logo_URL = null;
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		List<WebElement> all_Logos =driver.findElements(By.xpath("//*[@class='quandl-logo qa-quandl-logo primary-nav__nav-block ember-view active']"));
		int x = all_Logos.size();
		System.out.println("Number of available logo to display = "+ x);
		for(int i=0;i<all_Logos.size();i++)
		{
			if(all_Logos.get(i).isDisplayed()){
				active_Logo = all_Logos.get(i);
				visbile_logo_coutner++;	
			}
		}
		System.out.println("Number of Displayed Logos are  = "+ visbile_logo_coutner);
		avtive_Logo_URL = active_Logo.getAttribute("href");
		System.out.println("Quandl Logo URL is: "+ avtive_Logo_URL);
		Assert.assertEquals(avtive_Logo_URL, "https://www.quandl.com/");
	}

	@Test
	public void footer_career_Test(){
		System.out.println("Careers  in footer test");
		WebElement career_link = driver.findElement(By.linkText("Careers"));
		boolean career_link_display = career_link.isDisplayed();
		boolean career_link_clickable = career_link.isEnabled();
		String className = career_link.getAttribute("class");
		String career_URL=career_link.getAttribute("href");
		System.out.println("ClassName: "+ className+"\nURL: "+career_URL);
		if(className.contains("footer")&&career_link_clickable&&career_link_display){
			Assert.assertTrue(true);
			System.out.println("Career link is in footer class, It is displaye and enabled.");
		}else{Assert.assertFalse(true);}
	}
	
	@Test
	public void sign_up_Quandl(){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Sing up Test");
		wait_and_click(driver, driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/top-bar/nav[1]/div[3]/a[3]")));
		wait_and_click(driver, driver.findElement(By.xpath("/html/body/div[2]/div/modal-dialog/section/footer/a")));
		System.out.println("Sing up page to a new account is visible.\nTest Sign Up");
		WebElement username, pwd, confirm_pwd, email;
		username = driver.findElement(By.id("ember1301"));
		email = driver.findElement(By.id("ember1303"));
		pwd = driver.findElement(By.id("ember1305"));
		confirm_pwd = driver.findElement(By.id("ember1308"));
		/* userName and Email needs to be re-entered if already used
		 * Please update the that information
		 */
		username.sendKeys("Fake_id1234");
		email.sendKeys("fake_email_1234@gmail.com");
		pwd.sendKeys("Test1234");
		confirm_pwd.sendKeys("Test1234");
		
		wait_and_click(driver, driver.findElement(By.id("ember1309")));
		System.out.println("Sing up Mock Data is entered. ");
		try{
			WebElement successMessage = driver.findElement(By.xpath("/html/body/div[2]/div/modal-dialog/section/section"));
			if(successMessage.isDisplayed()){Assert.assertTrue(true);}
		}catch(Exception e){
			System.out.println("Please Enter an update username and email address and try again");
		}
	}
	
	@AfterClass
	public static void postTest() throws InterruptedException{
		driver = close_firefoxDriver(driver);
	}

}
