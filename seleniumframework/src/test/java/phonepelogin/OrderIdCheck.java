//
//package phonepelogin;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//
//public class OrderIdCheck {
//
//    public static void main(String[] args) {
//        // List of order IDs to check
//    	List<String> orderIds = List.of(
//    			"6247583001",
//    			"6187456101"
//    		);
//        WebDriver driver = new ChromeDriver();
//        try {
//            // Login process
//            driver.get("https://dashboard.payshack.in/login");
//            driver.manage().window().maximize();
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//
//            WebElement email = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[1]/div/input"));
//            email.sendKeys("daniil.s@monetix.pro");
//            
//            WebElement password = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[2]/div/div[1]/input"));
//            password.sendKeys("MONEclt@123");
//
//            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/button")).click();  // login button
//            
//            Thread.sleep(500);
//            // Navigate to orders page
//            driver.findElement(By.xpath("/html/body/div/div/div[1]/div[3]/div[3]/a")).click();
//            
//            // Create a list to store results
//            List<String> results = new ArrayList<>();
//            
//            for (String orderId : orderIds) {
//                try {
//                    // Open search dialog
//                    driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[2]/header/div/div/div[1]/div[3]/div/button")).click(); //filter button in the payyin
//                    
//                    // Enter order ID and search
////                    WebElement searchInput = driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[2]/div[7]/div/input")); //enter order id
//                   
//                    WebElement searchInput = driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[2]/div[5]/div/input")); //enter order id
//                    searchInput.clear();
//                    searchInput.sendKeys(orderId);
//                    
//                    driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[2]/div[7]/button[2]")).click();
//                    
//                    // Wait for results to load
//                    Thread.sleep(5000);
//                    
//                    // Get status and date
//                    String status = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[8]/div")).getText();
//                    String dateOfTransaction = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[2]")).getText();
//                    
////                    String clientname = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr[1]/td[4]")).getText();
//                    
//                    
//                     String utr=driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[7]")).getText();
//                     String amount = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[8]")).getText();
//                     
//                    
//                    
//                    
//                    // Store result
//                    String result = String.format("Order ID: %-20s | Date: %-15s | Status: %s", 
//                            orderId, dateOfTransaction, status);
//                    results.add(result);
//                    
//                    // Print current result
//                    System.out.println(result);
//                    
//                } catch (Exception e) {
//                    String errorResult = "Order ID: " + orderId + " - Error: " + e.getMessage();
//                    results.add(errorResult);
//                    System.out.println(errorResult);
//                    e.printStackTrace();
//                }
//            }
//            
//            // Print all results at the end
//            System.out.println("\nFinal Results:");
//            for (String result : results) {
//                System.out.println(result);
//            }
//        
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            driver.quit();
//        }
//    }
//}


package phonepelogin;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class OrderIdCheck {

    public static void main(String[] args) {
        // List of order IDs to check
    	List<String> orderIds = List.of(
                "6550561102", "6576400502", "6565877402", "6566029702", "6567305402",
                "6566044702", "6566637202", "6566044902", "6566000802", "6565734302",
                "6566010502", "6567197002", "6565818302", "6566635302", "6566041902",
                "6566305502", "6567692102", "6567940502", "6566304102", "6567390302",
                "6566994502", "6565979502", "6567943502", "6566037602", "6567011502",
                "6565338202", "6565340602", "6567146302", "6567001902", "6566955002",
                "6566961902", "6567310002", "6565831102", "6566038602", "6568196802",
                "6565802602", "6566935802", "6567138102", "6566431002", "6566960302",
                "6567399502", "6566404502", "6567188502", "6567138602", "6565738902",
                "6566951002", "6595679702", "6569921602", "6571349802", "6577719502",
                "6577722202", "6569807602", "6569809602", "6601877602", "6603825502",
                "6602574302", "6614253902", "6620110102", "6621844102", "6621204902",
                "6620426302", "6620474302", "6620615402", "6620081402", "6620084702",
                "6620764902", "6620082702", "6620626502", "6621049402", "6639149902",
                "6648595002", "6637646202", "6637264702", "6638992602", "6639081502",
                "6637501202", "6638745402", "6637624802", "6637257202", "6637755302",
                "6652029802", "6637701702", "6637093402", "6631668902", "6638990102",
                "6638963202", "6637678402", "6637181202", "6637753502", "6622743202",
                "6637529002", "6637664102", "6636848702", "6637400402", "6655050402",
                "6638170902", "6630041702", "6637498602", "6637626802", "6637662102",
                "6637674102", "6636538902", "6637950102", "6637540002", "6636925202",
                "6637279202", "6637591402", "6638566002", "6660475402", "6660540602",
                "6653477102", "6653321702", "6653015102", "6664110702", "6653036602",
                "6653375702", "6653876002", "6653695802", "6654058802", "6653309902",
                "6668846002", "6666743102", "6669418502", "6667876702", "6669006802",
                "6666732502", "6685988302", "6685336802", "6685246402", "6686001402",
                "6685124902", "6685411502", "6686035202", "6685525102", "6685777802",
                "6715957202", "6705200602", "6702913002", "6702997202", "6703135102",
                "6702887202", "6702907002", "6704566802", "6703170402", "6722048202",
                "6722896602", "6718766502", "6703572402", "6723370302", "6723327902",
                "6719378102", "6703414702", "6719255202", "6722842202", "6718901802",
                "6723489302", "6721509602", "6720735902", "6715932402", "6719348702",
                "6720744902", "6720024902", "6721036202", "6718360702", "6719646902",
                "6722870502", "6719337602", "6720384302", "6739185001", "6721443902",
                "6723016302", "6702949902", "6739700101", "6721506702", "6703241402",
                "6720393202", "6702932002", "6702909302", "6721049502", "6723225302",
                "6718757902", "6723477802", "6719169802", "6719838102", "6718127502",
                "6745808701", "6739037401", "6739979601", "6775452501", "6776792801",
                "6792456601", "6792653301", "6797184801", "6793859301", "6794028301",
                "6571301502", "6569383902", "6569390202", "6570004302", "6569141702",
                "6570066802", "6569714202", "6611276002", "6612225802", "6613623002",
                "6594459002", "6614503002", "6616368802", "6629917902", "6634615102",
                "6613781602", "6619739702", "6636241702", "6636378702", "6650671202",
                "6652123202", "6630051002", "6633182502", "6657435802", "6654058602",
                "6653616602", "6654210202", "6663351302", "6653316502", "6653654702",
                "6654135002", "6646295302", "6646352802", "6653979002", "6676098102",
                "6667865502", "6669722802", "6667909602", "6668456902", "6668547602",
                "6682545402", "6669034402", "6666653002", "6668800802", "6666944302",
                "6659614602", "6685787202", "6685484702", "6685406202", "6685525802",
                "6684493302", "6685244902", "6685517102", "6686033002", "6684415802",
                "6685252602", "6685474702", "6685507502", "6685753902", "6708882002",
                "6710488402", "6711040202", "6714130902", "6698535002", "6702157002",
                "6702789602", "6702825702", "6703068902", "6702868602", "6704862702",
                "6698872202", "6702344002", "6707334302", "6707329902", "6719839302",
                "6704589202", "6722152202", "6719152002", "6720402502", "6719256002",
                "6722735302", "6717663702", "6704675802", "6720976902", "6720576902",
                "6707105402", "6707696002", "6722979602", "6722508102", "6711966702",
                "6704209602", "6712443102", "6723388402", "6712649702", "6704644302",
                "6712152002", "6722866002", "6722858002", "6707430202", "6719647102",
                "6720384702", "6712147602", "6707049802", "6722832902", "6723090802",
                "6703354802", "6705137402", "6718902502", "6707453702", "6720917402",
                "6702349502", "6722883202", "6712146802", "6703462102", "6723490202",
                "6721593302", "6721221702", "6721272302", "6721131402", "6711474702",
                "6722151602", "6743180101", "6727522302", "6740989701", "6725055402",
                "6724028402", "6761218301", "6763766801", "6746911401", "6772748701",
                "6787156901", "6793733601", "6807940201", "6814255801", "6816825901",
                "6680670702", "6695627902", "6718905002", "6718896802", "6719643202",
                "6719253602", "6719373802", "6719643002", "6721440102", "6720848002",
                "6720016502", "6719833902", "6721724902", "6721035702", "6720391702",
                "6723327002", "6722670702", "6720744202", "6720400902", "6722953902",
                "6722657402", "6721907902", "6722945502", "6723093102", "6723102702",
                "6720398702", "6720399402", "6720849902", "6724100902", "6724095002",
                "6724042202", "6739991001", "6740054701", "6739662401", "6741427701",
                "6770018301", "6771129701", "6785494401", "6787153301", "6796018601",
                "6816509101", "6816747601", "6805965201", "6816597701", "6837173402"
            );
        WebDriver driver = new ChromeDriver();
        try {
            // Login process
            driver.get("https://dashboard.payshack.in/login");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            WebElement email = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[1]/div/input"));
            email.sendKeys("daniil.s@monetix.pro");
            
            WebElement password = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[2]/div/div[1]/input"));
            password.sendKeys("MONEclt@123");

            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/button")).click();  // login button
            
            Thread.sleep(500);
            // Navigate to orders page
            driver.findElement(By.xpath("/html/body/div/div/div[1]/div[3]/div[3]/a")).click();
            
            // Create a list to store results
            List<String> results = new ArrayList<>();
            
            for (String orderId : orderIds) {
                try {
                    // Open search dialog
                    driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[2]/header/div/div/div[1]/div[3]/div/button")).click(); //filter button in the payyin
                    
                    // Enter order ID and search
                    WebElement searchInput = driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[2]/div[5]/div/input")); //enter order id
                    searchInput.clear();
                    searchInput.sendKeys(orderId);
                    
                    driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[2]/div[7]/button[2]")).click();
                    
                    // Wait for results to load
                    Thread.sleep(5000);
                    
                    // Get status, date, UTR, and amount
                    String status = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[8]/div")).getText();
                    String dateOfTransaction = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[2]")).getText();
                    String utr = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[6]")).getText();
                    String amount = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[7]")).getText(); // Adjusted index to td[9] for amount
                    
                    // Store result with UTR and Amount
                    String result = String.format("Order ID: %-12s | Date: %-12s | Status: %-10s | UTR: %-15s | Amount: %s", 
                            orderId, dateOfTransaction, status, utr, amount);
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