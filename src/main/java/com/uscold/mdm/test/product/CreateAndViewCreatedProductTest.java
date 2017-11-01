package com.uscold.mdm.test.product;

import com.uscold.mdm.test.AbstractTestClass;
import com.uscold.mdm.test.util.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class CreateAndViewCreatedProductTest extends AbstractTestClass {

    final static String CUSTOMER_NUMBER = "1016620";
    final static String productCode = "AUTOTEST_PRODUCT_1";
    final static String productName = "AUTOTEST_PRODUCT_1";
    final static String productDescription = "A-TEST_";

    //Method for adding timestamp to add to product code
    // Create object of SimpleDateFormat class and decide the format
    DateFormat dateFormat = new SimpleDateFormat("MMddHHMMSS ");



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
        driver.findElement(By.id("prodCode")).sendKeys(productCode );
//        driver.findElement(By.id("prodCode")).sendKeys(productName);
        driver.findElement(By.id("prodDesc")).clear();
        driver.findElement(By.id("prodDesc")).sendKeys(productDescription+dateFormat);

        //Storage drop-down -  Click on dropdown and select Freezer index=4
        driver.findElement(By.xpath(".//*[@id='strgTypCde_chosen']/a/span")).click();
        driver.findElement(By.xpath(".//*[@id='strgTypCde_chosen']/div/ul/li[4]")).click();
//        Thread.sleep(2000);
        PageHelper.waitForJSandJQueryToLoad(driver);

        //Commodity Code drop-down -  Click on dropdown and select 193-MISC FREEZER index=87
        driver.findElement(By.xpath(".//*[@id='comCde_chosen']/a/div/b")).click();
        driver.findElement(By.xpath(".//*[@id='comCde_chosen']/div/ul/li[87]")).click();
//        Thread.sleep(2000);
        PageHelper.waitForJSandJQueryToLoad(driver);

        //Package Code drop-down -  Click on dropdown and select carton index=21
        driver.findElement(By.xpath(".//*[@id='pkgCde_chosen']/a/span")).click();
        driver.findElement(By.xpath(".//*[@id='pkgCde_chosen']/div/ul/li[21]")).click();
//        Thread.sleep(2000);
        PageHelper.waitForJSandJQueryToLoad(driver);

        //Codedate drop-down -  Click on dropdown and select Receipt Date as type index=3
        driver.findElement(By.xpath(".//*[@id='dtTypCde_chosen']/a/span")).click();
        driver.findElement(By.xpath(".//*[@id='dtTypCde_chosen']/div/ul/li[3]")).click();
//        Thread.sleep(2000);
        PageHelper.waitForJSandJQueryToLoad(driver);

        //Status drop-down -  Click on dropdown and select Active index=2
        driver.findElement(By.xpath(".//*[@id='stsCde_chosen']/a/span")).click();
        driver.findElement(By.xpath(".//*[@id='stsCde_chosen']/div/ul/li[2]")).click();
//        Thread.sleep(2000);
        PageHelper.waitForJSandJQueryToLoad(driver);

        driver.findElement(By.id("tieCnt")).clear();
        driver.findElement(By.id("tieCnt")).sendKeys("10");
        driver.findElement(By.id("prodHghVal")).clear();
        driver.findElement(By.id("prodHghVal")).sendKeys("10");
        driver.findElement(By.id("stckHgtVal")).clear();
        driver.findElement(By.id("stckHgtVal")).sendKeys("1");
//        driver.findElement(By.id("tempFrmVal")).clear();
//        driver.findElement(By.id("tempFrmVal")).sendKeys("0");
//        driver.findElement(By.id("tempToVal")).clear();
//        driver.findElement(By.id("tempToVal")).sendKeys("25");


        driver.findElement(By.id("basicDtlNext")).click();
        PageHelper.waitForJSandJQueryToLoad(driver);

        driver.findElement(By.xpath(".//*[@id='1_grossWgt']")).clear();
        driver.findElement(By.xpath(".//*[@id='1_grossWgt']")).sendKeys("10.00");

        driver.findElement(By.xpath(".//*[@id='1_tare']")).clear();
        driver.findElement(By.xpath(".//*[@id='1_tare']")).sendKeys("1");

        driver.findElement(By.xpath(".//*[@id='1_length']")).clear();
        driver.findElement(By.xpath(".//*[@id='1_length']")).sendKeys("7.00");

        driver.findElement(By.xpath(".//*[@id='1_width']")).clear();
        driver.findElement(By.xpath(".//*[@id='1_width']")).sendKeys("5.50");

        driver.findElement(By.xpath(".//*[@id='1_height']")).clear();
        driver.findElement(By.xpath(".//*[@id='1_height']")).sendKeys("4.00");

        driver.findElement(By.id("dimNextClick")).click();


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
