package com.uscold.mdm.test;

import com.uscold.mdm.test.util.PageHelper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;


public class ViewAllReceipts extends AbstractTestClass {

    @Test
    public void viewAllReceipts() throws InterruptedException {
        //   ми на сторінці хоум

        PageHelper.chooseModule(driver,"Receipt Maintenance");
        PageHelper.chooseWarehouse(driver, 160);
        PageHelper.chooseCustomer(driver,100950);
        WebElement srchBtn = driver.findElement(By.id("basicSrch"));
        click(srchBtn);
        PageHelper.waitForJSandJQueryToLoad(driver);
        WebElement tableEl = driver.findElement(By.id("list"));
        List<WebElement> trs = tableEl.findElements(By.tagName("tr"));
        Assert.assertTrue(trs.size()>1);
//sa
    }

}
