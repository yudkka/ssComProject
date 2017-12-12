package ss.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SSComTest {
    private WebDriver driver;

    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        // Open chrome browser
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        // Maximize window
        driver.manage().window().maximize();
        // Set timeouts
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

    }

    @BeforeMethod(alwaysRun = true)
    public void openMainPage(){
        //Open ss.com page
        driver.get("https://www.ss.com/");
    }


    @AfterClass (alwaysRun = true)
    public void tearDown(){
        // close Browser
        driver.quit();
    }

    @Test
    public void mainPageTest(){
        // check that main page is opened
        String url = driver.getCurrentUrl();
        assertEquals(url, "https://www.ss.com/", "ss.com page opened fail");
    }

    @Test
    public void testRealEstateCategory() throws InterruptedException {
        // Find real estate category link
        WebElement link = driver.findElement(By.cssSelector("h2 > a[href*='real-estate']"));
        // Click link
        link.click();
        //wait page load for 2sec
        Thread.sleep(2000);
        //Check that correct page is opened
        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("real-estate/"), "Real Estate page is not opened");
    }


    @Test
    public void searchTodayFlatTest() throws InterruptedException {
        // open page https://www.ss.com/lv/real-estate/flats/riga/centre/
        driver.get("https://www.ss.com/lv/real-estate/flats/riga/centre/");
        Thread.sleep(2000);

// select today advertisement
// WebElement daysFilter = driver.findElement(By.id("today_cnt_sl"));
// daysFilter.click();

        Select daysFilter = new Select(driver.findElement(By.id("today_cnt_sl")));
        daysFilter.selectByIndex(1);

        // max price 10
        WebElement maxPriceInput = driver.findElement(By.id("f_o_8_max"));
        maxPriceInput.clear();
        maxPriceInput.sendKeys("10");

        // click Search button
        WebElement searchButton = driver.findElement(By.cssSelector("#filter_tbl > tbody > tr > td:nth-child(2) > input"));
        searchButton.click();
        Thread.sleep(2000);

        //check that only one is found
        List<WebElement> searchResults = driver.findElements(By.cssSelector("#filter_frm table:nth-child(3) tr input[type='checkbox']"));
        assertEquals(searchResults.size(), 1, "More than one result found");
    }

}
