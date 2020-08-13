import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Tester {
    public static void main(String[] args)
    {
        System.setProperty("webdriver.gecko.driver", "geckodriver");
        WebDriver driver =  new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://google.com");
        String searchWord = "software automated testing";
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys(searchWord + "\n"); // send also a "\n"
        element.submit();

        // wait until the google page shows the result
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("result-stats")));

        List<WebElement> findElements = driver.findElements(By.tagName("a"));
        List<WebElement> els = driver.findElements(By.tagName("a"));

        // this are all the links you like to visit
        System.out.println(findElements.size());
        for (WebElement webElement : findElements)
        {
            if (webElement.getText().contains("wikipedia")) {
                driver.get(webElement.getAttribute("href"));
            }
        }
    }
}
