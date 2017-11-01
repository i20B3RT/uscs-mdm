package com.uscold.mdm.test;

import com.uscold.mdm.test.util.PageHelper;
import com.uscold.mdm.test.util.WebDriverFactory;
import org.apache.commons.lang3.BooleanUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.log4testng.Logger;

import static com.uscold.mdm.test.util.TestConstants.GET_ELEMENT_TIMEOUT;

public abstract class AbstractTestClass {
    private static final Logger LOGGER = Logger.getLogger(AbstractTestClass.class);
    private static final boolean IS_HEADLESS = BooleanUtils.toBoolean(System.getProperty("ui.headless"));

    protected WebDriver driver;
    protected WebDriverWait wait;

    public static final String EWM_URL = System.getProperty("ewm.url");

    //Method for adding timestamp to add to product code
    // Create object of SimpleDateFormat class and decide the format
//    DateFormat dateFormat = new SimpleDateFormat("MMddHHMMSS ");


//    String timestamp = String.valueOf(System.currentTimeMillis());

    @BeforeClass
    public void beforeClassInit() {driver = initDriver();
        LOGGER.warn("EWM_URL:" + EWM_URL);
        LOGGER.warn("HEADLESS:" + IS_HEADLESS);
        wait = new WebDriverWait(driver, 25000);
        PageHelper.loginWithCookies(driver, EWM_URL);
    }

    @BeforeTest
    public void beforeTestConfig(final ITestContext testContext) {
        LOGGER.info(String.format("starting test {}", testContext.getName()));
    }

    @AfterTest
    public void afterTestconfig(final ITestContext testContext) {
        LOGGER.info(String.format("Stopping test {}", testContext.getName()));
    }

    private WebDriver initDriver() {
        driver = WebDriverFactory.getDriver(getOptions(IS_HEADLESS));
        return driver;
    }

    private ChromeOptions getOptions(boolean isHeadless) {
        LOGGER.info("WARNING Test in headless mode");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars");
        if (isHeadless) {
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--test-type");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--no-first-run");
            chromeOptions.addArguments("--no-default-browser-check");
            chromeOptions.addArguments("--ignore-certificate-errors");
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.addArguments("disable-infobars");
        }

        return chromeOptions;
    }

    @AfterClass(alwaysRun = true)
    public void afterTest() {
        driver.quit();
        LOGGER.info("Driver quit");
    }

    protected void click(WebElement el, int maxWaitTimeMillis) {
        PageHelper.click(el, maxWaitTimeMillis);
    }

    protected void click(WebElement el) {
        PageHelper.click(el, GET_ELEMENT_TIMEOUT * 1000);
    }

    protected void click(By by) {
        WebElement el = driver.findElement(by);
        PageHelper.click(el, GET_ELEMENT_TIMEOUT * 1000);
    }

    long recordTimeForOperation(Runnable runnable) {
        long startedAt = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - startedAt;
    }

    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
