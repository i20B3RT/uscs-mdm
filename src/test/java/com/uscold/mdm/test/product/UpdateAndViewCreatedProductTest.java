package com.uscold.mdm.test.product;

import com.uscold.mdm.test.AbstractTestClass;
import com.uscold.mdm.test.util.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UpdateAndViewCreatedProductTest extends AbstractTestClass {
    private final String CUSTOMER_NUMBER = CreateAndViewCreatedProductTest.CUSTOMER_NUMBER;
    private final String productName = CreateAndViewCreatedProductTest.productName;
    private final String productDescription = CreateAndViewCreatedProductTest.productDescription;
    private final String EDITED_TIE = "15";
    private final String EDITED_HIGH = "15";
    private boolean updatedSuccessfully;

    @Test(priority = 1)
    public void updateProduct() throws InterruptedException {

        PageHelper.chooseModule(driver, "Product Management");
        PageHelper.chooseWarehouse(driver, 160);
        driver.findElement(By.id("codeOrDesc")).clear();
        driver.findElement(By.id("codeOrDesc")).sendKeys(productName.toUpperCase());
        click(driver.findElement(By.id("searchOne")));
        PageHelper.waitForJSandJQueryToLoad(driver);

        WebElement table = driver.findElement(By.id("list"));
        WebElement row = table.findElement(By.xpath(".//tr[td[contains(text(),'" + CUSTOMER_NUMBER + "')]]"));
        click(row.findElement(By.xpath("td/a")));

        click(driver.findElement(By.id("editBasicLinkView")));

        WebElement prdDescInput = driver.findElement(By.id("prodDesc"));
        prdDescInput.clear();
        prdDescInput.sendKeys(productDescription + ":EDITED");

        WebElement tieInp = driver.findElement(By.id("tieCnt"));
        tieInp.clear();
        tieInp.sendKeys(EDITED_TIE);
        WebElement highVInp = driver.findElement(By.id("prodHghVal"));
        highVInp.clear();
        highVInp.sendKeys(EDITED_HIGH);
        /*WebElement shortDescInput = driver.findElement(By.id("prodShortDesc"));
        shortDescInput.clear();
        shortDescInput.sendKeys("SHORT:"+productDescription);*/

        click(driver.findElement(By.id("btn_save")));
        WebElement statusMsg = driver.findElement(By.id("reportSuccessMsg"));
        if (!statusMsg.isDisplayed() && !statusMsg.getText().toLowerCase().contains("updated successfully"))
            throw new RuntimeException("Failed to update product");
        updatedSuccessfully = true;
    }

    @Test(priority = 2)
    public void loadEditedProductInTable() {
        Assert.assertTrue(updatedSuccessfully, "Skip.Product wasn't updated");
        driver.findElement(By.id("codeOrDesc")).clear();
        driver.findElement(By.id("codeOrDesc")).sendKeys(productName.toUpperCase());
        click(driver.findElement(By.id("searchOne")));
        PageHelper.waitForJSandJQueryToLoad(driver);
        WebElement table = driver.findElement(By.id("list"));
        WebElement row = table.findElement(By.xpath(".//tr[td[contains(text(),'" + CUSTOMER_NUMBER + "')]]"));

        String expectedDesc = productDescription + ":EDITED";
        Assert.assertEquals(row.findElement(By.xpath("td[7]")).getText().trim().toLowerCase(), expectedDesc.trim().toLowerCase());
        int value = Integer.valueOf(EDITED_TIE) * Integer.valueOf(EDITED_HIGH);
        Assert.assertEquals(row.findElement(By.xpath("td[10]")).getText(), String.valueOf(value));

    }
}
