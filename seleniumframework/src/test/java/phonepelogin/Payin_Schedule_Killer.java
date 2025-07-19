package phonepelogin;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Payin_Schedule_Killer {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        
        try {
            // Configure browser settings
            driver.get("https://dashboard.payshack.in/login");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

//            // Login process
            WebElement email = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[1]/div/input"));
            email.sendKeys("admin-user@indigate.in");
            
            WebElement password = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[2]/div/div[1]/input"));
            password.sendKeys("User@12345");

            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/button")).click();
//            
//            // Create explicit wait for the menu item
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement menuItem = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div/div/div[1]/div[3]/div[12]/a")));
            
//            // Scroll to the element
            ((JavascriptExecutor)driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
                menuItem);
            
//            // Wait briefly for scroll to complete
            Thread.sleep(500);
//            
//            // Click the element
            menuItem.click();
//            
//            
            Thread.sleep(5000);
            
            WebElement activescheduler = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr[75]/td[7]/div/button[2]"));
            activescheduler.click();
            
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
//            // Close the browser
            driver.quit();
        }
    }
}