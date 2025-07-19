package phonepelogin;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Chargeback {

    public static void main(String[] args) {
        // List of order IDs to check
        List<String> orderIds = List.of(

        		"6309712402",
        		"6378118902",
        		"6094106801"

            );
        WebDriver driver = new ChromeDriver();
        try {
            // Login process
            driver.get("https://dashboard.payshack.in/login");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            WebElement email = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div[1]/input"));
            email.sendKeys("admin-user@indigate.in");
            
            WebElement password = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div/input"));
            password.sendKeys("User@12345");

            driver.findElement(By.xpath("/html/body/div/div/div/div/button")).click();  // login
            
            // Navigate to orders page
            driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/div[4]/a")).click();
            
            // Create a list to store results
            List<String> results = new ArrayList<>();
            
            for (String orderId : orderIds) {
                try {
                    // Open search dialog
                    driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/header/div/div/div[1]/div[2]/div/button")).click();
                    
                    // Enter order ID and search
                    WebElement searchInput = driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[5]/input"));
                    searchInput.clear();
                    searchInput.sendKeys(orderId);
                    
                    driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[7]/button[2]")).click();
                    
                    // Wait for results to load
                    Thread.sleep(2000);
                    
                    // Get all details
                    String status = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/table/tr[2]/td[9]/div")).getText();
                    String dateOfTransaction = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/table/tr[2]/td[2]")).getText();
                    String clientname = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/table/tr[2]/td[4]")).getText();
                    String trxnid = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/table/tr[2]/td[5]")).getText();
                    String amount = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/table/tr[2]/td[8]")).getText();
                    
                    // Store result with all details
                    String result = String.format("Order ID: %-15s | Date: %-15s | Client: %-20s | Trxn ID: %-15s | Amount: %-10s | Status: %s", 
                            orderId, dateOfTransaction, clientname, trxnid, amount, status);
                    results.add(result);
                    
                    // Print current result
                    System.out.println(result);
                    
                } catch (Exception e) {
                    String errorResult = "Order ID: " + orderId + " - Error: " + e.getMessage();
                    results.add(errorResult);
                    System.out.println(errorResult);
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