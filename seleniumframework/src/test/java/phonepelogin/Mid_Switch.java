package phonepelogin;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Mid_Switch {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		WebDriver driver = new ChromeDriver();
		 LocalDateTime now = LocalDateTime.now();

		driver.get("https://dashboard.payshack.in/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement email = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div[1]/input"));
		email.sendKeys("admin-user@indigate.in");
		WebElement password = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div/input"));
		password.sendKeys("User@12345");

		driver.findElement(By.xpath("/html/body/div/div/div/div/button")).click();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div[1]/button")).click();
		Thread.sleep(3000);

		String volume = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[2]/div[4]/div[2]")).getText();//fetching the volume



		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        System.out.println("Current volume:" + volume);
        System.out.println("Timestamp: " + formattedDateTime);

//		System.out.println("current volume : "+ volume);

        driver.findElement(By.xpath("/html/body/div/div/div[1]/div[3]/a")).click();

        for (int i = 0; i < 5; i++) {
            driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/div/button[7]")).click();
            Thread.sleep(500); // Optional: small delay between clicks (500ms)
        }

        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/table/tbody/tr[10]/td[6]")).click(); //selecting the internal client testing

        driver.findElement(By.xpath("/html/body/div[2]/div[3]/p/div[2]/button")).click();

        WebElement dropdown = driver.findElement(By.tagName("select"));
        Select select = new Select(dropdown);
        select.selectByVisibleText("AIRPAY-PAYIN");
        select.selectByValue("6e3522cd-b85e-4dfe-ba60-18b1ec40c01a");


        WebElement mid=driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[2]/input[1]"));
        mid.clear();
        mid.sendKeys("338898");
        Thread.sleep(2000);

        WebElement secretkey = driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[1]/input"));
        secretkey.clear();
        secretkey.sendKeys("ySnHDPaA2jx63tXg");

        Thread.sleep(2000);
        WebElement username = driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[2]/input[3]"));
        username.clear();
        username.sendKeys("KaQ4Z4C9mS");

        Thread.sleep(2000);
        WebElement pwd = driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[2]/input[4]"));
        pwd.clear();
        pwd.sendKeys("gpJgySq7");
        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div[3]/div[3]/button")).click();
        Thread.sleep(2000);
        driver.navigate().back();//navigating to the back


        driver.findElement(By.xpath("/html/body/div/div/div[1]/div[3]/a")).click();	//clicking the client


        for (int i = 0; i < 6; i++) {
            driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/div/button[7]")).click();
            Thread.sleep(500); // Optional: small delay between clicks (500ms)
        }


       driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/table/tbody/tr[6]/td[6]/div")).click();

       driver.findElement(By.xpath("//*[@id=\"demo-popup-popover\"]/div[3]/p/div[2]/button")).click();







        driver.findElement(By.xpath("/html/body/div[2]/div[3]/p/div[2]/button")).click();

        WebElement dropdown1 = driver.findElement(By.tagName("select"));
        Select select1 = new Select(dropdown1);
        select1.selectByVisibleText("AIRPAY-PAYIN");
        select1.selectByValue("6e3522cd-b85e-4dfe-ba60-18b1ec40c01a");

        Thread.sleep(2000);

        WebElement mid1=driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[2]/input[1]"));
        mid1.clear();
        mid1.sendKeys("338898");
        Thread.sleep(2000);
        WebElement secretkey1 = driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[1]/input"));
        secretkey1.clear();
        secretkey1.sendKeys("ySnHDPaA2jx63tXg");
        Thread.sleep(2000);
        WebElement username1 = driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[2]/input[3]"));
        username1.clear();
        username1.sendKeys("KaQ4Z4C9mS");
        Thread.sleep(2000);
        WebElement pwd1 = driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[2]/input[4]"));
        pwd1.clear();
        pwd1.sendKeys("gpJgySq7");
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[3]/div[3]/button")).click();


	}
	}