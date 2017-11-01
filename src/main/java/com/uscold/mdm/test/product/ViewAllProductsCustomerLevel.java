package com.uscold.mdm.test.product;

import com.uscold.mdm.test.AbstractTestClass;
import com.uscold.mdm.test.util.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ViewAllProductsCustomerLevel extends AbstractTestClass {

    @Test
    public void viewAllProductsCustomerLevel() throws InterruptedException {
        PageHelper.chooseWarehouse(driver,160);
        PageHelper.chooseCustomer(driver,100950);
        PageHelper.chooseModule(driver,"Product Management");
        click(driver.findElement(By.xpath("//button[@id='searchOne']")));
        PageHelper.waitForJSandJQueryToLoad(driver);
        List<WebElement> trs = driver.findElements(By.xpath("//table[@id='list']/tbody/tr"));
        Assert.assertTrue(trs.size() > 1);

    }

}