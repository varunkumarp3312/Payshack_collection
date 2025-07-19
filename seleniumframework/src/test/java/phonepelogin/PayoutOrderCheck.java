////package phonepelogin;
////
////import java.time.Duration;
////import java.time.LocalDateTime;
////
////import org.openqa.selenium.By;
////import org.openqa.selenium.WebDriver;
////import org.openqa.selenium.WebElement;
////import org.openqa.selenium.chrome.ChromeDriver;
////
////public class OrderIdCheck {
////
////	public static void main(String[] args) {
////		// TODO Auto-generated method stub
////		
////		WebDriver driver = new ChromeDriver();
////		
////
////		driver.get("https://dashboard.payshack.in/login");
////		driver.manage().window().maximize();
////		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
////
////		WebElement email = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div[1]/input"));
////		email.sendKeys("admin-user@indigate.in");
////		WebElement password = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div/input"));
////		password.sendKeys("User@12345");
////
////		driver.findElement(By.xpath("/html/body/div/div/div/div/button")).click();  //login
////		
////		driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/div[4]/a")).click();
////		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/header/div/div/div[1]/div[2]/div/button")).click();
////		
////		driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[5]/input")).sendKeys("6117545201");
////				
////		driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[7]/button[2]")).click();
////		
////		String status = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/table/tr[2]/td[9]/div")).getText();
////		
////		System.out.println(status);
////	}
////}
//
//



package phonepelogin;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PayoutOrderCheck {

    public static void main(String[] args) {
        // List of order IDs to check
        List<String> orderIds = List.of(
        		
        		    "00006894537601",
        		    "00006892321801",
        		    "00006892674201",
        		    "00006892672901"
        		);

        WebDriver driver = new ChromeDriver();
        
        try {
            // Login process
            driver.get("https://dashboard.payshack.in/login");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            WebElement email = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[1]/div/input"));
            email.sendKeys("admin-user@indigate.in");
            
            WebElement password = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[2]/div/div[1]/input"));
            password.sendKeys("User@12345");

            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/button")).click();  // login
            
            // Navigate to orders page
            driver.findElement(By.xpath("/html/body/div/div/div[1]/div[3]/div[3]/a")).click();
            System.out.println("fghjk");
            driver.findElement(By.xpath("/html/body/div/div/div[1]/div[3]/div[3]/div/a[2]")).click();
            
            
            Thread.sleep(1000);
//            
            driver.findElement(By.xpath("/html/body/div/div/div[1]/div[3]/div[3]/div[2]/a[2]")).click();
//            
//            // Create a list to store results
            List<String> results = new ArrayList<>();
            
            for (String orderId : orderIds) {
                try {
//                    // Open search dialog
                    driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[2]/header/div/div/div[1]/div[2]/div/button")).click();
//                    
//                    // Enter order ID and search
                    WebElement searchInput = driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[2]/div[7]/div/input"));
                    searchInput.clear();
                    searchInput.sendKeys(orderId);
                    
                    driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[2]/div[10]/button[2]")).click();
//                    
//                    // Wait for results to load
                    Thread.sleep(2000);
//                    
           // Get status
                    String status = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[11]/div")).getText();
//                    
              // Store result
                    results.add("Order ID: " + orderId + " - Status: " + status);
                    System.out.println("Order ID: " + orderId + " - Status: " + status);
                    
                } catch (Exception e) {
                    results.add("Order ID: " + orderId + " - Error: " + e.getMessage());
                    System.out.println("Failed to check Order ID: " + orderId);
                    e.printStackTrace();
                }
        }
        
        // Print all results at the end
        System.out.println("\nFinal Results:");
         for (String result : results) {
             System.out.println(result);
         }
        
  } catch (Exception e) {
      e.printStackTrace();
   } finally {
       driver.quit();
    }
  }
}




