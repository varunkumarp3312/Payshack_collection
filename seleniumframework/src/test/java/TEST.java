import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TEST {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        
        try {
//             Configure browser settings
            driver.get("https://dashboard.payshack.in/login");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

//             Login process
            WebElement email = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[1]/div/input"));
            email.sendKeys("admin-user@indigate.in");
            
            WebElement password = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[2]/div/div[1]/input"));
            password.sendKeys("User@12345");

            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/button")).click();
            
//             Create explicit wait for the menu item
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement menuItem = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div/div/div[1]/div[3]/div[12]/a")));
            
//             Scroll to the element
            ((JavascriptExecutor)driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
                menuItem);
            
//             Wait briefly for scroll to complete
            Thread.sleep(500);
            
//             Click the element
            menuItem.click();
            
//             Wait for page to load
            Thread.sleep(5000);
            
//             Scroll to bottom of the table container
            WebElement tableContainer = driver.findElement(By.xpath("//div[contains(@style, 'height: 76vh')]"));
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollTo(0, arguments[0].scrollHeight)", tableContainer);
            
//             Find all rows in the table
            java.util.List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
            
            boolean buttonFound = false;
            
//             Iterate through rows from bottom to top
            for (int i = rows.size() - 1; i >= 0; i--) {
                WebElement row = rows.get(i);
                
//                 Check if this row has a second button in Actions column
                try {
                    WebElement button2 = row.findElement(By.xpath(".//td[7]//button[2]"));
                    
//                     Scroll the button into view
                    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button2);
                    
//                     Highlight the button
                    ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", button2);
                    
//                     Click the button
                    button2.click();
                    System.out.println("Successfully clicked button 2 in row " + (i+1));
                    buttonFound = true;
                    
//                     Wait for action to complete
                    Thread.sleep(2000);
                    break;
                } catch (Exception e) {
//                     Button not found in this row, continue searching
                    continue;
                }
            }
            
            if (!buttonFound) {
                System.out.println("No button 2 (Deactivate) found in any row's Actions column");
            }
            
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
//             Close the browser
        	driver.quit();
        }
    }
}



//import java.time.Duration;
//import java.util.List;
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class TEST {
//
//    public static void main(String[] args) {
//        WebDriver driver = new ChromeDriver();
//        
//        try {
//            // Configure browser settings
//            driver.get("https://dashboard.payshack.in/login");
//            driver.manage().window().maximize();
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//
//            // Login process
//            WebElement email = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[1]/div/input"));
//            email.sendKeys("admin-user@indigate.in");
//            
//            WebElement password = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[2]/div/div[1]/input"));
//            password.sendKeys("User@12345");
//
//            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/button")).click();
//            
//            // Navigate to scheduler page
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//            WebElement menuItem = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("/html/body/div/div/div[1]/div[3]/div[12]/a")));
//            
//            ((JavascriptExecutor)driver).executeScript(
//                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
//                menuItem);
//            Thread.sleep(500);
//            menuItem.click();
//            
//            // Wait for scheduler page to load
//            Thread.sleep(5000);
//            
//            // Scroll to bottom of the table container
//            WebElement tableContainer = driver.findElement(By.xpath("//div[contains(@style, 'height: 76vh')]"));
//            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollTo(0, arguments[0].scrollHeight)", tableContainer);
//            Thread.sleep(1000);
//            
//            // Find all rows in the table
//            List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
//            System.out.println("Found " + rows.size() + " rows in the table");
//            
//            int clickedCount = 0;
//            
//            // Iterate through all rows to find active button2 elements
//            for (int i = 0; i < rows.size(); i++) {
//                WebElement row = rows.get(i);
//                
//                try {
//                    // Find button2 in the Actions column (7th td)
//                    WebElement button2 = row.findElement(By.xpath(".//td[7]//button[2]"));
//                    
//                    // Check if button is displayed and enabled
//                    if (button2.isDisplayed() && button2.isEnabled()) {
//                        // Scroll to the button
//                        ((JavascriptExecutor)driver).executeScript(
//                            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
//                            button2);
//                        
//                        // Highlight the button (visual feedback)
//                        ((JavascriptExecutor)driver).executeScript(
//                            "arguments[0].style.border='3px solid red';", 
//                            button2);
//                        
//                        Thread.sleep(300);
//                        
//                        // Click using JavaScript to avoid interception
//                        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", button2);
//                        clickedCount++;
//                        System.out.println("Clicked button 2 in row " + (i+1));
//                        
//                        // Wait for action to complete
//                        Thread.sleep(2000);
//                        
//                        // Refresh rows after click to avoid stale elements
//                        rows = driver.findElements(By.xpath("//table/tbody/tr"));
//                    }
//                } catch (Exception e) {
//                    // Button not found or not clickable in this row
//                    continue;
//                }
//            }
//            
//            System.out.println("Total buttons clicked: " + clickedCount);
//            
//            if (clickedCount == 0) {
//                System.out.println("No active button 2 elements found in the table");
//            }
//            
//        } catch (Exception e) {
//            System.err.println("Error occurred: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            System.out.println("Execution completed");
//            driver.quit();
//        }
//    }
//}