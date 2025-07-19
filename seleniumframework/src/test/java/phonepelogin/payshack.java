package phonepelogin;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class payshack {

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

        driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/div[6]/a")).click();

        for (int i = 0; i < 5; i++) {
            driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/div/button[7]")).click();
            Thread.sleep(500); // Optional: small delay between clicks (500ms)
        }

        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/table/tbody/tr[5]/td[4]/button")).click(); //selecting the internal client testing


        WebElement dropdown = driver.findElement(By.id("sel"));  //dropdown
        Select select = new Select(dropdown);
        select.selectByVisibleText("AIRPAY-PAYIN");  //selecting the airpay

//        driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div[1]/span/button[2]")).click();  //disable button

        driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div[1]/span/button[1]")).click();//enable button

        System.out.println("internal testing is disabled from the airpay");

        Thread.sleep(3000);

        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/div/button[7]")).click();   //clicking to next page


        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/table/tbody/tr[6]/td[4]")).click();


        WebElement dropdown1 = driver.findElement(By.id("sel"));  //dropdown
        Select select1 = new Select(dropdown1);
        select1.selectByVisibleText("AIRPAY-PAYIN");  //selecting the airpa

//        driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div[1]/span/button[2]")).click();//disable buttoon

        driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div[1]/span/button[1]")).click();//enable button

        System.out.println("client testing is disabled from the airpay");





//        driver.findElement(By.xpath("//*[@id=\"sel\"]")).click();
//

	}
	}


