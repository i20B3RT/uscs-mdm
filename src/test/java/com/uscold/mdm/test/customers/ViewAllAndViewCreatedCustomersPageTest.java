package com.uscold.mdm.test.customers;

import com.uscold.mdm.test.AbstractTestClass;
import com.uscold.mdm.test.util.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class ViewAllAndViewCreatedCustomersPageTest extends AbstractTestClass {
    //customer has user-friendly reference
    private final String CUSTOMER_TO_FIND = "1016620";


    private final String EXPECTED_ACCOUNT_TYPE = "Customer";
    private final String EXPECTED_CORPORATE_NAME = "KOZAK";

    @Test(priority = 1)
    public void viewAllCustomers() throws IOException, InterruptedException {
        PageHelper.chooseModule(driver, "Customer Management");
        PageHelper.chooseWarehouse(driver, 160);

        WebElement tableBtnSearch = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchOne")));
        click(tableBtnSearch);

        //wait for loading img to disappear
        WebElement loading = driver.findElement(By.id("load_list"));
        wait.until(ExpectedConditions.invisibilityOf(loading));
        List<WebElement> customers = driver.findElement(By.xpath("//table[@id='list']")).findElements(By.tagName("tr"));

        //String idOfFirstRow = customers.get(1).findElement(By.xpath("td[3]/a")).getText();
        Assert.assertTrue(customers.size() > 1, "No customers found");
    }

    @Test(priority = 2)
    public void viewExistingCustomersPage() {

        WebElement customerInput = driver.findElement(By.id("txt_searchNumber"));
        customerInput.clear();
        customerInput.sendKeys(CUSTOMER_TO_FIND);

        WebElement tableBtnSearch = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchOne")));
        click(tableBtnSearch);

        //wait for loading img to disappear
        WebElement loading = driver.findElement(By.id("load_list"));
        wait.until(ExpectedConditions.invisibilityOf(loading));
        WebElement customerTr = driver.findElement(By.xpath("//table[@id='list']//tr[2]"));
        click(customerTr.findElement(By.xpath("td[contains(@title,'" + CUSTOMER_TO_FIND + "')]/a")));

        WebElement columnContainer = driver.findElement(By.xpath("//div[@class='leftColumnContainer']"));

        WebElement span = columnContainer.findElement(By.xpath("div[1]/span"));
        String accountType = span.getText();

        span = columnContainer.findElement(By.xpath("div[2]/span"));
        String customerNumber = span.getText();

        span = columnContainer.findElement(By.xpath("div[3]/span"));
        String corporateName = span.getText();

        Assert.assertEquals(customerNumber, CUSTOMER_TO_FIND);
        Assert.assertEquals(accountType, EXPECTED_ACCOUNT_TYPE);
        Assert.assertEquals(corporateName, EXPECTED_CORPORATE_NAME);

        WebElement tabStructureView = driver.findElement(By.cssSelector("div.bodyContainer div.tabStructureView"));
        List<WebElement> tabs = tabStructureView.findElements(By.cssSelector("div.hand"));
        for (int i = 1; i < tabs.size(); i++) {
            click(tabs.get(i));
            tabStructureView = driver.findElement(By.cssSelector("div.bodyContainer div.tabStructureView"));
            tabs = tabStructureView.findElements(By.cssSelector("div.hand"));
        }

    }

    @Test(priority = 3)
    public void checkIfScrollbarExistsWhileInZoom() {
        WebElement tabStructureView = driver.findElement(By.cssSelector("div.bodyContainer div.tabStructureView"));
        WebElement tab = tabStructureView.findElements(By.cssSelector("div.hand")).get(0);
        click(tab);
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom = '180%';");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean scrollBarPresent = (boolean) js.executeScript(
                "var element = document.querySelector('div.bodyContainer'); " +
                        "return element.scrollHeight > element.clientHeight;");
        Assert.assertTrue(scrollBarPresent);
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom = '100%';");

    }

    @Test(priority = 4)
    public void openSummaryTabTest() {
        click(driver.findElement(By.id("viewBasicSmryLink")));

        String[] windows = new String[2];
        driver.getWindowHandles().toArray(windows);
        driver.switchTo().window(windows[windows.length - 1]);


        WebElement columnContainer = driver.findElement(By.xpath("//div[@class='leftColumnContainer']"));
        WebElement span = columnContainer.findElement(By.xpath("div[1]/span"));
        String customerNumber = span.getText();
        span = columnContainer.findElement(By.xpath("div[2]/span"));
        String corporateName = span.getText();

        Assert.assertEquals(customerNumber, EXPECTED_ACCOUNT_TYPE);
        Assert.assertEquals(corporateName, EXPECTED_CORPORATE_NAME);
        driver.close();
        driver.switchTo().window(windows[0]);
    }
}
