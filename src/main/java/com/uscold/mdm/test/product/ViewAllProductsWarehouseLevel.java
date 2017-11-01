package com.uscold.mdm.test.product;

import com.uscold.mdm.test.AbstractTestClass;
import com.uscold.mdm.test.util.PageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ViewAllProductsWarehouseLevel extends AbstractTestClass {

    @Test
    public void viewAllProductsWarehouseLevel() throws InterruptedException {
        PageHelper.chooseWarehouse(driver,160);
        PageHelper.chooseModule(driver,"Product Management");
//        click(driver.findElement(By.xpath("//div[@id='globalWarehouseSelect_chosen']//a[@class='chosen-single']")));
//        click(driver.findElement(By.xpath("//div[@id='globalWarehouseSelect_chosen']//ul[@class='chosen-results']/li[contains(text(),'160')]")));
        click(driver.findElement(By.xpath("//div[@id='basicSearchSelect_chosen']")));
        click(driver.findElement(By.xpath("//div[@id='basicSearchSelect_chosen']//li[contains(text(),'Warehouse')]")));
        click(driver.findElement(By.xpath("//div[@id='warehousebasic_chosen']")));

        click(driver.findElement(By.xpath("//div[@id='warehousebasic_chosen']//li[contains(text(),'160')]")));
        click(driver.findElement(By.xpath("//button[@id='searchOne']")));
        PageHelper.waitForJSandJQueryToLoad(driver);
        List<WebElement> trs = driver.findElements(By.xpath("//table[@id='list']/tbody/tr"));
        Assert.assertTrue(trs.size() > 1);

    }

}

