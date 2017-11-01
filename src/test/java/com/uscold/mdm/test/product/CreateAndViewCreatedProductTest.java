package com.uscold.mdm.test.product;

import com.uscold.mdm.test.AbstractTestClass;
import com.uscold.mdm.test.util.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CreateAndViewCreatedProductTest extends AbstractTestClass {

    final static String CUSTOMER_NUMBER = "1016620";
    final static String productName = "AUTOTEST_PRODUCT_1";
    final static String productDescription = "auto snack bar";


    @Test(priority = 1)
    public void createProductTest() {
        PageHelper.chooseModule(driver, "Product Management");
        PageHelper.chooseWarehouse(driver, 160);

        click(driver.findElement(By.id("createNewBtn")));

        driver.findElement(By.id("custNumber")).clear();
        driver.findElement(By.id("custNumber")).sendKeys(CUSTOMER_NUMBER);
        List<WebElement> lis = driver.findElements(By.xpath("//ul[@class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all']/li"));
        click(lis.stream().filter(el -> el.getText().toLowerCase().contains(CUSTOMER_NUMBER)).findFirst().get());
        PageHelper.waitForJSandJQueryToLoad(driver);

        driver.findElement(By.id("prodCode")).clear();
        driver.findElement(By.id("prodCode")).sendKeys(productName);
        driver.findElement(By.id("prodDesc")).clear();
        driver.findElement(By.id("prodDesc")).sendKeys(productDescription);
        driver.findElement(By.id("tieCnt")).clear();
        driver.findElement(By.id("tieCnt")).sendKeys("10");
        driver.findElement(By.id("prodHghVal")).clear();
        driver.findElement(By.id("prodHghVal")).sendKeys("10");
        driver.findElement(By.id("stckHgtVal")).clear();
        driver.findElement(By.id("stckHgtVal")).sendKeys("2");
        driver.findElement(By.id("tempFrmVal")).clear();
        driver.findElement(By.id("tempFrmVal")).sendKeys("0");
        driver.findElement(By.id("tempToVal")).clear();
        driver.findElement(By.id("tempToVal")).sendKeys("25");

        //WebElement tabStructureView = driver.findElement(By.cssSelector(""));


        PageHelper.waitForJSandJQueryToLoad(driver);
        click(driver.findElement(By.id("prodSubmit")));

        String url = driver.getCurrentUrl();
        if (!(url.contains("product/productSearch?") && url.contains("hasSuccessMsg=true"))) {
            try {
                Assert.assertFalse(driver.findElement(By.id("prodCode.errors")).getText().toLowerCase().contains("duplicate"), "Product with this product code already exists. \n");
            } catch (Exception ex) {
            }
            throw new RuntimeException("Can`t add this product code.");
        }

        createdSuccessfully = true;
    }

    private boolean createdSuccessfully = false;

    @Test(priority = 2)
    public void viewCreatedProductTest() {
        Assert.assertTrue(createdSuccessfully, "Skip. Product wasn't created \n");
        driver.findElement(By.id("codeOrDesc")).clear();
        driver.findElement(By.id("codeOrDesc")).sendKeys(productName);
        driver.findElement(By.id("searchOne")).click();

        WebElement table = driver.findElement(By.id("list"));
        WebElement row = table.findElement(By.xpath(".//tr[td[contains(text(),'" + CUSTOMER_NUMBER + "')]]"));
        click(row.findElement(By.xpath("td/a")));

        WebElement columnContainer = driver.findElement(By.xpath("//div[@class='leftColumnContainer']"));
        WebElement span = columnContainer.findElement(By.xpath("div[1]/span"));
        String customerNumber = span.getText();

        span = columnContainer.findElement(By.xpath("div[2]/span"));
        String productCode = span.getText();

        span = columnContainer.findElement(By.xpath("div[3]/span"));
        String productDesctiption = span.getText();

        org.testng.Assert.assertTrue(customerNumber.contains(CUSTOMER_NUMBER));
        org.testng.Assert.assertTrue(productCode.equalsIgnoreCase(productName));
        org.testng.Assert.assertTrue(productDesctiption.equalsIgnoreCase(productDesctiption));

    }

    //fill product dimensions up
/* List<WebElement> tabs = driver.findElements(By.cssSelector("div.bodyContainer div.tabStructure>div.hand>img"));
        click(tabs.get(1));
        //Product dimensions tab
        driver.findElement(By.id("1_grossWgt")).clear();
        driver.findElement(By.id("1_grossWgt")).sendKeys("0");
        driver.findElement(By.id("1_tare")).clear();
        driver.findElement(By.id("1_tare")).sendKeys("0");
        driver.findElement(By.id("1_length")).clear();
        driver.findElement(By.id("1_length")).sendKeys("0.01");
        driver.findElement(By.id("1_width")).clear();
        driver.findElement(By.id("1_width")).sendKeys("0.01");
        driver.findElement(By.id("1_height")).clear();
        driver.findElement(By.id("1_height")).sendKeys("0.01");
        driver.findElement(By.id("2_grossWgt")).clear();
        driver.findElement(By.id("2_grossWgt")).sendKeys("0");
        driver.findElement(By.id("2_tare")).clear();
        driver.findElement(By.id("2_tare")).sendKeys("0");
        driver.findElement(By.id("2_length")).clear();
        driver.findElement(By.id("2_length")).sendKeys("0.01");
        driver.findElement(By.id("2_width")).clear();
        driver.findElement(By.id("2_width")).sendKeys("0.01");
        driver.findElement(By.id("2_height")).clear();
        driver.findElement(By.id("2_height")).sendKeys("0.01");

        click(driver.findElement(By.id("btnSubmit")));*/
}
