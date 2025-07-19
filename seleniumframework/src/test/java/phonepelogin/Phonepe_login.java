package phonepelogin;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Phonepe_login {

	public static void main(String [] args) throws InterruptedException {

		WebDriver driver = new ChromeDriver();
		driver.get("https://merchant.cashfree.com/auth/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement email = driver.findElement(By.id("username"));
		email.sendKeys("appytimes01@gmail.com");
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("Assure@2025");

		driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div[1]/div/div[2]/div/div/div[2]/div[1]/form/button")).click();

	}

}
