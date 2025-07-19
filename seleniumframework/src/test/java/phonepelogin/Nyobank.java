package phonepelogin;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Nyobank {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		WebDriver driver = new ChromeDriver();
		driver.get("https://nyobnk.com/login.aspx");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));
		
		driver.findElement(By.xpath("/html/body/form/div[3]/div/div[6]/div/div[2]/div/div/div/div[2]/input")).sendKeys("45511419");
		driver.findElement(By.xpath("/html/body/form/div[3]/div/div[6]/div/div[2]/div/div/div/div[3]/input")).sendKeys("9C17BAD1");
		
		driver.findElement(By.xpath("/html/body/form/div[3]/div/div[6]/div/div[2]/div/div/div/div[4]/input")).click();
		
		driver.findElement(By.xpath("//*[@id=\"sidebarnav\"]/li[4]/a")).click();
		
		
		
	

	}

}
