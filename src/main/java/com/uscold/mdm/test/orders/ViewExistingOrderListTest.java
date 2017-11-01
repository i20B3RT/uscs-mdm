/*
package com.uscold.mdm.test.orders;

import com.uscold.mdm.test.AbstractTestClass;
import com.uscold.mdm.test.util.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;


//this is Oleksandra's attempt =) please don't update, I'll finish the work later

public class ViewExistingOrderListTest extends AbstractTestClass {

    @Test
    public void viewExistingOrderList() throws InterruptedException {
        PageHelper.chooseWarehouse(driver,"160");
        PageHelper.chooseModule(driver,"Order Maintenance");










        public class Untitled {
            private WebDriver driver;
            private String baseUrl;
            private boolean acceptNextAlert = true;
            private StringBuffer verificationErrors = new StringBuffer();

            @BeforeClass(alwaysRun = true)
            public void setUp() throws Exception {
                driver = new FirefoxDriver();
                baseUrl = "http://uatwas.uscold.com:9113/ewm/home.htm";
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            }

            @Test
            public void testUntitled() throws Exception {
                driver.get(baseUrl + "/ewm/orderentry/loadOrderEntry");
                driver.findElement(By.cssSelector("#orderEntryCust_chosen > div.chosen-drop > div.chosen-search > input[type=\"text\"]")).clear();
                driver.findElement(By.cssSelector("#orderEntryCust_chosen > div.chosen-drop > div.chosen-search > input[type=\"text\"]")).sendKeys("perdue");
                driver.findElement(By.cssSelector("li.active-result.result-selected")).click();
                driver.findElement(By.cssSelector("button.ms-choice")).click();
                driver.findElement(By.xpath("(//input[@name='selectItem'])[2]")).click();
                driver.findElement(By.name("selectItem")).click();
                driver.findElement(By.cssSelector("a.chosen-single.chosen-default > div > b")).click();
                driver.findElement(By.id("oeBasicSearch")).click();
            }

            @AfterClass(alwaysRun = true)
            public void tearDown() throws Exception {
                driver.quit();
                String verificationErrorString = verificationErrors.toString();
                if (!"".equals(verificationErrorString)) {
                    fail(verificationErrorString);
                }
            }

            private boolean isElementPresent(By by) {
                try {
                    driver.findElement(by);
                    return true;
                } catch (NoSuchElementException e) {
                    return false;
                }
            }

            private boolean isAlertPresent() {
                try {
                    driver.switchTo().alert();
                    return true;
                } catch (NoAlertPresentException e) {
                    return false;
                }
            }

            private String closeAlertAndGetItsText() {
                try {
                    Alert alert = driver.switchTo().alert();
                    String alertText = alert.getText();
                    if (acceptNextAlert) {
                        alert.accept();
                    } else {
                        alert.dismiss();
                    }
                    return alertText;
                } finally {
                    acceptNextAlert = true;
                }
            }
        }





//        WebElement tableBtnSearch = wait.until(ExpectedConditions.elementToBeClickable(By.id("oeBasicSearch")));

    }

}
*/
